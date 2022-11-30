package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.event.RedoneEvent;
import ch.supsi.editor2d.frontend.gui.event.UndoneEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * Main view controller that handles the main view logic.
 */
public class MainViewController implements PropertyChangeListener {

    @FXML
    private MenuItem runPipelineMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem undoMenuItem;
    @FXML
    private MenuItem redoMenuItem;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem exportMenuItem;
    @FXML
    private Button zoomInButton;
    @FXML
    private Button zoomOutButton;

    /**
     * Scroll pane for filter selection FXML
     */
    @FXML
    private AnchorPane filtersListPane;

    /**
     * Scroll pane for pipeline FXML
     */
    @FXML
    private AnchorPane pipelinePane;

    /**
     * Image pane reference
     */
    @FXML
    private AnchorPane imagePane;

    public MenuItem getUndoMenuItem() {
        return undoMenuItem;
    }

    public MenuItem getRedoMenuItem() {
        return redoMenuItem;
    }

    public MenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public MenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public MenuItem getRunPipelineMenuItem() {
        return runPipelineMenuItem;
    }

    public MenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public MenuItem getExportMenuItem() {
        return exportMenuItem;
    }

    public Button getZoomInButton() {
        return zoomInButton;
    }

    public Button getZoomOutButton() {
        return zoomOutButton;
    }


    @FXML
    public void initialize() {
        // set event handlers + load about view
            // Open directory chooser
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Export location");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    /**
     * Get the image pane reference
     *
     * @return Image pane
     */
    public AnchorPane getImagePane() {
        return imagePane;
    }

    public AnchorPane getFiltersListPane() {
        return filtersListPane;
    }

    /**
     * Get the pipeline pane reference
     *
     * @return Pipeline Pane
     */
    public AnchorPane getPipelinePane() {
        return pipelinePane;
    }


    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event instanceof UndoneEvent) {
            // TODO: 26/11/2022 Implement undone operation through memento pattern
            System.out.println("Something was undone!");
        }

        if (event instanceof RedoneEvent) {
            // TODO: 26/11/2022 Implement redone operation through memento pattern
            System.out.println("Something was redone!");
        }
    }


}
