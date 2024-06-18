package simulator.politicalCompassElements;

import java.util.Random;

/** Represents an element positioned on the political compass */
public class ElementOnCompass implements PoliticalCompassElement {
    private float posX;
    private float posY;

    /** Constructs an element with a random position on the political compass */
    public ElementOnCompass() {
        setRandPos();
    }
    
    /** 
     * returns the X position on the political compass of the element
     * @return the X position on the political compass of the element
     */
    @Override
    public float getPosX() {
        return this.posX;
    }

     /** 
     * returns the Y position on the political compass of the element
     * @return the Y position on the political compass of the element
     */
    @Override
    public float getPosY() {
        return this.posY;
    }

    /**
     * Calculates and returns the Euclidean distance from this element to another element on the political compass
     * @param element the other element to calculate the distance from
     * @return the distance between this element and the other element on the political compass
     */
    public <T extends PoliticalCompassElement> double distanceFrom(T element) {
        return Math.sqrt(Math.pow((this.getPosX() - element.getPosX()), 2)
                + Math.pow((this.getPosY() - element.getPosY()), 2));
    }

    /** Sets a random position for the element on the political compass */
    public void setRandPos() {
        this.posX = -10 + (new Random()).nextFloat() * 20;
        this.posY = -10 + (new Random()).nextFloat() * 20;
        return;
    }
}
