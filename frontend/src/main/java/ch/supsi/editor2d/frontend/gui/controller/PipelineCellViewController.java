package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class PipelineCellViewController {

    private DataModel model;

    @FXML
    private Label filterName;
    @FXML
    private BorderPane cell;

    private Task<ImageWrapper, FilterTaskResult> task;


    public BorderPane getCell() {
        return cell;
    }
    public void initModel(DataModel model) {
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
    }

    public void setFilterName(String label) {
        this.filterName = new Label(label);
    }

    public void setTask(Task<ImageWrapper, FilterTaskResult> task){
        this.task = task;
    }


    public void removeHandler(MouseEvent mouseEvent) {
        model.removeTaskFromPipeline(task);
    }
}
