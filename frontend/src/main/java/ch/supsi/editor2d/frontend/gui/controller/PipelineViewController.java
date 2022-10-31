package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class PipelineViewController {
    @FXML
    private ListView<Task<ImageWrapper, FilterTaskResult>> filterPipelineList;
    private DataModel model;

    private EventHandler<ActionEvent> onFilterRemovedSuccessfully = event -> {};

    public void initModel(DataModel model) {
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;

        //Setting filter pipeline as dataModel list
        filterPipelineList.setItems(model.getActualFiltersPipeline());

        //Setting personalized ListViewCell
        filterPipelineList.setCellFactory(taskListView -> new PipelineCell(model, onFilterRemovedSuccessfully));
    }

    public void setOnFilterRemovedSuccessfully(EventHandler<ActionEvent> onFilterRemovedSuccessfully) {
        this.onFilterRemovedSuccessfully = onFilterRemovedSuccessfully;
    }

}
