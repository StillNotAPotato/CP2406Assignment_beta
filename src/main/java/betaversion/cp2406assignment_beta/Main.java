package betaversion.cp2406assignment_beta;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * CP2406 Assignment - Tan Beng Siang
 * Beta version
 * Main Class to be run using the Rainfall Visualiser/Analyser classes.
 * Creates an interactive app using JavaFX.
 * <p>
 * See https://github.com/StillNotAPotato/CP2406Assignment_betarepository
 */
public class Main extends Application {

    private static RainfallData rainfallData = new RainfallData();
    private static final RainfallAnalyser rainfallAnalyser = new RainfallAnalyser();

    private final Stage homeStage = new Stage();
    private final Stage visualiserStage = new Stage();
    private final Stage helpStage = new Stage();
    private final Label statusBar = new Label();

    /**
     * Main method that starts the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Main JavaFX method. Overrides the original method of the JavaFX package.
     * Builds the three main stages of the app.
     */
    @Override
    public void start(Stage stage) {

        // Build the stages to be used in the program
        buildHomeStage();
        buildVisualiserStage();
        buildHelpStage();
        statusBar.setText("Please load a rainfall csv to be analysed");

        // Set the stage to the Home Scene and show it
        homeStage.show();
    }

    /**
     * Builds the stage used for the Home Stage
     */
    private void buildHomeStage() {
        // Set up all the labels and put them in a VBox
        Label message = new Label("Welcome to the Rainfall Visualiser");
        message.setFont(new Font(20));
        statusBar.setAlignment(Pos.CENTER);
        VBox labelBar = new VBox(message, statusBar);
        labelBar.setAlignment(Pos.CENTER);

        // Set up all the buttons and put them in a HBox
        Button startButton = new Button("Start Visualiser");
        Button loadButton = new Button("Load Rainfall Data");
        Button quitButton = new Button("Quit");

        // Add tooltips to each of the main buttons
        Tooltip startTooltip = new Tooltip(), loadTooltip = new Tooltip(), quitTooltip = new Tooltip();
        startTooltip.setText("Starts the Rainfall Visualiser");
        Tooltip.install(startButton, startTooltip);
        loadTooltip.setText("Load the Rainfall Data from a file on your computer");
        Tooltip.install(loadButton, loadTooltip);
        quitTooltip.setText("Exits the Rainfall Visualiser");
        Tooltip.install(quitButton, quitTooltip);

        // Assign the actions to each of the main buttons
        startButton.setOnAction(e -> {
            homeStage.hide();
            visualiserStage.show();
        });
        loadButton.setOnAction(e -> rainfallData = loadRainfallData());
        quitButton.setOnAction(e -> Platform.exit());

        HBox buttonBar = new HBox(50, startButton, loadButton, quitButton);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPrefHeight(50);

        MenuButton menuButton = buildMenuButton();
        MenuBar menuBar = buildMenuBar();

        // Set up the home root and scene
        BorderPane homeRoot = new BorderPane();
        homeRoot.setCenter(labelBar);
        homeRoot.setBottom(buttonBar);
        homeRoot.setRight(menuButton);
        homeRoot.setTop(menuBar);
        homeRoot.setStyle("-fx-border-width: 2px; -fx-border-color: #444");

        // Build the home scene and assign it to the home stage
        Scene homeScene = new Scene(homeRoot, 600, 400);
        homeStage.setScene(homeScene);
        homeStage.setTitle("CP2406 Assignment - Tan Beng Siang");
        homeStage.centerOnScreen();
        homeStage.setResizable(false);

    }

    /**
     * Creates the Stage for the Rainfall Visualiser.
     * Get the StackedBarChart from the Rainfall Visualiser class
     */
    private void buildVisualiserStage() {
        BorderPane visualiserRoot = new BorderPane(RainfallVisualiser.getRainfallBarChart(rainfallData));
        Button returnButton = new Button("Close Visualiser");

        MenuButton menuButton = buildMenuButton();
        menuButton.setPopupSide(Side.TOP);

        HBox visualiserHBox = new HBox(100, returnButton, menuButton);
        visualiserHBox.setAlignment(Pos.CENTER);
        visualiserHBox.setPrefHeight(30);

        MenuBar menuBar = buildMenuBar();

        visualiserRoot.setTop(menuBar);
        visualiserRoot.setBottom(visualiserHBox);
        visualiserRoot.setStyle("-fx-border-width: 2px; -fx-border-color: GRAY");
        Scene visualiserScene = new Scene(visualiserRoot, 1200, 600);

        visualiserStage.setScene(visualiserScene);
        visualiserStage.setTitle("Rainfall Visualiser");
        visualiserStage.centerOnScreen();
        visualiserStage.setResizable(true);

        returnButton.setOnAction(actionEvent -> {
            visualiserStage.hide();
            homeStage.show();
        });
    }

    /**
     * Builds the help stage. Used to give the user basic instructions
     * on running the app.
     **/
    private void buildHelpStage() {
        TabPane helpPane = new TabPane();   // create object using JavaFx TabPane class called helpPane
        helpPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        helpPane.setTabDragPolicy(TabPane.TabDragPolicy.FIXED);
        helpPane.setStyle("-fx-border-width: 2px; -fx-border-color: GRAY");

        Label generalHelp = new Label("""
                Welcome to the Rainfall visualiser app.
                                
                This application analyses recorded rainfall data in the form of a CSV file
                and displays the data as a bar chart on
                the screen of a visualiser.
                             
                It makes use of Apache's Commons CSV module.
                                
                If you want a more extensive explanation, please pick a specific tab.""");
        Label loadingHelp = new Label("""
                 A file may be loaded into the rainfall visualiser in three ways.

                 The first method is to use the load option at the top page.
                 This will launch a file explorer window, allowing the user to
                 select a new file to be
                 analyzed and displayed with a graph on the visualiser.
                 This option is accessible from both the home and visualiser screens.

                 The second method is to press the button on the home screen.
                 This also launches the file explorer,
                 allowing you to add a new file.

                The third option comes from the drop down menu on the left side of the home screen.
                This is a list of previously loaded and saved files
                that are saved within the project.""");
        Label savingHelp = new Label("""
                To save a file, the user must choose the save file option
                at the top of the program screen. Before the software can
                store any rainfall data, it must first
                load a file. This recorded information is saved in the project
                and can only be accessed via the home screen's drop down
                list on the right.

                This functionality was included to save
                the user from having to manually
                to locate a file each time they wised to
                examine the graph in the visualiser.""");
        Label visualiserHelp = new Label("""
                The supplied rainfall data is
                shown as a bar chart by the visualiser.
                The user can obtain the current value
                of an entry by hovering their cursor
                over its bar. This number comprises
                the date, as well as whether it is a
                minimum, maximum, or total amount
                in millimeters. Using the menu bar at
                the top of the screen, a new file
                may be imported into the chart from the
                visualiser screen. On this screen, the
                bar chart will automatically refresh.""");

        Tab general = new Tab("General", generalHelp);
        Tab load = new Tab("Loading", loadingHelp);
        Tab save = new Tab("Saving", savingHelp);
        Tab visualiser = new Tab("Visualiser", visualiserHelp);

        helpPane.getTabs().addAll(general, load, save, visualiser);
        Button close = new Button("Close Help");
        close.setOnAction(e -> helpStage.hide());
        VBox helpBox = new VBox(helpPane, close);
        helpBox.setAlignment(Pos.CENTER);

        Scene helpScene = new Scene(helpBox, 400, 350);
        helpStage.setScene(helpScene);
        helpStage.setTitle("Help for Rainfall Visualiser");
        helpStage.setResizable(false);
        helpStage.initStyle(StageStyle.UTILITY);

        // Close help screen when focus is lost from home stage
        helpStage.focusedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (!newVal) {
                helpStage.hide();
            }
        });
    }

    /**
     * Build menu button for home stage
     * Used to load previously saved and analysed rainfall data.
     */
    private MenuButton buildMenuButton() {
        MenuButton menuButton = new MenuButton();
        menuButton.setText("Saved Rainfall Data");
        menuButton.setAlignment(Pos.CENTER);

        File f = new File("src/main/resources/betaversion/cp2406assignment_beta/analysedrainfalldata");
        if (Objects.requireNonNull(f.list()).length == 0) {
            MenuItem noData = new MenuItem("No saved analysed rainfall data");
            menuButton.getItems().add(noData);  // add item to Menu
        } else {
            for (String filename : Objects.requireNonNull(f.list())) {
                MenuItem choice = new MenuItem(filename);   // create object of a reference type to store filename
                choice.setOnAction(e -> {
                    // Windows OS users can use either "/" or "\".
                    String path = f.getAbsolutePath() + "\\" + filename;
                    try {
                        rainfallData = rainfallAnalyser.getAnalysedRainfallData(path);
                        buildVisualiserStage();
                        statusBar.setText(filename + " successfully loaded");
                    } catch (IOException ex) {
                        statusBar.setText(filename + " failed to load.\n " + ex.getMessage());
                    }
                });
                menuButton.getItems().add(choice);  // add items to menu bar
            }
        }
        return menuButton;
    }

    /**
     * Build the menu bar for the Home stage and the Visualiser Stage.
     * Acts as the only way to change the Rainfall data set while on
     * the visualiser stage. Can also be used to load the help stage
     * or close the app on any stage.
     */
    private MenuBar buildMenuBar() {
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        MenuItem open = new MenuItem("Open");
        open.setOnAction(e -> loadRainfallData());

        MenuItem save = new MenuItem("Save");
        save.setOnAction(e -> {
            String filename = rainfallAnalyser.saveRainfallData(rainfallData);
            buildHomeStage();
            buildVisualiserStage();
            if (filename == null)
                statusBar.setText("No file loaded to save");    // notify user that the selected file has already been
                // saved
                // Or if the user selects the save button from the drop-down toolbar without selecting a file
            else
                statusBar.setText(filename + " successfully saved");
        });

        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> Platform.exit());

        fileMenu.getItems().addAll(open, save, close);  // add menu item to drop down menu

        MenuItem help = new MenuItem("Help");
        help.setOnAction(e -> helpStage.show());
        helpMenu.getItems().add(help);

        return menuBar;
    }

    /**
     * Opens a file explorer menu and allows the user load in a csv rainfall file
     * for analysis.
     * Res
     * The Rainfall Analyser class is responsible for catching any errors that occur.
     * The studied data set that will be represented by the bar chart on the visualiser stage is returned.
     * Each time it is executed, it updates the Visualiser stage.
     */
    private RainfallData loadRainfallData() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(homeStage);
        // Check whether file selector successfully loaded a file
        if (file == null) {
            statusBar.setText("No file loaded");
            return null;
        }
        String path = file.getAbsolutePath();

        try {
            rainfallData = rainfallAnalyser.analyseRainfallData(path);
            statusBar.setText(rainfallData.getFilename() + " successfully loaded");
            buildVisualiserStage();
        } catch (Exception err) {
            statusBar.setText("Failed to load " + file.getName() + "\n" +
                    err.getMessage());
        }
        return rainfallData;
    }

}
