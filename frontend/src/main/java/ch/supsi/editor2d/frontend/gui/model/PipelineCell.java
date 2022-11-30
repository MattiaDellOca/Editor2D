package ch.supsi.editor2d.frontend.gui.model;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.backend.objectPresentation.FilterPresentable;
import ch.supsi.editor2d.frontend.gui.controller.CellViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PipelineCell extends ListCell<Task<ImageWrapper, FilterTaskResult>> {

    private Parent root;
    private CellViewController cellViewController;

    public PipelineCell() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cellView.fxml"));
        try {
            root = loader.load();
            cellViewController = loader.getController();
        } catch (IOException ignored) {}
    }

    @Override
    protected void updateItem(Task<ImageWrapper, FilterTaskResult> task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setGraphic(null);
        } else {
            //Present the filter name
            FilterPresentable filterPresentable = new FilterPresentable();
            cellViewController.setFilterName(filterPresentable.present(((FilterTask) task).getFilter()));
            setGraphic(root);
        }
    }
}
