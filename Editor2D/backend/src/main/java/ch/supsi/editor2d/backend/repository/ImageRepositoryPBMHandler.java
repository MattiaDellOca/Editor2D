package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.model.ImageWrapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static ch.supsi.editor2d.backend.repository.utils.LineChecker.checkAndGetLine;


/*
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
 0 0 0 0 0 0 */


public class ImageRepositoryPBMHandler extends ImageRepositoryHandler {


    @Override
    public ImageWrapper handleLoad(String extension, String path) throws FileReadingException {
        if (extension.equalsIgnoreCase("PBM")) {
            // This handler can handle the load request
            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)
            ) {
                //Reading Magic number
                String tempLine = checkAndGetLine('#', bufferedReader);
                if (!tempLine.equals("P1")) {
                    throw new FileReadingException("Magic number is incorrect");
                }

                //Reading length and height
                tempLine = checkAndGetLine('#', bufferedReader);
                String[] widthHeight = tempLine.split(" ");
                int width = Integer.parseInt(widthHeight[0]);
                int height = Integer.parseInt(widthHeight[1]);

                //data reading
                ColorWrapper[][] data = new ColorWrapper[height][width];
                for (int h = 0; h < height; h++) {
                    tempLine = checkAndGetLine('#', bufferedReader);
                    tempLine = tempLine.replace(" ", ""); //remove spaces
                    for (int w = 0; w < width; w++) {
                        if (tempLine.charAt(w) == '1') {
                            data[h][w] = new ColorWrapper(0.f,0.f,0.f); //black
                        } else if (tempLine.charAt(w) == '0'){
                            data[h][w] = new ColorWrapper(1.f,1.f,1.f); //white;
                        } else {
                            throw new IOException();
                        }
                    }
                }
                return new ImagePBM(width, height, data);
            } catch (IOException | NumberFormatException | StringIndexOutOfBoundsException e) {
                throw new FileReadingException("Error during image loading");
            }
        } else if (successor != null) {
            // Skip to the next successor
            return successor.handleLoad(extension, path);
        }

        throw new FileReadingException("File extension not supported");

    }
}
