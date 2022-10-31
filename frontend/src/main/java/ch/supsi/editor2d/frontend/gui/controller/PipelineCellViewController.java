package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
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

    @FXML
    public void removeHandler() {
        model.removeTaskFromPipeline(task);
    }

    public void swipeUpVisibility(boolean value) {
        swipeUpImageView.setVisible(value);
    }

    public void swipeDownVisibility(boolean value) {
        swipeDownImageView.setVisible(value);
    }

    @FXML
    public void swipeUp() {
        model.swipeUpFilterPipeline(task);
    }

    @FXML
    public void swipeDown() {
        model.swipeDownFilterPipeline(task);
    }
}
