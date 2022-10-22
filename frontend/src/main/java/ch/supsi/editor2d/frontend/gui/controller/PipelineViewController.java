package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PipelineViewController {
    @FXML
    private ListView<Task<ImageWrapper, FilterTaskResult>> filterPipelineList;
    private DataModel model;

    public void initModel(DataModel model) {
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;

        //Setting filter pipeline as datamodel list
        filterPipelineList.setItems(model.getActualFiltersPipeline());

        //Setting personalized ListViewCell
        filterPipelineList.setCellFactory(new Callback<ListView<Task<ImageWrapper, FilterTaskResult>>, ListCell<Task<ImageWrapper, FilterTaskResult>>>() {
            @Override
            public ListCell<Task<ImageWrapper, FilterTaskResult>> call(ListView<Task<ImageWrapper, FilterTaskResult>> taskListView) {
                return new PipelineCell();
            }
        });
    }


}
