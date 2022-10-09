package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ImageViewController {

    private DataModel model;

    @FXML
    private ImageView imageView;

    @FXML
    private Button button;

    public void initModel(DataModel model){
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
    }

    @FXML
    public void buttonPress(ActionEvent actionEvent) {
        model.loadImage();
        imageView.setImage(model.getImage().getImage());
    }
}
