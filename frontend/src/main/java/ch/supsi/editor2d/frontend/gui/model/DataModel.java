package ch.supsi.editor2d.frontend.gui.model;


import ch.supsi.editor2d.backend.controller.ImageController;
import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.SharpenFilter;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

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


    public DataModel() {
        this.image = new ImageView();
        this.imageController = new ImageController();

    }

    public void loadImage(String path) {

        try {
            ImageWrapper img2 = imageController.getImage(path);

            SharpenFilter filter = new SharpenFilter();
            ImageWrapper img = filter.apply(img2);

            WritableImage writableImage = new WritableImage(img.getWidth(), img.getHeight());
            PixelWriter pixelWriter = writableImage.getPixelWriter();


            for (int h = 0; h < img.getHeight(); h++) {
                for (int w = 0; w < img.getWidth(); w++) {
                    ColorWrapper tempColor = img.getData()[h][w];
                    pixelWriter.setColor(w, h, Color.color(tempColor.getRed(),tempColor.getGreen(),tempColor.getBlue()));
                }
            }
            image.setImage(writableImage);
        } catch (FileReadingException | FilterApplyException e) {
            //Show Alert
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }

    public ImageView getImage() {
        return image;
    }
}
