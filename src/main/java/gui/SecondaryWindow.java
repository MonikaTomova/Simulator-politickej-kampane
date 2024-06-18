package gui;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.HBox;

/** Represents a secondary window for displaying output data */
public class SecondaryWindow extends Stage {
    private HBox root = new HBox();
    private NumberAxis aOIAxis = new NumberAxis();
    private NumberAxis votesAxis = new NumberAxis();
    private LineChart<Number,Number> lineChart = new LineChart<>(aOIAxis, votesAxis);

    /** Initializes a secondary window with a line chart for displaying output data */
    protected SecondaryWindow() {
        aOIAxis.setLabel("Average area of impact");
        votesAxis.setLabel("Votes recieved (political parties *0.1)");
        root.getChildren().addAll(lineChart);
        this.setTitle("Output");
        this.setScene(new Scene(root, 500, 800));
    }

    /**
     * Returns the line chart from the secondary window
     * @return The line chart for displaying output data
     */
    protected LineChart<Number, Number> getLineChart() {
        return this.lineChart;
    }
}
