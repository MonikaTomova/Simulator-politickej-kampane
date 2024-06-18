package simulator.politicalCompassElements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simulator.Request;

/** Represents a political party in the political compass */
public class PoliticalParty extends PoliticalSubject {
    private List<Request> requests = new ArrayList<>();
    private List<Candidate> members = new ArrayList<>();

    /**
     * Returns a list of members of the political party
     * @return he list of members of the political party
     */
    public List<Candidate> getMembers() {
        return this.members;
    }

    /**
     * Saves the given request
     * @param request the request to be saved
     */
    protected void saveRequest(Request request) {
        requests.add(request);
    }

    /**
     * Sorts requests by the distances from the requestors and accepts first <maxMembers> of them
     * @param maxMembers the maximum number of members that a political party can accept
     */
    public void processRequests(int maxMembers) {
        Collections.sort(requests, (a, b) -> Double.compare(a.getDistance(), b.getDistance()));

        int i = requests.size();
        if (i > 150) {
            i = 150;
        }

        for (int j = 0; j < i; j++) {
            Candidate acceptedCandidate = requests.get(j).getCandidate();
            members.add(acceptedCandidate);
            acceptedCandidate.setParty(this);
        }
        return;
    }
}