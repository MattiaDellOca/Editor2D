package ch.supsi.editor2d.frontend.gui.model;


import ch.supsi.editor2d.backend.controller.ImageController;
import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.File;

public class DataModel {

    /**
     * Interface imageController
     * the right controller (ImagePBMController, ImagePPMController, ...) will be assigned on run time
     */
    private final ImageController imageController;

    /**
     * Image showed
     */
    private final ImageView image;

    /**
     * Image wrapper containing the image data
     */
    private ImageWrapper imageData;

    public DataModel() {
        this.image = new ImageView();
        this.imageController = new ImageController();
    }

    public void loadImage(String path) {
        try {
            imageData = imageController.getImage(path);
            WritableImage writableImage = new WritableImage(imageData.getWidth(), imageData.getHeight());
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            for (int h = 0; h < imageData.getHeight(); h++) {
                for (int w = 0; w < imageData.getWidth(); w++) {
                    ColorWrapper tempColor = imageData.getData()[h][w];
                    pixelWriter.setColor(w, h, Color.color(tempColor.getRed(),tempColor.getGreen(),tempColor.getBlue()));
                }
            }
            image.setImage(writableImage);
        } catch (FileReadingException e) {
            //Show Alert
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }

    public void exportImage (File directory) {
        try {
            // Try to export image into selected directory
            imageController.exportImage(directory, imageData);
        } catch (FileWritingException e) {
            //Show Alert
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }

    public ImageView getImage() {
        return image;
    }
}
