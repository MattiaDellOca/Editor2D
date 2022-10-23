package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class PipelineCell extends ListCell<Task<ImageWrapper, FilterTaskResult>> {

    private Parent root;
    private PipelineCellViewController pipelineCellViewController;


    public PipelineCell(DataModel model) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pipelineCellView.fxml"));
        try {
            root = loader.load();
            pipelineCellViewController = loader.getController();
            pipelineCellViewController.initModel(model);
        } catch (IOException ignored) {}

    }

    @Override
    protected void updateItem(Task<ImageWrapper, FilterTaskResult> task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setGraphic(null);
        } else {
            pipelineCellViewController.setTask(task);
            pipelineCellViewController.setFilterName("filterNAME");
            setGraphic(root);
        }
    }
}
