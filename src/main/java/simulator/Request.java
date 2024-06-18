package simulator;

import simulator.politicalCompassElements.Candidate;

public class Request {
    private Candidate candidate;
    private double distance;

    /**
     * Constructs a Request object with the specified candidate and distance
     * @param candidate the candidate making the request
     * @param distance the distance between the candidate and the political party they want to collaborate with
     */
    public Request(Candidate judgingElement, double distance) {
        this.candidate = judgingElement;
        this.distance = distance;
    }

    /** 
     * Returns the distance between the candidate and the political party which they want to colaborate with
     * @return the distance between the candidate and the political party which they want to colaborate with
     */
    public double getDistance() {
        return this.distance;
    }

    
    /** 
     * Returns the requesting candidate
     * @return the requesting candidate
     */
    public Candidate getCandidate() {
        return this.candidate;
    }
}
