package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.frontend.gui.event.ImageLoadedEvent;
import ch.supsi.editor2d.frontend.gui.event.ImageUpdatedEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Controller of the component entitled to show the image.
 * The main component is a ScrollPane containing an ImageView.
 * When zooming in or out, the dimension of the ImageView are changed: when the ImageView becomes bigger than the
 * ScrollPane, the latter creates a viewport (a "sub-image") of the ImageView
 */
public class ImageViewController implements PropertyChangeListener {

    private DataModel model;

    // Main component, contains the image and handles visualizing it in sub-images when it's too big
    @FXML
    private ScrollPane scrollPane;

    // Component responsible for visualizing an Image
    @FXML
    private ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Initialize the components
     */
    public void initModel(DataModel model) {
        // ensure model is only set once
        if (model == null) {
            throw new IllegalStateException("Model can't be null");
        }

        // Set scroll pane properties
        scrollPane.setPannable(true);

        // By default, this component will show a placeholder pane with a button
        // that will load an image from a URL
        this.model = model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event instanceof ImageLoadedEvent || event instanceof ImageUpdatedEvent) {
            drawImage();
        }
    }

    private void drawImage() {
        // Create a new WritableImage
        ImageWrapper imageWrapper = model.getImageData();

        WritableImage writableImage = new WritableImage(imageWrapper.getWidth(), imageWrapper.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        // Draw the image
        for (int h = 0; h < imageWrapper.getHeight(); h++) {
            for (int w = 0; w < imageWrapper.getWidth(); w++) {
                ColorWrapper tempColor = imageWrapper.getData()[h][w];
                pixelWriter.setColor(w, h, Color.color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue()));
            }
        }

        // Display image on ImageView component
        imageView.setImage(writableImage);

        // Set container size
        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);
        imageView.setCache(false);
    }
}
