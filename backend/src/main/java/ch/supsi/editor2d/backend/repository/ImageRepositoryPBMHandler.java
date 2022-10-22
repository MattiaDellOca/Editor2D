package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.repository.utils.DataValuesParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static ch.supsi.editor2d.backend.repository.utils.LineChecker.checkHeaderLine;


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
                String tempLine = checkHeaderLine('#', bufferedReader);
                if (!tempLine.equals("P1")) {
                    throw new FileReadingException("Magic number is incorrect");
                }

                //Reading length and height
                String[] widthHeight = checkHeaderLine('#', bufferedReader).split(" ");
                int width = Integer.parseInt(widthHeight[0]);
                int height = Integer.parseInt(widthHeight[1]);

                //data reading
                ColorWrapper[][] data = new ColorWrapper[height][width];
                DataValuesParser parser = new DataValuesParser(bufferedReader);

                for (int h = 0; h < height; h++) {
                    for (int w = 0; w < width; w++) {
                        data[h][w] = parser.getNext() == 0 ? ColorWrapper.WHITE : ColorWrapper.BLACK;
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
