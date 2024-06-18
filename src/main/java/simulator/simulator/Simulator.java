package simulator.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import exceptions.MissingPoliticalPartiesOrCandidatesException;
import exceptions.PreferencesNotSetException;
import exceptions.PromisesNotSpokenException;
import exceptions.MissingElectorsOrCandidatesException;
import simulator.Pair;
import simulator.politicalCompassElements.Candidate;
import simulator.politicalCompassElements.Elector;
import simulator.politicalCompassElements.JudgedElement;
import simulator.politicalCompassElements.JudgingElement;
import simulator.politicalCompassElements.PoliticalParty;

public class Simulator {
    private SubjectsManager subjectsManager = new SubjectsManager(this);
    private PreferenceManager preferenceManager = new PreferenceManager(this);
    private CampaignManager campaignManager = new CampaignManager(this);
    private ElectionsManager electionsManager = new ElectionsManager(this);
    private List<PoliticalParty> politicalParties = new ArrayList<>();
    private List<Candidate> candidates = new ArrayList<>();
    private List<Elector> electors = new ArrayList<>();
    private Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> preferences = new ConcurrentHashMap<>();
    private double minToVote = 0;
    private double secondaryImpact = 0;
    private int maxMembers = 150;

    /** 
     * Sets the minimum value of sympathy needed to vote a candidate independently of the political party they are in
     * @param double the minimum value of sympathy needed to vote a candidate independently of the political party they are in 
     */
    public void setMinToVote(double minToVote) {
        this.minToVote = minToVote;
        return;
    }
    
    /** Returns the minimum value of sympathy needed to vote a candidate independently of the political party they are in
     * @return the minimum value of sympathy needed to vote a candidate independently of the political party they are in
     */
    public double getMinToVote() {
        return this.minToVote;
    }

    /** 
     * Sets the impact of a candidate's promise on the preferences of their political party
     * @param double the impact of a candidate's promise on the preferences of their political party
     */
    public void setSecondaryImpact(double secondaryImpact) {
        this.secondaryImpact = secondaryImpact;
        return;
    }

    /** 
     * Returns the impact of a candidate's promise on the preferences of their political party
     * @return the impact of a candidate's promise on the preferences of their political party
    */
    public double getSecondaryImpact() {
        return this.secondaryImpact;
    }

    /** 
     * Returns PreferenceManager object managing the preferences of candidates and political parties 
     * @return PreferenceManager object managing the preferences of candidates and political parties 
     */
    public PreferenceManager getPreferenceManager() {
        return this.preferenceManager;
    }

    /** 
     * Returns a list of all the political parties
     * @return a list of all the political parties
     */
    public List<PoliticalParty> getPoliticalParties() {
        return this.politicalParties;
    }

    /** 
     * Returns a list of all the candidates
     * @return a list of all the candidates
     */
    public List<Candidate> getCandidates() {
        return this.candidates;
    }

    /** 
     * Returns a list of all the electors
     * @return a list of all the electors
     */
    public List<Elector> getElectors() {
        return this.electors;
    }

    /**
     * Returns a hashmap of all the prefernces
     * @return a hashmap of all the preferences
     */
    public Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> getPreferences() {
        return this.preferences;
    }

    /** Resets the simulator = clears all lists and maps */
    public void reset() {
        politicalParties.clear();
        candidates.clear();
        electors.clear();
        preferences.clear();
    }

    /** Generates political parties, candidates and electors 
     * @param int number of political parties to generate
     * @param int number of candidates to generate
     * @param int number of electors to generate
    */
    public void generateElements(int nPoliticalParties, int nCandidates, int nElectors) {
        subjectsManager.generateElements(nPoliticalParties, nCandidates, nElectors);
        return;
    }

    /** Matches candidates with political parties */
    public void matchPoliticalSubjects() throws MissingPoliticalPartiesOrCandidatesException {
        if (politicalParties.isEmpty() || candidates.isEmpty()) {
            throw new MissingPoliticalPartiesOrCandidatesException("No subjects to match. Please generate political parties and candidates first.");
        } else {
            subjectsManager.matchPoliticalSubjects(maxMembers);
        }
        return;
    }

    /** Sets all electors' preferences towards every political party and candidate */
    public void setPreferences() throws MissingElectorsOrCandidatesException {
        if (electors.isEmpty() || candidates.isEmpty()) {
            throw new MissingElectorsOrCandidatesException("Unable to set preferences. Please generate electors and candidates first.");
        } else {
            preferenceManager.setPreferences();
        }
        return;
    }

    /** Makes each political party and candidate make their election promise, updates preferences */
    public void propagate() throws PreferencesNotSetException {
        if (preferences.isEmpty()) {
            throw new PreferencesNotSetException("No preferences to influence. Please set preferences first.");
        } else {
            campaignManager.propagate();
        }
        return;
    }

    /** Turns each political party's and candidate's preferences into votes */
    public void setVotes() throws PromisesNotSpokenException {
        if ((politicalParties.isEmpty() || politicalParties.get(0).getPromieses().isEmpty()) ||
            (candidates.isEmpty() || candidates.get(0).getPromieses().isEmpty())) {
            throw new PromisesNotSpokenException("Unable to evaluate the impact of the election campaign. Please make political subjects say their promises first.");
        } else {
            electionsManager.setVotes();
        }
        return;
    }
}
