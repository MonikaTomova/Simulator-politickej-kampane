package simulator.simulator;

import java.util.List;

import simulator.politicalCompassElements.Candidate;
import simulator.politicalCompassElements.Elector;
import simulator.politicalCompassElements.PoliticalParty;

public class SubjectsManager {
    private Simulator simulator;

    protected SubjectsManager(Simulator simulator) {
        this.simulator = simulator;
    }
    
    /** Generates political parties, candidates and electors */
    protected void generateElements(int nPoliticalParties, int nCandidates, int nElectors) {
        List<PoliticalParty> politicalParties = simulator.getPoliticalParties();
        List<Candidate> candidates = simulator.getCandidates();
        List<Elector> electors = simulator.getElectors();

        for (int i = 0; i < nPoliticalParties; i++) {
            politicalParties.add(new PoliticalParty());
        }
        for (int i = 0; i < nCandidates; i++) {
            candidates.add(new Candidate());
        }
        for (int i = 0; i < nElectors; i++) {
            electors.add(new Elector());
        }

        return;
    }

    /** Matches candidates with political parties */
    public void matchPoliticalSubjects(int maxMembers) {
        List<PoliticalParty> politicalParties = simulator.getPoliticalParties();
        List<Candidate> candidates = simulator.getCandidates();

        // send requests
        for (Candidate candidate : candidates) {
            candidate.sendRequest(politicalParties);
        }

        // process requests
        for (PoliticalParty politicalParty : politicalParties) {
            politicalParty.processRequests(maxMembers);
        }

        return;
    }
}
