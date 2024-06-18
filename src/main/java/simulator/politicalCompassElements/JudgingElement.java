package simulator.politicalCompassElements;

import java.util.List;
import java.util.Map;

import simulator.Pair;

/** Represents an element that can express opinions and judge other elements on the political compass */
public interface JudgingElement extends PoliticalCompassElement { 
    
    /**
     * Expresses an opinion based on preferences and determines the preferred subject(s)
     * @param preferences hashmap containing all the preferences
     * @param politicalParties list of all the political parties candidating
     * @param candidates list of all the candidates
     * @param minToVote how much has the element to sympathize with a candidate to vote them independently of the political party they are member of
     */
    public void expressOpinion(Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> preferences,
        List<PoliticalParty> politicalParties, List<Candidate> candidates, double minToVote);
    
    /**
     * Finds the preferred subject from the given list based on preferences
     * @param preferences hashmap of all the preferences
     * @param politicalSubjects list of political subjects to choose from
     * @return the preferred subject from the given list
     */
    public <T extends PoliticalSubject> T findPreferredSubject(
        Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> preferences,
        List<T> politicalSubjects);
}
