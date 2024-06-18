package simulator.simulator;

import java.util.List;
import java.util.Map;

import simulator.ElectionPromise;
import simulator.Pair;
import simulator.politicalCompassElements.Candidate;
import simulator.politicalCompassElements.Elector;
import simulator.politicalCompassElements.JudgedElement;
import simulator.politicalCompassElements.JudgingElement;
import simulator.politicalCompassElements.PoliticalParty;
import simulator.politicalCompassElements.PoliticalSubject;

/** Manages the campaign process */
public class CampaignManager {

    private Simulator simulator;

    protected CampaignManager(Simulator simulator) {
        this.simulator = simulator;
    }

    /** Makes each political party and candidate make their election promise, updates preferences */
    public <T extends PoliticalSubject> void propagate() {
        Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> preferences = simulator.getPreferences();
        List<PoliticalParty> politicalParties = simulator.getPoliticalParties();
        List<Candidate> candidates = simulator.getCandidates();
        List<Elector> electors = simulator.getElectors();
        double minToVote = simulator.getMinToVote();
        double secondaryImpact = simulator.getSecondaryImpact();

        simulator.getPreferenceManager().resetPreferences((List<T>) politicalParties, (List<T>) candidates);

        for (PoliticalParty politicalParty : politicalParties) {
                ElectionPromise<? extends JudgedElement> promise = politicalParty.randPromise();
                promise.influencePreferences(electors, preferences, secondaryImpact);
        }

        for (Candidate candidate : candidates) {
            ElectionPromise<? extends JudgedElement> promise = candidate.randPromise();
            promise.influencePreferences(electors, preferences, secondaryImpact);
        }

        for (Elector elector : electors) {
            elector.expressOpinion(preferences, politicalParties, candidates, minToVote);
        }

        return;
    }
}
