package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.controller.model.ImagePBM;

import java.awt.*;

public class MainController {

    public void SayHello() {
        System.out.println("HELLO FROM BACKEND MAIN CONTROLLER!");
    }

    public static void main(String[] args) {

        int width = 10;
        int height = 15;
        Color[][] dataImage = new Color[height][width];

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                dataImage[h][w] = new Color(100 + w + h, 100 + w + h, 100 + w + h);
                System.out.print(dataImage[h][w] + " ");
            }
            System.out.println();
        }
        ImagePBM imagePGM = new ImagePBM(10, 10, dataImage);


    }
}
