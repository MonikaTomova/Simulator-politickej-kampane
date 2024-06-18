package simulator.politicalCompassElements;

import java.util.List;

import simulator.Request;

/** Represents a candidate on the political compass */
public class Candidate extends PoliticalSubject {
    private PoliticalParty politicalParty = null;

    
    /**
     * Sends a request to the closest political party
     * @param politicalParties list of all the political parties to choose from
     */
    public void sendRequest(List<PoliticalParty> politicalParties) {
        double closestPartyDistance = 20;
        double currentPartyDistance;
        PoliticalParty closestParty = politicalParties.get(0);
        for (PoliticalParty party : politicalParties) {
            if ((currentPartyDistance = distanceFrom(party)) <= closestPartyDistance) {
                closestPartyDistance = currentPartyDistance;
                closestParty = party;
            }
        }
        if (closestPartyDistance < 5) {
            Request request = new Request(this, closestPartyDistance);
            closestParty.saveRequest(request);
        }
        return;
    }

    /**
     * Sets the political party for the candidate
     * @param politicalParty the political party to set for the candidate
     */
    protected void setParty(PoliticalParty politicalParty) {
        this.politicalParty = politicalParty;
        return;
    }

    /**
     * Returns the political party of the candidate
     * @return the political party of the candidate
     */
    public PoliticalParty getParty() {
        return this.politicalParty;
    }
}
