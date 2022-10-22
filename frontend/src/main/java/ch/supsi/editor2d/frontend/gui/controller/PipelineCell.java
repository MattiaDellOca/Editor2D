package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class PipelineCell extends ListCell<Task<ImageWrapper, FilterTaskResult>> {

    private Parent root;
    private PipelineCellViewController pipelineCellViewController;

    public PipelineCell() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pipelineCellView.fxml"));
        try {
            root = loader.load();
            pipelineCellViewController = loader.getController();
        } catch (IOException ignored) {
            System.out.println("error");
        }

    }

    @Override
    protected void updateItem(Task<ImageWrapper, FilterTaskResult> imageWrapperFilterTaskResultTask, boolean empty) {
        super.updateItem(imageWrapperFilterTaskResultTask, empty);

        if(empty || imageWrapperFilterTaskResultTask == null){
            setGraphic(null);
        } else{
            pipelineCellViewController.setFilterName("filterNAME");

            setGraphic(root);
        }
    }
}
