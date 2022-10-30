package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;



public class FilterCellViewController {

    private DataModel model;

    @FXML
    private Label filterName;


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
}
