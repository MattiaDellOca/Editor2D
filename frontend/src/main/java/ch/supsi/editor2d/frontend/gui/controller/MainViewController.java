package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.event.FileDropEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.File;

/**
 * Main view controller that handles the main view logic.
 */
public class MainViewController {

    /**
     * List of supported file extensions
     */
    private final String[] SUPPORTED_FORMATS = new String[] { "ppm", "pgm", "pbm", "tga" };

    /**
     * Data model reference
     */
    private DataModel model;

    /**
     * File drop event handler
     */
    private EventHandler<FileDropEvent> fileDropped = event -> {};

    /**
     * About menu event handler
     */
    private EventHandler<ActionEvent> onAboutMenuClicked = event -> {};

    /**
     * Image pane reference
     */
    @FXML
    private Pane imagePane;

    /**
     * Initialize the model reference
     * @param model Data model
     */
    public void initModel(DataModel model){

        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        // Enable drag and drop to imagePane component
        imagePane.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() != imagePane && dragEvent.getDragboard().hasFiles()) {
                // Check if file is supported
                File file = dragEvent.getDragboard().getFiles().get(0);
                String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                for (String format : SUPPORTED_FORMATS) {
                    if (extension.equals(format)) {
                        dragEvent.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
                        break;
                    }
                }
            }
            dragEvent.consume();
        });

        // Handle image drop
        imagePane.setOnDragDropped(dragEvent -> {
            boolean success = false;
            if (dragEvent.getDragboard().hasFiles()) {
                // Check if file has a valid extension
                File actualFile = dragEvent.getDragboard().getFiles().get(0);
                String fileName = actualFile.getName();
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

                // Check if file format is supported
                for (String format : SUPPORTED_FORMATS) {
                    if (extension.equals(format)) {
                        // If an image is already loaded, remove it
                        getOnFileDropped().handle(new FileDropEvent(actualFile, imagePane));

                        // Set success flag
                        success = true;
                        break;
                    }
                }
            }
            dragEvent.setDropCompleted(success);
            dragEvent.consume();
        });

        this.model = model;
    }


    /**
     * Set the file dropped event handler
     * @param event File drop event
     */
    public void setOnFileDropped(EventHandler<FileDropEvent> event) {
        this.fileDropped = event;
    }

    /**
     * Get the file dropped event handler
     * @return File drop event handler
     */
    public EventHandler<FileDropEvent> getOnFileDropped() {
        return this.fileDropped;
    }

    /**
     * Set the about menu event handler
     * @param event About menu event
     */
    public void setOnAboutMenuClicked(EventHandler<ActionEvent> event) {
        this.onAboutMenuClicked = event;
    }

    /**
     * Get the about menu event handler
     * @return About menu event handler
     */
    public EventHandler<ActionEvent> getOnAboutMenuClicked() {
        return this.onAboutMenuClicked;
    }

    /**
     * Get the image pane reference
     * @return Image pane
     */
    public Pane getImagePane() {
        return imagePane;
    }

    /**
     * Handle the zoom out action
     * @param actionEvent Action event
     */
    public void zoomOut(ActionEvent actionEvent) {
        System.out.println("ZOOMING OUT: " + actionEvent);
    }

    /**
     * Handle the zoom in action
     * @param actionEvent Action event
     */
    public void zoomIn(ActionEvent actionEvent) {
        System.out.println("ZOOMING IN: " + actionEvent);
    }

    /**
     * Handle the about menu action
     * @param actionEvent Action event
     */
    public void onAboutMenu(ActionEvent actionEvent) {
        // Run related event handler
        getOnAboutMenuClicked().handle(actionEvent);
    }

    public void onRunPipeline(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    public void onRedo(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    public void onUndo(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    public void onSave(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    public void onOpen(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    public void onClose(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }
}
