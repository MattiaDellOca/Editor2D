package ch.supsi.editor2d.backend;

import ch.supsi.editor2d.backend.controller.IImageController;
import ch.supsi.editor2d.backend.model.ImageWrapper;

public class MainController {

    public void SayHello() {
        System.out.println("HELLO FROM BACKEND MAIN CONTROLLER!");
    }

    public static void main(String[] args) {

        String path = "/home/manuelenolli/Desktop/cat.pbm";/*
        ImagePBMController imagePBMController = new ImagePBMController();
        ImagePBM myImage = imagePBMController.getImage(path);

        Color[][] temp = myImage.getData();
        for (int h = 0; h < myImage.getHeight(); h++) {
            for (int w = 0; w < myImage.getWidth(); w++) {
                System.out.print(temp[h][w]);
            }
            System.out.println();
        }*/

    }
}
