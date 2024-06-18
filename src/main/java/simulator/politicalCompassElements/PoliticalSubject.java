package simulator.politicalCompassElements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import simulator.ElectionPromise;

/** Represents a political subject on the political compass */
public abstract class PoliticalSubject extends ElementOnCompass implements JudgedElement {
    private List<ElectionPromise<? extends JudgedElement>> promises = new ArrayList<>();
    private long preferences = 0;
    private long votes = 0;

    /** Increments the preferences of the political subject */
    protected void addPreference() {
        this.preferences++;
    }

    /** Resets the preferences of the political subject */
    public void resetPreferences() {
        this.preferences = 0;
    }
    
    /** Receives votes based on preferences */
    public void recieveVotes() {
        this.votes = this.preferences;
        return;
    }

    /**
     * Returns the preferences of the political subject
     * @return the preferences of the political subject
     */
    public long getPreferences() {
        return this.preferences;
    }    

    /**
     * Returns the votes received by the political subject
     * @return the votes received by the political subject
     */
    public long getVotes() {
        return this.votes;
    }

    /**
     * Returns the promises made by the political subject
     * @return the promises made by the political subject
     */
    public List<ElectionPromise<? extends JudgedElement>> getPromieses() {
        return this.promises;
    }
    
    /** 
     * Makes and returns a new ElectionPromise object
     * @param areaOfImpact how far around the political subject should the promise have a positive impact
     * @return a new ElectionPromise object
     */
    protected ElectionPromise<? extends JudgedElement> sayPromise(float areaOfImpact) {
        ElectionPromise<? extends JudgedElement> promise = new ElectionPromise<>(this, areaOfImpact);
        promises.add(promise);
        return promise;
    }
    
 
    /** 
     * Makes and returns an election promise object with a random area of impact
     * @return election promise object with a random area of impact
     */
    public ElectionPromise<? extends JudgedElement> randPromise() {
        float areaOfImpact = 1 + (new Random()).nextFloat() * 9; // 1 - 9
        ElectionPromise<? extends JudgedElement> promise = sayPromise(areaOfImpact);
        return promise;
    }   
}
