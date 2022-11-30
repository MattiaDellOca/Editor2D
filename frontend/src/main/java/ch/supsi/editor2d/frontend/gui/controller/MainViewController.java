package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.event.FileOpenEvent;
import ch.supsi.editor2d.frontend.gui.event.ImageLoadedEvent;
import ch.supsi.editor2d.frontend.gui.event.RedoneEvent;
import ch.supsi.editor2d.frontend.gui.event.UndoneEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;

import static ch.supsi.editor2d.frontend.gui.controller.Start.SUPPORTED_FORMATS;

/**
 * Main view controller that handles the main view logic.
 */
public class MainViewController extends AbstractFXMLController {

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
     * File drop event handler
     */
    private EventHandler<FileOpenEvent> onFileOpened = event -> {
    };

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
        try {
            initEventHandlers();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load about view, please check the resources folder");
        }
    }

    private void initEventHandlers() throws IOException {

        // Open directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Export location");
        directoryChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        // Enable drag and drop to imagePane component
        imagePane.setOnDragOver(dragEvent -> {
            if (
                    dragEvent.getGestureSource() != imagePane
                            && dragEvent.getDragboard().hasFiles()
                            && isSupportedFormat(dragEvent.getDragboard().getFiles().get(0))
            ) {
                dragEvent.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
            }
            dragEvent.consume();
        });

        // Handle image drop
        imagePane.setOnDragDropped(dragEvent -> {
            boolean success = dragEvent.getDragboard().hasFiles()
                    && dragEvent.getDragboard().getFiles().size() == 1
                    && isSupportedFormat(dragEvent.getDragboard().getFiles().get(0));

            // handle response
            dragEvent.setDropCompleted(success);

            // If drop was successful, open the file
            if (success) {
                // Fire file dropped event

                onFileOpened.handle(new FileOpenEvent(dragEvent.getDragboard().getFiles().get(0), imagePane));
            }

            // Consume event
            dragEvent.consume();
        });
    }

    /**
     * Check if the file extension is supported
     *
     * @param file File to check
     * @return true if the file extension is supported, false otherwise
     */
    private boolean isSupportedFormat(File file) {
        // Check if file is supported
        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        for (String format : SUPPORTED_FORMATS) {
            if (extension.equals(format)) {
                return true;
            }
        }
        return false;
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
     * @return Pipeline Pane
     */
    public AnchorPane getPipelinePane() {
        return pipelinePane;
    }


    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if(event instanceof UndoneEvent) {
            // TODO: 26/11/2022 Implement undone operation through memento pattern
            System.out.println("Something was undone!");
        }

        if(event instanceof RedoneEvent) {
            // TODO: 26/11/2022 Implement redone operation through memento pattern
            System.out.println("Something was redone!");
        }
    }


}
