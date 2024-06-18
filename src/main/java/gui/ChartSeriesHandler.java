package gui;

import java.util.List;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import simulator.ElectionPromise;
import simulator.politicalCompassElements.Candidate;
import simulator.politicalCompassElements.JudgedElement;
import simulator.politicalCompassElements.PoliticalParty;
import simulator.politicalCompassElements.PoliticalSubject;

/** Handles adding series to JavaFX charts */
public class ChartSeriesHandler {

    /**
     * Returns pluralized version of given string
     * @param singular string to be pluralized
     * @return pluralized string
     */
    private String pluralize(String singular) {
        if (singular.endsWith("y")) {
            return singular.substring(0, singular.length() - 1) + "ies";
        } else {
            return singular + "s";
        }
    }

    /** 
     * Displays the preferences of given political parties and candidates on given bar chart (preferences of political parties are devided by 10 for better clearance of the chart)
     * @param barChart bar chart to display the data on
     * @param politicalParties political parties whose preferences to display
     * @param candidates candidates whose preferences to display
     */
    protected void addPreferenceSeries(BarChart<String, Number> barChart, List<PoliticalParty> politicalParties, List<Candidate> candidates) {
        XYChart.Series<String, Number> politicalPartySeries = new XYChart.Series<>();
        XYChart.Series<String, Number> candidateSeries = new XYChart.Series<>();
        int i = 0;

        politicalPartySeries.setName("Political parties");
        candidateSeries.setName("Candidates");
        
        for (PoliticalParty politicalParty : politicalParties) {
            
            for (Candidate member : politicalParty.getMembers()) {
                politicalPartySeries.getData()
                .add(new XYChart.Data<String, Number>(Integer.toString(i),
                politicalParty.getPreferences() / 10));
                candidateSeries.getData()
                .add(new XYChart.Data<String, Number>(Integer.toString(i),
                member.getPreferences()));
                i++;
            }
            
            politicalPartySeries.getData()
            .add(new XYChart.Data<String, Number>("-" + Integer.toString(i), (Number) 1));
            candidateSeries.getData()
                    .add(new XYChart.Data<String, Number>("-" + Integer.toString(i), (Number) 1));
        }

        for (Candidate candidate : candidates) {
            if (candidate.getParty() == null) {
                candidateSeries.getData()
                .add(new XYChart.Data<String, Number>(Integer.toString(i),
                candidate.getPreferences()));
                i++;
            }
        }

        barChart.getData().addAll(politicalPartySeries, candidateSeries);
            
        return;
    }
    
    /** 
     * Displays the relationship between the political subjects' campaign strategy and their success int the elections on given line chart (preferences of political parties are devided by 10 for better clearance of the chart)
     * @param lineChart line chart to display the data on
     * @param politicalSubjects political subjects to display
     * @param seriesName name of the group of the political subjects given (e.g. "Political parties")
     */
    private <T extends PoliticalSubject> void addElectionsSeries(LineChart<Number,Number> lineChart, List<T> politicalSubjects, String seriesName) {
        
        XYChart.Series<Number, Number> series = new XYChart.Series<>(); 
        List<ElectionPromise<? extends JudgedElement>> promises;
        float averageAOI;

        series.setName(seriesName);

        for (T politicalSubject : politicalSubjects) {
            promises = politicalSubject.getPromieses();
            averageAOI = 0;
            for (ElectionPromise<? extends JudgedElement> promise : promises) {
                averageAOI += promise.getAreaOfImpact();
            }
            if (promises.size() > 1) {
                averageAOI /= promises.size();
            }
            series.getData().add(new XYChart.Data<>(averageAOI, politicalSubject.getVotes() / 10));
        }

        lineChart.getData().add(series);

        return;
    }

    /** 
     * Displays the relationship between the given political subjects' campaign strategy and their success in the elections on given line chart (preferences of political parties are devided by 10 for better clearance of the chart)
     * @param lineChart line chart to display the data on
     * @param lists lists of the political subjects to process
     */
    protected <T extends PoliticalSubject> void addAllElectionsSeries(LineChart<Number,Number> lineChart, List<T>... lists) {
        Class<?> elementType;
        String seriesName;
        for (List<T> list : lists) {
            if (!list.isEmpty()) {
                elementType = list.get(0).getClass();
                if (PoliticalSubject.class.isAssignableFrom(elementType)) {
                    seriesName = pluralize(elementType.getSimpleName());
                    addElectionsSeries(lineChart, list, seriesName);
                }
            }
        }
        return;
    }
}