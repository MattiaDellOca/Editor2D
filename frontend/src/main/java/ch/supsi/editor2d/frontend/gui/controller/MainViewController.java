package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.helper.FilterPipeline;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.filter.GrayscaleFilter;
import ch.supsi.editor2d.backend.model.filter.SepiaFilter;
import ch.supsi.editor2d.backend.model.pipeline.Pipeline;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.frontend.gui.event.FileDropEvent;
import ch.supsi.editor2d.frontend.gui.event.ImageUpdatedEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainViewController {

    private final String[] SUPPORTED_FORMATS = new String[] { "ppm", "pgm", "pbm", "tga" };

    private DataModel model;

    private EventHandler<FileDropEvent> fileDropped = event -> {};

    private EventHandler<ImageUpdatedEvent> imageUpdated = event -> {};

    @FXML
    private Pane imagePane;

    @FXML
    private ListView<String> filtersSelectionView;

    @FXML
    private ListView<String> filtersSelectedView;

    private final List<String> filters = new ArrayList<>(List.of(new String[]{"Flip", "Grayscale", "Sepia"}));

    private final Pipeline<ImageWrapper, FilterTaskResult> filterPipeline = new FilterPipeline();

    public void setOnFileDropped(EventHandler<FileDropEvent> event) {
        this.fileDropped = event;
    }

    public void setOnImageUpdated(EventHandler<ImageUpdatedEvent> event) { this.imageUpdated = event; }

    public EventHandler<FileDropEvent> getOnFileDropped() {
        return this.fileDropped;
    }

    public EventHandler<ImageUpdatedEvent> getOnImageUpdated() { return this.imageUpdated; }

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

        // Add filters to filtersSelectionView
        filtersSelectionView.getItems().addAll(filters);
        // Add ChangeListener to filtersSelectionView
        filtersSelectionView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, filter) -> {
            filtersSelectedView.getItems().add(filter);
            ImageWrapper image = model.getImageLoaded();

            switch (filter) {
                case "Flip" -> filterPipeline.add(new FilterTask(new FlipFilter()));
                case "Grayscale" -> filterPipeline.add(new FilterTask(new GrayscaleFilter()));
                case "Sepia" -> filterPipeline.add(new FilterTask(new SepiaFilter()));
            }

            ImageWrapper i = filterPipeline.run(image).getResult();

            getOnImageUpdated().handle(new ImageUpdatedEvent(i, imagePane));

        });


        // Add ChangeListener to filtersSelectedView
        filtersSelectedView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String filter = filtersSelectedView.getSelectionModel().getSelectedItem();
            filtersSelectionView.getItems().add(filter);
            //filtersSelectedView.getItems().remove(filter);
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
