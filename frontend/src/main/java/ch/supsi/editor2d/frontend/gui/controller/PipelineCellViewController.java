package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class PipelineCellViewController {

    private DataModel model;

    @FXML
    private Label filterName;
    @FXML
    private BorderPane cell;

    private Task<ImageWrapper, FilterTaskResult> task;

    private EventHandler<ActionEvent> onFilterRemovedSuccessfully = event -> {};

    public void initModel(DataModel model) {
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
    }

    public void setFilterName(String label) {
        this.filterName.setText(label);
    }

    public void setTask(Task<ImageWrapper, FilterTaskResult> task){
        this.task = task;
    }

    public void setOnFilterRemovedSuccessfully(EventHandler<ActionEvent> onFilterRemovedSuccessfully) {
        this.onFilterRemovedSuccessfully = onFilterRemovedSuccessfully;
    }

    @FXML
    public void removeHandler() {
        try {
            // Remove task from pipeline + rerun pipeline
            model.removeTaskFromPipeline(task);
            // Notify that image has been updated
            onFilterRemovedSuccessfully.handle(new ActionEvent());
        } catch (PipelineException e) {
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }
}
