package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.event.ImageUpdatedEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.beans.PropertyChangeEvent;

/**
 * Controller of the component entitled to show the image.
 * The main component is a ScrollPane containing an ImageView.
 * When zooming in or out, the dimension of the ImageView are changed: when the ImageView becomes bigger than the
 * ScrollPane, the latter creates a viewport (a "sub-image") of the ImageView
 */
public class ImageViewController extends AbstractFXMLController {

    private static final double ZOOM_FACTOR = 1.1;

    private DataModel model;

    // Main component, contains the image and handles visualizing it in sub-images when it's too big
    @FXML
    private ScrollPane scrollPane;

    // Component responsible for visualizing an Image
    @FXML
    private ImageView imageView;

    /**
     * Initialize the components
     */
    public void initModel(DataModel model){
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        // By default, this component will show a placeholder pane with a button
        // that will load an image from a URL
        this.model = model;
    }

    /**
     * This method is called every time the window is resized: the ImageView is resized accordingly
     */
    public void refresh(){
        // Set container size
        imageView.setImage(model.getImageComponent().getImage());
        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);
        imageView.setCache(false);
    }

    /**
     * Perform the zoom in/out actions modifying the width and height of the imageView
     * @param isIn if true, performs zoom in action; otherwise performs zoom out
     */
    public void setZoom(boolean isIn) {
        if(isIn) {
            imageView.setFitWidth(imageView.getFitWidth() * ZOOM_FACTOR);
            imageView.setFitHeight(imageView.getFitHeight() * ZOOM_FACTOR);
        } else {
            imageView.setFitWidth(imageView.getFitWidth() / ZOOM_FACTOR);
            imageView.setFitHeight(imageView.getFitHeight() / ZOOM_FACTOR);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event instanceof ImageUpdatedEvent) {
            refresh();
        }
    }
}
