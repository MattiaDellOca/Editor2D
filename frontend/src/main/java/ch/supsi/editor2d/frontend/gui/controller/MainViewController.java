package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.frontend.gui.event.FileOpenEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Main view controller that handles the main view logic.
 */
public class MainViewController {

    /**
     * List of supported file extensions
     */
    private Collection<String> SUPPORTED_FORMATS;

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
     * Data model reference
     */
    private DataModel model;

    /**
     * File drop event handler
     */
    private EventHandler<FileOpenEvent> onFileOpened = event -> {
    };

    /**
     * About view action event handler
     */
    private EventHandler<ActionEvent> onAboutClicked = event -> {
    };

    private EventHandler<ActionEvent> onClose = event -> {};

    /**
     * Export file action event handler
     */
    private EventHandler<ActionEvent> onExportClicked = event -> {
    };

    /**
     * Refresh image callback
     */
    private EventHandler<ActionEvent> onPipelineFinishedRunning = event -> {};

    /**
     * Image pane reference
     */
    @FXML
    private Pane imagePane;

    /**
     * File chooser reference
     */
    private FileChooser fileChooser;

    /**
     * Initialize the model reference and set all the event handlers
     *
     * @param model Data model
     * @param supportedFormats List of supported file extensions
     */
    public void init(DataModel model, final Collection<String> supportedFormats) {

        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;

        // Setup supported formats
        this.SUPPORTED_FORMATS = supportedFormats;

        // set event handlers + load about view
        try {
            initEventHandlers();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load about view, please check the resources folder");
        }
    }

    private void initEventHandlers() throws IOException {
        // Load about page
        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("/view/aboutView.fxml"));
        Stage aboutStage = new Stage();
        aboutStage.setScene(new Scene(aboutLoader.load()));

        // Load file chooser
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open picture");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Image Files",
                        SUPPORTED_FORMATS.stream().map(s -> "*." + s).toArray(String[]::new)
                )
        );

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
     * Set the file dropped event handler
     *
     * @param event File drop event
     */
    public void setOnFileOpen(EventHandler<FileOpenEvent> event) {
        this.onFileOpened = event;
    }

    /**
     * Set the about clicked event handler
     * @param event About clicked event
     */
    public void setOnAboutClicked(EventHandler<ActionEvent> event) {
        this.onAboutClicked = event;
    }

    /**
     * Set the export clicked event handler
     * @param event Export clicked event
     */
    public void setOnExportClicked(EventHandler<ActionEvent> event) {
        this.onExportClicked = event;
    }

    /**
     * Set the pipeline finished running event handler
     * @param event Pipeline finished running event handler
     */
    public void setOnPipelineFinishedRunning(EventHandler<ActionEvent> event) {
        this.onPipelineFinishedRunning = event;
    }

    /**
     * Set the close event handler
     * @param event Close event handler
     */
    public void setOnClose(EventHandler<ActionEvent> event) {
        this.onClose = event;
    }

    /**
     * Get the image pane reference
     *
     * @return Image pane
     */
    public Pane getImagePane() {
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

    /**
     * Handle the zoom out action
     *
     * @param actionEvent Action event
     */
    public void zoomOut(ActionEvent actionEvent) {
        System.out.println("ZOOMING OUT: " + actionEvent);
    }

    /**
     * Handle the zoom in action
     *
     * @param actionEvent Action event
     */
    public void zoomIn(ActionEvent actionEvent) {
        System.out.println("ZOOMING IN: " + actionEvent);
    }

    /**
     * Handle the about menu action
     */
    public void onAboutMenu(ActionEvent e) {
        onAboutClicked.handle(e);
    }

    public void onRunPipeline() {
        try {
            // Run pipeline again
            model.refreshPipeline();
            // Then, refresh the image
            onPipelineFinishedRunning.handle(new ActionEvent());
        } catch (PipelineException e) {
            System.err.println("Unable to refresh pipeline: " + e.getMessage());
            ErrorAlert.showError("Unable to refresh pipeline: " + e.getMessage());
        }
    }

    public void onOpen() {
        // Open file browser
        File file = fileChooser.showOpenDialog(imagePane.getScene().getWindow());

        // additional file extension check
        if (file != null && isSupportedFormat(file)) {
            // Fire file dropped event
            onFileOpened.handle(new FileOpenEvent(file, imagePane));
            // Then, refresh the image
            onPipelineFinishedRunning.handle(new ActionEvent());
        }
    }

    public void onClose() {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    /**
     * Handle image export action
     */
    public void onExport(ActionEvent e) {
        onExportClicked.handle(e);
    }
}
