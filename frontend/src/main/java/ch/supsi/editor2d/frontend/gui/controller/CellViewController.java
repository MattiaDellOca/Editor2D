package ch.supsi.editor2d.frontend.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CellViewController {

    @FXML
    private Label filterName;

    public void setFilterName(String label) {
        this.filterName.setText(label);
    }
}
