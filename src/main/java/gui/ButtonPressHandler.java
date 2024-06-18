package gui;

import java.util.List;

import exceptions.MissingElectorsOrCandidatesException;
import exceptions.MissingPoliticalPartiesOrCandidatesException;
import exceptions.PreferencesNotSetException;
import exceptions.PromisesNotSpokenException;
import javafx.application.Platform;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import simulator.politicalCompassElements.PoliticalCompassElement;
import simulator.politicalCompassElements.PoliticalSubject;
import simulator.simulator.Simulator;

/**
 * Manages button press events in the GUI
 */
public class ButtonPressHandler {
    private Simulator simulator;
    private TextArea textArea;
    private TextField politicalPartiesTF;
    private TextField candidatesTF;
    private TextField electorsTF;
    private PoliticalCompass politicalCompass;
    private Slider slider1;
    private Slider slider2;
    private BarChart<String, Number> bc1;
    private BarChart<String, Number> bc2;
    private ChartSeriesHandler chartSeriesHandler = new ChartSeriesHandler();
    private ThreadHandler threadHandler = new ThreadHandler();

    /**
     * Constructs a ButtonPressHandler with the specified parameters
     * @param simulator simulator object to perform the actions
     * @param textArea text area to display information
     * @param politicalPartiesTF text field for the number of political parties
     * @param candidatesTF text field for the number of candidates
     * @param electorsTF text field for the number of electors
     * @param politicalCompass political compass object
     * @param slider1 slider for how prefered must a candidate be to be elected independently of their political party
     * @param slider2 slider for the influence of candidate's propagation on their party's preferences
     * @param bc1 bar chart for preferences before campaign
     * @param bc2 bar chart for preferences after campaign
     */
    protected ButtonPressHandler(Simulator simulator, TextArea textArea, TextField politicalPartiesTF, TextField candidatesTF, TextField electorsTF, PoliticalCompass politicalCompass, Slider slider1, Slider slider2, BarChart<String, Number> bc1, BarChart<String, Number> bc2) {
        this.simulator = simulator;
        this.textArea = textArea;
        this.politicalPartiesTF = politicalPartiesTF;
        this.candidatesTF = candidatesTF;
        this.electorsTF = electorsTF;
        this.politicalCompass = politicalCompass;
        this.slider1 = slider1;
        this.slider2 = slider2;
        this.bc1 = bc1;
        this.bc2 = bc2;
    }

    /** Handles the action when the "Generate" button is pressed */
    protected <T extends PoliticalCompassElement> void generateButtonPressed() {
            int nPoliticalParties = Integer.parseInt(politicalPartiesTF.getText());
            int nCandidates = Integer.parseInt(candidatesTF.getText());
            int nElectors = Integer.parseInt(electorsTF.getText());
            textArea.appendText("Generating...\n");
            bc1.getData().clear();
            bc2.getData().clear();
            simulator.generateElements(nPoliticalParties, nCandidates, nElectors);
            politicalCompass.displayAll((List<T>) simulator.getPoliticalParties(), (List<T>) simulator.getCandidates(), (List<T>) simulator.getElectors());
            textArea.appendText(nPoliticalParties + " political parties, " +
                    nCandidates + " politicians and " +
                    nElectors + " electors " + "generated.\n");
            return;
        }

    /** Handles the action when the "Regenerate" button is pressed */
    protected void regenerateButtonPressed() {
        simulator.reset();
        politicalCompass.clear();
        generateButtonPressed();
        return;
    }

    /** Handles the action when the "Match" button is pressed */
    protected void matchButtonPressed() {
        Thread matchThread = new Thread() {
            public void run() {
                textArea.appendText(("Matching...\n"));
                try {
                    simulator.matchPoliticalSubjects();
                    Platform.runLater(() -> {
                        threadHandler.endThread(this);
                        politicalCompass.connectWithMembers(simulator.getPoliticalParties(), Color.rgb(242, 96, 45));                    
                        textArea.appendText("Political subjects matched.\n");   
                    });
                } catch (MissingPoliticalPartiesOrCandidatesException exception) {
                    textArea.appendText(exception.getMessage());
                    Platform.runLater(() -> threadHandler.endThread(this));
                }
                return;
            }   
        };
        threadHandler.addThread(matchThread);
        return;
    }

    /** Handles the action when the "Set Preferences" button is pressed */
    protected void setPreferencesButtonPressed() {
        Thread setPreferencesThread;
        simulator.setMinToVote(slider1.getValue());
        setPreferencesThread = new Thread() {
            public void run() {
                textArea.appendText("Setting preferences...\n");
                try {
                    simulator.setPreferences();
                    Platform.runLater(() -> {
                        bc1.getData().clear();
                        chartSeriesHandler.addPreferenceSeries(bc1, simulator.getPoliticalParties(), simulator.getCandidates());
                        threadHandler.endThread(this);
                        textArea.appendText("Preferences set.\n");
                    });
                } catch (MissingElectorsOrCandidatesException exception) {
                    textArea.appendText(exception.getMessage());
                    Platform.runLater(() -> threadHandler.endThread(this));
                }
                return;
            }
        };
        threadHandler.addThread(setPreferencesThread);
        return;
    }

    /** Handles the action when the "Propagate" button is pressed */
    protected void propagateButtonPressed() {
        Thread propagateThread;
        simulator.setSecondaryImpact(slider2.getValue());
        propagateThread = new Thread() {
            public void run() {
                textArea.appendText("Initializing political campaign...\n");
                try {
                    simulator.propagate();
                    Platform.runLater(() -> {
                        bc2.getData().clear();
                        chartSeriesHandler.addPreferenceSeries(bc2, simulator.getPoliticalParties(), simulator.getCandidates());
                        threadHandler.endThread(this);
                        textArea.appendText("Political campaign over.\n");
                    });
                } catch (PreferencesNotSetException exception) {
                    textArea.appendText(exception.getMessage());
                    Platform.runLater(() -> threadHandler.endThread(this));
                }
                return;
            }
        };
        threadHandler.addThread(propagateThread);
        return;
}

    /** Handles the action when the "Elections" button is pressed */
    protected <T extends PoliticalSubject> void electionsButtonPressed() {
        Thread electionsThread;
        List<T> politicalParties = (List<T>) simulator.getPoliticalParties();
        List<T> candidates = (List<T>) simulator.getCandidates();
        electionsThread = new Thread() {
            public void run() { 
                textArea.appendText("Initializing elections...\n");
                try {
                    simulator.setVotes();
                    Platform.runLater(() -> {
                        SecondaryWindow secondaryWindow = new SecondaryWindow();
                        secondaryWindow.show();
                        chartSeriesHandler.addAllElectionsSeries(secondaryWindow.getLineChart(), politicalParties, candidates);                                
                        threadHandler.endThread(this);
                        textArea.appendText("Elections over.\n");
                    });
                } catch (PromisesNotSpokenException exception) {
                    textArea.appendText(exception.getMessage());
                    Platform.runLater(() -> threadHandler.endThread(this));
                }
                return;
            }
        };
        threadHandler.addThread(electionsThread);
        return;
    }
}
