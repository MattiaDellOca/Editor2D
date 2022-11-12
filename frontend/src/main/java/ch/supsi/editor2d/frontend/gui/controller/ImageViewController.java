package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

public class ImageViewController {

    private DataModel model;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane imageContainer;

    @FXML
    private ImageView imageView;

    public void initModel(DataModel model){
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        // Refresh image on resize
        scrollPane.widthProperty().addListener((observable, oldValue, newValue) -> refresh());

        // Set scroll pane properties
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(imageView);

        // By default, this component will show a placeholder pane with a button
        // that will load an image from a URL
        this.model = model;
    }

    public void refresh(){
        // Set container size
        scrollPane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        imageView.setImage(model.getImageComponent().getImage());
        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);
        imageView.setCache(false);
    }

    public void setZoom(boolean isIn) {
        Scale newScale = new Scale();
        newScale.setPivotX(imageView.getTranslateX());
        newScale.setPivotY(imageView.getTranslateY());
        if(isIn) {
            newScale.setX(imageView.getScaleX() * 1.05);
            newScale.setY(imageView.getScaleY() * 1.05);
        } else {
            newScale.setX(imageView.getScaleX() / 1.05);
            newScale.setY(imageView.getScaleY() / 1.05);
        }
        imageContainer.getTransforms().add(newScale);
        imageView.getTransforms().add(newScale);
    }
}
