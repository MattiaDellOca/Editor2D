package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainViewController {

    private DataModel model;

    @FXML
    private AnchorPane imagePane;

    public void initModel(DataModel model){
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
    }

    public AnchorPane getImagePane() {
        return imagePane;
    }


}
