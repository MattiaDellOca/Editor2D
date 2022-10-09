package ch.supsi.editor2d.frontend.gui.model;


import ch.supsi.editor2d.backend.controller.IImageController;
import ch.supsi.editor2d.backend.controller.PBM.ImagePBMController;
import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class DataModel {


    private IImageController imageController;
    private ImageView image;


    public DataModel() {
        this.image = new ImageView();
    }

    public void loadImage(){
        imageController = new ImagePBMController();
        ImageWrapper img = imageController.getImage("/home/manuelenolli/Desktop/Image/cat.pbm");

        WritableImage writableImage = new WritableImage(img.getWidth(), img.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();


        System.out.println("Frontend");
        System.out.println(img.getHeight());
        System.out.println(img.getWidth());
        for(int h = 0; h < img.getHeight(); h++){
            for(int w = 0; w < img.getWidth(); w++){
                pixelWriter.setColor(w,h,img.getData()[h][w]);
                System.out.print(img.getData()[h][w] + " ");
            }
            System.out.println();
        }

        image.setImage(writableImage);

    }

    public ImageView getImage() {
        return image;
    }
}
