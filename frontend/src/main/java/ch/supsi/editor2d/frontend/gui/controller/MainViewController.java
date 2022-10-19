package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.event.FileDropEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.File;

public class MainViewController {

    private final String[] SUPPORTED_FORMATS = new String[] { "ppm", "pgm", "pbm", "tga" };

    private DataModel model;

    private EventHandler<FileDropEvent> fileDropped = event -> {};

    @FXML
    private Pane imagePane;

    public void setOnFileDropped(EventHandler<FileDropEvent> event) {
        this.fileDropped = event;
    }

    public EventHandler<FileDropEvent> getOnFileDropped() {
        return this.fileDropped;
    }

    public void initModel(DataModel model){

        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        // Enable drag and drop to imagePane component
        imagePane.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() != imagePane && dragEvent.getDragboard().hasFiles()) {
                // Accept both copy and move, whatever user chooses
                dragEvent.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
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

    public void zoomOut(ActionEvent actionEvent) {
        System.out.println("ZOOMING OUT: " + actionEvent);
    }

    public void zoomIn(ActionEvent actionEvent) {
        System.out.println("ZOOMING IN: " + actionEvent);
    }

    public Pane getImagePane() {
        return imagePane;
    }
}
