package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.service.IImageService;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
EXAMPLE PBM
P1
# This is an example bitmap of the letter "J"
6 10
0 0 0 0 1 0
0 0 0 0 1 0
0 0 0 0 1 0
0 0 0 0 1 0
0 0 0 0 1 0
0 0 0 0 1 0
1 0 0 0 1 0
0 1 1 1 0 0
0 0 0 0 0 0
0 0 0 0 0 0
 */

/**
 * Image PBM repository
 */
public class ImagePBMRepository {
    

    //TODO exceptions handler
    /**
     * Load a PBM image from file inside path
     * @param path path of the file to load
     * @return an ImagePBM with the data
     */
    public ImagePBM loadImage(String path) {

        try (FileReader fileReader = new FileReader(path);
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String tempLine = bufferedReader.readLine();
            if(!tempLine.equals("P1")){
                throw new RuntimeException("Not a PBM Ascii file");
            }

            tempLine = bufferedReader.readLine();

            if(tempLine.length() > 1 && tempLine.charAt(0) == '#'){ // Check if is present a comment
                tempLine = bufferedReader.readLine(); //skip the comment
            }

            //width and height reading
            String[] widthHeight = tempLine.split(" ");
            int width = Integer.parseInt(widthHeight[0]);
            int height = Integer.parseInt(widthHeight[1]);

            //data reading
            Color[][] data = new Color[height][width];
            for(int h = 0; h < height; h++){
                tempLine = bufferedReader.readLine();
                tempLine = tempLine.replace(" ", ""); //remove spaces
                for(int w = 0; w < width; w++){
                    if(tempLine.charAt(w) == '1'){
                        data[h][w] = Color.BLACK;
                    } else{
                        data[h][w] = Color.WHITE;
                    }
                }
            }

            return new ImagePBM(width,height,data);
        } catch (IOException e) {
            System.out.println("Errore caricamento immagine");
            return null;
        }
    }

}
