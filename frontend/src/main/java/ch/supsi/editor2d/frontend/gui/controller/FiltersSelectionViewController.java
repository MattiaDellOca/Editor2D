package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.*;
import ch.supsi.editor2d.frontend.exception.ImageNotLoadedException;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.frontend.gui.event.ImageUpdatedEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class FiltersSelectionViewController {
    @FXML
    private ListView<Filter> filterSelectionList;

    private DataModel model;

    private EventHandler<ImageUpdatedEvent> imageUpdated = event -> {};

    public EventHandler<ImageUpdatedEvent> getOnImageUpdated() { return this.imageUpdated; }

    public void setOnImageUpdated(EventHandler<ImageUpdatedEvent> event) { this.imageUpdated = event; }

    public void initModel(DataModel model) {
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;

        //Setting filters as dataModel list
        filterSelectionList.setItems(model.getActualFiltersList());

        //Setting personalized ListViewCell
        filterSelectionList.setCellFactory(taskListView -> new FilterCell(model));

        // Add EventHandler to filtersSelectionView
        filterSelectionList.setOnMouseClicked(mouseEvent -> {
            Filter filter = filterSelectionList.getSelectionModel().getSelectedItem();

            // If the user selected an empty field object, do nothing
            if(filter == null)
                return;

            try {
                // Add the selected filter to the pipeline
                model.addFilterPipeline(filter);
            }  catch (ImageNotLoadedException e) {
                System.err.println("Unable to apply filter: Please load an image before applying a filter.");
                ErrorAlert.showError("Unable to apply filter: Please load an image before applying a filter.");
            }
        });

    }
}
