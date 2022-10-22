package ch.supsi.editor2d.frontend.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PipelineCellViewController {
    @FXML
    private Label filterName;
    @FXML
    private BorderPane cell;

    public BorderPane getCell(){
        return cell;
    }

    public void setFilterName(String label) {
        this.filterName = new Label(label);
    }
}
