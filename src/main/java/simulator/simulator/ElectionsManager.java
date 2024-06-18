package simulator.simulator;

import java.util.List;

import simulator.politicalCompassElements.Candidate;
import simulator.politicalCompassElements.PoliticalParty;

/** Manages the election process */
public class ElectionsManager {
    private Simulator simulator;

    protected ElectionsManager(Simulator simulator) {
        this.simulator = simulator;
    }

    /** Sets the votes received by political parties and candidates */
    protected void setVotes() {
        List<PoliticalParty> politicalParties = simulator.getPoliticalParties();
        List<Candidate> candidates = simulator.getCandidates();
        for (PoliticalParty politicalParty : politicalParties) {
            politicalParty.recieveVotes();
        }
        for (Candidate candidate : candidates) {
            candidate.recieveVotes();
        }
        return;
    }
}
