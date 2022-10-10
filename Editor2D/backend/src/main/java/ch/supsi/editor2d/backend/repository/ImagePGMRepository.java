package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.service.IImageService;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ImagePGMRepository implements IImageService {


    /**
     * @param path 
     * @return
     */
    @Override
    public ImageWrapper loadImage(String path) {

        try (FileReader fileReader = new FileReader(path);
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String tempLine = bufferedReader.readLine();
            if(!tempLine.equals("P2")){
                throw new RuntimeException("Not a PGM Ascii file");
            }

            tempLine = bufferedReader.readLine();

            if(tempLine.length() > 1 && tempLine.charAt(0) == '#'){ // Check if is present a comment
                tempLine = bufferedReader.readLine(); //skip the comment
            }

            //width and height reading
            String[] widthHeight = tempLine.split(" ");
            int width = Integer.parseInt(widthHeight[0]);
            int height = Integer.parseInt(widthHeight[1]);

            //scale of gray
            tempLine = bufferedReader.readLine();
            int scaleOfGray = Integer.parseInt(tempLine);

            //data reading
            Color[][] data = new Color[height][width];
            for(int h = 0; h < height; h++){
                tempLine = bufferedReader.readLine();
                String templine2 = tempLine.replaceAll("\s+"," ");//starting with whitespace one or more
                String[] tempLineArray = templine2.split(" ");
                for(int w = 0; w < width; w++){
                    System.out.println("pos "+ w +": " +tempLineArray[w]);
                    int grayColor = (255/scaleOfGray) * Integer.parseInt(tempLineArray[w]);
                    data[h][w] = Color.rgb(grayColor,grayColor,grayColor);
                }
            }

            return new ImagePBM(width,height,data);
        } catch (IOException e) {
            System.out.println("Errore caricamento immagine");
            return null;
        }
    }
}
