package ch.supsi.editor2d.frontend.gui.model;


import ch.supsi.editor2d.backend.controller.IImageController;
import ch.supsi.editor2d.backend.controller.ImageController;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class DataModel {

    /**
     * Interface imageController
     * the right controller (ImagePBMController, ImagePPMController, ...) will be assigned on run time
     */
    private ImageController imageController;

    /**
     * Image showed
     */
    private ImageView image;


    public DataModel() {
        this.image = new ImageView();
    }

    public void loadImage(){
        imageController = new ImageController();
        //ImageWrapper img = imageController.getImage("/home/manuelenolli/Desktop/Image/img1.pbm"); //TODO feature 20542
        ImageWrapper img = imageController.getImage("/home/manuelenolli/Desktop/Image/img2.pgm"); //TODO feature 20542

        WritableImage writableImage = new WritableImage(img.getWidth(), img.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();


        for(int h = 0; h < img.getHeight(); h++){
            for(int w = 0; w < img.getWidth(); w++){
                pixelWriter.setColor(w,h,img.getData()[h][w]);
            }
        }

        image.setImage(writableImage);
    }

    public ImageView getImage() {
        return image;
    }
}
