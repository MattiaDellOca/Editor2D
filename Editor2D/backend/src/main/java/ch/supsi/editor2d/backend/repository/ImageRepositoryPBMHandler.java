package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * handle load with the logic found here http://davis.lbl.gov/Manuals/NETPBM/doc/pbm.html (12.10.2022)

 EXAMPLE PBM:
 P1
 #Comment
 # This is an example bitmap of the letter "J"
 6 10
 #Comment
 0 0 0 0 1 0
 0 0 0 0 1 0
 0 0 0 0 1 0 #Comment
 0 0 0 0 1 0
 0 0 0 0 1 0
 0 0 0 0 1 0
 1 0 0 0 1 0
 0 1 1 1 0 0
 0 0 0 0 0 0
 0 0 0 0 0 0



 */
public class ImageRepositoryPBMHandler extends ImageRepositoryHandler {

    private String checkAndGetLine(char commentType, BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        if(line == null){
            throw new IOException();
        }

        int indexComment = line.indexOf(commentType);
        if (indexComment == -1) {
            //no comment present
            return line;
        }

        //create a substring
        String possibleResult = line.substring(0, indexComment);
        if (possibleResult.length() > 0) {
            //there is some information
            return possibleResult;
        }

        //no information in this line, return the next one
        return checkAndGetLine(commentType, bufferedReader);
    }


    @Override
    public ImageWrapper handleLoad(String path) {
        if (path.equalsIgnoreCase("/home/manuelenolli/Desktop/Image/img3.pbm")) {
            // This handler can handle the load request


            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)
            ) {

                //Reading Magic number
                String tempLine = checkAndGetLine('#',bufferedReader);
                if (!tempLine.equals("P1")) {
                    throw new RuntimeException("Not a PBM Ascii file");
                }

                //Reading length and height
                tempLine = checkAndGetLine('#',bufferedReader);
                String[] widthHeight = tempLine.split(" ");
                int width = Integer.parseInt(widthHeight[0]);
                int height = Integer.parseInt(widthHeight[1]);

                //data reading
                Color[][] data = new Color[height][width];
                for (int h = 0; h < height; h++) {
                    tempLine = checkAndGetLine('#',bufferedReader);
                    tempLine = tempLine.replace(" ", ""); //remove spaces
                    for (int w = 0; w < width; w++) {
                        if (tempLine.charAt(w) == '1') {
                            data[h][w] = Color.BLACK;
                        } else {
                            data[h][w] = Color.WHITE;
                        }
                    }
                }

                return new ImagePBM(width, height, data);
            } catch (IOException e) {
                System.out.println("Errore caricamento immagine");
                return null;
            }

        } else if (successor != null) {
            // Skip to the next successor
            return successor.handleLoad(path);
        }

        throw new RuntimeException("No one can handle this request");
    }
}
