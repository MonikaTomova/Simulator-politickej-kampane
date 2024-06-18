package simulator;

import java.util.List;
import java.util.Map;

import simulator.politicalCompassElements.Candidate;
import simulator.politicalCompassElements.JudgedElement;
import simulator.politicalCompassElements.JudgingElement;
import simulator.politicalCompassElements.PoliticalParty;

public class ElectionPromise<T extends JudgedElement> {
    private T promiser;
    private float areaOfImpact;
    private float persuasiveness;

    /**
     * Constructs an ElectionPromise object with the specified promiser and area of impact
     * @param promiser the promiser of the promise
     * @param areaOfImpact the area of impact of the promise (1 - 9)
     */
    public ElectionPromise(T promiser, float areaOfImpact) {
        this.persuasiveness = ((10 - areaOfImpact) * 10) / 100; // 0.1 - 0.9
        this.promiser = promiser;
        this.areaOfImpact = areaOfImpact;
    }

    /**
     * Influences the preferences of judging elements based on the promiser's election promise
     * @param judgingElements a list of judging elements to influence
     * @param preferences a map containing preferences between judging and judged elements
     * @param secondaryImpact the impact of a candidate's promise on their party's preferences
     */
    public <U extends JudgingElement> void influencePreferences(List<U> judgingElements,
            Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> preferences, double secondaryImpact) {
        Pair<? extends JudgingElement, ? extends JudgedElement> key;
        Double oldValue, newValue;

        for (U judgingElement : judgingElements) {
            key = new Pair<>(judgingElement, promiser);
            oldValue = preferences.get(key);
            newValue = oldValue;
            if (judgingElement.distanceFrom(promiser) <= areaOfImpact) {
                newValue *= 1 + persuasiveness;
                if (promiser instanceof PoliticalParty) {
                    newValue *= 1 + persuasiveness;
                }
            } else {
                newValue *= 1 - persuasiveness;
                if (promiser instanceof PoliticalParty) {
                    newValue *= 1 - persuasiveness;
                }
            }
            preferences.replace(key, oldValue, newValue);
        }
        if (promiser instanceof Candidate) {
            Candidate candidate = (Candidate) promiser;
            secondaryImpact(candidate.getParty(), judgingElements, preferences, secondaryImpact);
        }
        return;
    }

    /**
     * Applies the secondary impact of a candidate's promise on their party's preferences
     * @param politicalParty the political party affected by the candidate's promise
     * @param judgingElements the list of judging elements to influence
     * @param preferences the map containing preferences between judging and judged elements
     * @param secondaryImpact the impact of a candidate's promise on their party's preferences
     */
    private <U extends JudgingElement> void secondaryImpact(PoliticalParty politicalParty, List<U> judgingElements,
            Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> preferences, double secondaryImpact) {
        Pair<? extends JudgingElement, ? extends JudgedElement> key;
        Double oldValue, newValue;
        double positiveInfluence = 1 + persuasiveness * secondaryImpact;
        double negativeInfluence = 1 - persuasiveness * secondaryImpact;
        for (U judgingElement : judgingElements) {
            key = new Pair<>(judgingElement, promiser);
            oldValue = preferences.get(key);
            newValue = oldValue;
            if (judgingElement.distanceFrom(promiser) <= areaOfImpact) {
                newValue *= positiveInfluence;
            } else {
                newValue *= negativeInfluence;
            }
            preferences.replace(key, oldValue, newValue);
        }
    }

    /**
     * Returns the area of impact of the promise
     * @return the area of the impact of the promise (1 - 9)
     */
    public float getAreaOfImpact() {
        return this.areaOfImpact;
    }
}
