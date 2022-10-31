package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.backend.objectPresentation.FilterPresentable;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PipelineCell extends ListCell<Task<ImageWrapper, FilterTaskResult>> {

    private Parent root;
    private PipelineCellViewController pipelineCellViewController;

    private DataModel model;

    public PipelineCell(DataModel model) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pipelineCellView.fxml"));
        try {
            root = loader.load();
            pipelineCellViewController = loader.getController();
            pipelineCellViewController.initModel(model);
            this.model = model;
        } catch (IOException ignored) {}
    }

    @Override
    protected void updateItem(Task<ImageWrapper, FilterTaskResult> task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setGraphic(null);
        } else {
            pipelineCellViewController.setTask(task);
            FilterPresentable filterPresentable = new FilterPresentable();
            pipelineCellViewController.setFilterName(filterPresentable.present(((FilterTask) task).getFilter()));

            pipelineCellViewController.swipeUpVisibility(!model.getActualFiltersPipeline().get(0).equals(task)); //if is the first task hide swipeUp
            pipelineCellViewController.swipeDownVisibility(!model.getActualFiltersPipeline().get(model.getActualFiltersPipeline().size()-1).equals(task));//if is the last task hide swipeBottom

            setGraphic(root);
        }
    }
}
