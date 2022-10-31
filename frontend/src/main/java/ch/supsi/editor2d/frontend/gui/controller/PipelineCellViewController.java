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
import javafx.scene.image.ImageView;

public class PipelineCellViewController {

    private DataModel model;

    @FXML
    private Label filterName;
    @FXML
    private ImageView swipeUpImageView;

    @FXML
    private ImageView swipeDownImageView;


    private Task<ImageWrapper, FilterTaskResult> task;

    private EventHandler<ActionEvent> onFilterChanged = event -> {
    };

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

    public void setTask(Task<ImageWrapper, FilterTaskResult> task) {
        this.task = task;
    }

    public void setOnFilterChanged(EventHandler<ActionEvent> onFilterChanged) {
        this.onFilterChanged = onFilterChanged;
    }

    @FXML
    public void removeHandler() {
        try {
            // Remove task from pipeline + rerun pipeline
            model.removeTaskFromPipeline(task);
            // Notify that image has been updated
            onFilterChanged.handle(new ActionEvent());
        } catch (PipelineException e) {
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }

    public void swipeUpVisibility(boolean value) {
        swipeUpImageView.setVisible(value);
    }

    public void swipeDownVisibility(boolean value) {
        swipeDownImageView.setVisible(value);
    }

    @FXML
    public void swipeUp() {
        try {
            //swipe this task with the previous one
            model.swipeUpFilterPipeline(task);
            // Notify that image has been updated
            onFilterChanged.handle(new ActionEvent());
        } catch (PipelineException e) {
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }

    @FXML
    public void swipeDown() {
        try {
            //swipe this task with the next one
            model.swipeDownFilterPipeline(task);
            // Notify that image has been updated
            onFilterChanged.handle(new ActionEvent());
        } catch (PipelineException e) {
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }
}
