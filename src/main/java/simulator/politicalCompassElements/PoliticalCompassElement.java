package simulator.politicalCompassElements;

/** Represents an element on the political compass */
public interface PoliticalCompassElement {

    /** 
     * returns the X position on the political compass of the element
     * @return the X position on the political compass of the element
     */
    public float getPosX();

     /** 
     * returns the Y position on the political compass of the element
     * @return the Y position on the political compass of the element
     */
    public float getPosY();

    /**
     * Calculates and returns the Euclidean distance from this element to another element on the political compass
     * @param element the other element to calculate the distance from
     * @return the distance between this element and the other element on the political compass
     */
    public <T extends PoliticalCompassElement> double distanceFrom(T element);

}