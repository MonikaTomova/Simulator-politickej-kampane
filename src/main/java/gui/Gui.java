package gui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import simulator.simulator.Simulator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

/** Handles the graphical user interface for the Political Campaign Simulator */
public class Gui {
    private Stage mainWindow;
    private FlowPane flowPane = new FlowPane();
    private int scale = 25;
    private HBox root = new HBox();
    private Button generateButton = new Button("Generate");
    private Button regenerateButton = new Button("Regenerate");
    private Button matchButton = new Button("Match");
    private Button setPreferencesButton = new Button("Set preferences");
    private Button propagateButton = new Button("Propagate");
    private Button electionsButton = new Button("Elections");
    private TextField politicalPartiesTF = new TextField("10");
    private TextField candidatesTF = new TextField("500");
    private TextField electorsTF = new TextField("4000");
    private Label politicalPartiesLabel = new Label("Political Parties");
    private Label candidatesLabel = new Label("Candidates");
    private Label electorsLabel = new Label("Electors");
    private Label slider1Label = new Label(
            "How prefered must a candidate be to be elected independently of their political party:");
    private Label slider2Label = new Label(
            "Influence of candidate's propagation on their party's preferences:                   ");
    private Slider slider1 = new Slider(70, 110, 90);
    private Slider slider2 = new Slider(0.1, 0.9, 0.5);
    private Canvas canvas = new Canvas(scale * 20 + 2, scale * 20 + 2);
    private PoliticalCompass politicalCompass = new PoliticalCompass(canvas.getGraphicsContext2D(), scale);
    private TextArea textArea = new TextArea();
    private CategoryAxis politicalSubjectAxis1 = new CategoryAxis();
    private CategoryAxis politicalSubjectAxis2 = new CategoryAxis();
    private NumberAxis votesAxis1 = new NumberAxis();
    private NumberAxis votesAxis2 = new NumberAxis();
    private BarChart<String, Number> bc1 = new BarChart<>(politicalSubjectAxis1, votesAxis1);
    private BarChart<String, Number> bc2 = new BarChart<>(politicalSubjectAxis2, votesAxis2);
    private ButtonPressHandler buttonPressHandler;

    /**
     * Initializes the GUI with the main window and a simulator instance
     * @param mainWindow main window of GUI
     * @param simulator simulator instance used in GUI
     */
    protected Gui(Stage mainWindow) {
        this.mainWindow = mainWindow;
        this.buttonPressHandler = new ButtonPressHandler(new Simulator(), textArea, politicalPartiesTF, candidatesTF, electorsTF, politicalCompass, slider1, slider2, bc1, bc2);
    }

    /** Initializes GUI buttons and their event handlers */
    private void initializeButtons() {
        generateButton.setOnAction(e -> buttonPressHandler.generateButtonPressed());
        regenerateButton.setOnAction(e -> buttonPressHandler.regenerateButtonPressed());
        matchButton.setOnAction(e -> buttonPressHandler.matchButtonPressed());
        setPreferencesButton.setOnAction(e -> buttonPressHandler.setPreferencesButtonPressed());
        propagateButton.setOnAction(e -> buttonPressHandler.propagateButtonPressed());
        electionsButton.setOnAction(e -> buttonPressHandler.electionsButtonPressed());
        return;
    }

    /** Bulids GUI (main window) */
    protected void buildGUI() {
        slider1.setShowTickLabels(true);
        slider1.setShowTickMarks(true);
        slider1.setMajorTickUnit(10);
        slider2.setShowTickLabels(true);
        slider2.setShowTickMarks(true);
        slider2.setMajorTickUnit(0.1);

        politicalCompass.drawCompassGrid();

        textArea.setPrefWidth(500);
        textArea.setPrefHeight(90);

        politicalSubjectAxis1.setLabel("Political subjects (each candidate in front of their party)");
        politicalSubjectAxis2.setLabel("Political subjects (each candidate in front of their party)");
        votesAxis1.setLabel("Votes recieved (political parties *0.1)");
        votesAxis2.setLabel("Votes recieved (political parties *0.1)");
        bc1.setTitle("Preferences before campaign");
        bc1.getXAxis().setTickLabelsVisible(false);
        bc1.setAnimated(false);
        bc2.setTitle("Preferences after campaign");
        bc2.getXAxis().setTickLabelsVisible(false);
        bc2.setAnimated(false);

        flowPane.getChildren().addAll(generateButton, regenerateButton, matchButton, setPreferencesButton, propagateButton,
                electionsButton, politicalPartiesTF,
                politicalPartiesLabel, candidatesTF, candidatesLabel,
                electorsTF, electorsLabel, slider1Label, slider1, slider2Label, slider2, canvas, textArea);
        root.getChildren().addAll(flowPane, bc1, bc2);

        initializeButtons();
                
        mainWindow.setTitle("Political Campaign Simulator");
        mainWindow.setScene(new Scene(root, 1600, 850));
        mainWindow.show();

        return;
    }

}