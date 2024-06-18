package gui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    
    /** 
     * Launches the application
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    
    /** 
     * Initializes the simulator and gui
     * @param mainWindow
     * @throws IOException
     */
    @Override
    public void start(Stage mainWindow) throws IOException {
        Gui gui = new Gui(mainWindow);
        gui.buildGUI();
    }

}