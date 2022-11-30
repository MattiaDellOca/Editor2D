package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.frontend.gui.event.AddedFilterEvent;
import ch.supsi.editor2d.frontend.gui.event.ImageLoadedEvent;
import ch.supsi.editor2d.frontend.gui.event.RemovedFilterEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.model.PipelineCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.beans.PropertyChangeEvent;

public class PipelineViewController extends AbstractFXMLController {

    @FXML
    private ListView<Task<ImageWrapper, FilterTaskResult>> filterPipelineList;

    private DataModel model;

    @FXML
    private Button removeFilter;

    @FXML
    private Button moveUpFilter;

    @FXML
    private Button moveDownFilter;

    @FXML
    private Button runPipeline;

    private Task<ImageWrapper, FilterTaskResult> selectedTask;

    private ObservableList<Task<ImageWrapper, FilterTaskResult>> actualFiltersPipeline;

    public void initModel(DataModel model) {
        // ensure model is only set once
        if (model == null) {
            throw new IllegalStateException("Model can't be null");
        }
        this.model = model;
    }

    //initialize method is called by FXMLLoader
    @FXML
    public void initialize() {
        actualFiltersPipeline = FXCollections.observableArrayList();

        //Setting filter pipeline as dataModel list
        filterPipelineList.setItems(actualFiltersPipeline);

        //Setting selected task
        removeFilter.setDisable(true);
        moveUpFilter.setDisable(true);
        moveDownFilter.setDisable(true);

        //Setting personalized ListViewCell
        filterPipelineList.setCellFactory(taskListView -> new PipelineCell());

        //Update filter pipeline selected item
        filterPipelineList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedTask = newValue;
            if (selectedTask != null) {
                moveUpFilter.setDisable(actualFiltersPipeline.get(0).equals(selectedTask)); //First element
                moveDownFilter.setDisable(actualFiltersPipeline.get(actualFiltersPipeline.size() - 1).equals(selectedTask)); //Last element
                removeFilter.setDisable(false);
            } else {
                removeFilter.setDisable(true);
                moveUpFilter.setDisable(true);
                moveDownFilter.setDisable(true);
            }
        });

        //Move up filter action
        moveUpFilter.setOnAction(event -> {
            try {
                model.moveUpFilterPipeline(selectedTask);
                updateFilterPipeline();
            } catch (PipelineException e) {
                System.err.println(e.getMessage());
                ErrorAlert.showError(e.getMessage());
            }
            filterPipelineList.getSelectionModel().clearSelection();
        });

        //Move down filter action
        moveDownFilter.setOnAction(event -> {
            try {
                model.moveDownFilterPipeline(selectedTask);
                updateFilterPipeline();
            } catch (PipelineException e) {
                System.err.println(e.getMessage());
                ErrorAlert.showError(e.getMessage());
            }
            filterPipelineList.getSelectionModel().clearSelection();
        });
    }

    private void updateFilterPipeline() {
        actualFiltersPipeline.setAll(model.getFilterPipeline().getTasks());
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event instanceof AddedFilterEvent || event instanceof RemovedFilterEvent || event instanceof ImageLoadedEvent) {
            updateFilterPipeline();
        }
    }

    public Button getRunPipeline() {
        return runPipeline;
    }

    public Button getRemoveFilter() {
        return removeFilter;
    }

    public Task<ImageWrapper, FilterTaskResult> getSelectedTask() {
        return selectedTask;
    }
}
