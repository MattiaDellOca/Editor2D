package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ImageViewController {

    private DataModel model;

    @FXML
    private Pane containerImage;

    @FXML
    private ImageView imageView;

    public void initModel(DataModel model){
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        // Refresh image on resize
        containerImage.widthProperty().addListener((observable, oldValue, newValue) -> refresh());

        // By default, this component will show a placeholder pane with a button
        // that will load an image from a URL
        this.model = model;
    }

    public void refresh(){
        // Set container size
        containerImage.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        imageView.setImage(model.getImageComponent().getImage());
        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);
        imageView.setCache(false);
        imageView.setFitHeight(containerImage.getHeight());
        imageView.setFitWidth(containerImage.getWidth());
    }
}
