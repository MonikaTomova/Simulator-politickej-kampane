package gui;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulator.politicalCompassElements.PoliticalCompassElement;
import simulator.politicalCompassElements.PoliticalParty;
import simulator.politicalCompassElements.Candidate;
import simulator.politicalCompassElements.Elector;

/** Handles the display of political compass elements on the political compass */
public class PoliticalCompass {

    private GraphicsContext gc;
    private int scale;

    protected PoliticalCompass(GraphicsContext gc, int scale) {
        this.gc = gc;
        this.scale = scale;
    }

    /** Clears the political compass */
    protected void clear() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    /** Draws the political compass grid */    
    protected void drawCompassGrid() {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4.0);
        gc.strokeRect(1, 1, (scale * 20) + 1, (scale * 20) + 1);
        gc.strokeRect(1, 1, scale * 10, scale * 10);
        gc.strokeRect((scale * 10) + 1, (scale * 10) + 1, (scale * 20) + 1, (scale * 20) + 1);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1.0);
        
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 20; col++) {
                gc.strokeRect(scale * col + 1, scale * row + 1, scale, scale);
            }
        }

        return;
    }

    /** 
     * Displays all given political parties, candidates and electors on the political compass
     * @param politicalParties political parties to display on the political compass
     * @param candidates candidates to display on the political compass
     * @param electors electors to display on the political compass
     */
    protected <T extends PoliticalCompassElement> void displayAll(List<T>... lists) { 
        for (List<T> list : lists) {
            if (!list.isEmpty()) {
                if (list.get(0) instanceof Elector) {
                    display(list, Color.rgb(86, 182, 87, (float) 0.1), (float) 0.5);
                }
                else if (list.get(0) instanceof Candidate) {
                    display(list, Color.rgb(249, 165, 2), (float) 0.3);
                }
                else if (list.get(0) instanceof PoliticalParty) {
                    display(list, Color.rgb(242, 96, 45), (float) 0.5);
                }
                else {
                    display(list, Color.GRAY, (float) 0.3);
                }
            }
        }
        drawCompassGrid();
        return;
    }

    /** 
     * Displays all the given political compass elements on the political compass as bubbles
     * @param politicalCompassElements list of all the elements to be displayed
     * @param color color of the bubbles
     * @param size size of the bubbles
     */
    protected <T extends PoliticalCompassElement> void display(List<T> politicalCompassElements, Color color, float size) {
        
        gc.setFill(color);

        for (T element : politicalCompassElements) {
            gc.fillOval((element.getPosX() + 10) * scale + 1 - size*scale/2, 
            (element.getPosY() + 10) * scale + 1 - size*scale/2,
            size * scale, size * scale);
        }

        return;
    }

    
    /** 
     * Connects the given political parties with their members on the political compass
     * @param politicalParties political parties to connect with their members
     * @param color color of the lines
     */
    protected void connectWithMembers(List<PoliticalParty> politicalParties, Color color) {
        
        List<Candidate> members;
        gc.setStroke(color);
        for (PoliticalParty politicalParty : politicalParties) {
            members = politicalParty.getMembers();
            for (Candidate member : members) {
                connect(politicalParty, member);
            }
        }

        return;
    }

    
    /** 
     * Draws a line between the 2 given elements on the political compass
     * @param element1 element to connect with the second element
     * @param element2 element to connect with the first element
     */
    protected <T extends PoliticalCompassElement> void connect(T element1, T element2) {
        gc.strokeLine(
                (element1.getPosX() + 10) * scale + 1,
                (element1.getPosY() + 10) * scale + 1,
                (element2.getPosX() + 10) * scale + 1,
                (element2.getPosY() + 10) * scale + 1);
        return;
    }
}
