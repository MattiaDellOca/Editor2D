package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePGM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.repository.utils.InterpolateRGB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static ch.supsi.editor2d.backend.repository.utils.LineChecker.checkAndGetLine;

/*
    handle load with the logic found here http://people.uncw.edu/tompkinsj/112/texnh/assignments/imageFormat.html (12.10.2022)
   Example PGM:
    P2
    24 7
    15
    0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0
    0  3  3  3  3  0  0  7  7  7  7  0  0 11 11 11 11  0  0 15 15 15 15  0
    0  3  0  0  0  0  0  7  0  0  0  0  0 11  0  0  0  0  0 15  0  0 15  0
    0  3  3  3  0  0  0  7  7  7  0  0  0 11 11 11  0  0  0 15 15 15 15  0
    0  3  0  0  0  0  0  7  0  0  0  0  0 11  0  0  0  0  0 15  0  0  0  0
    0  3  0  0  0  0  0  7  7  7  7  0  0 11 11 11 11  0  0 15  0  0  0  0
    0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0
 */

public class ImageRepositoryPGMHandler extends ImageRepositoryHandler {


    @Override
    public ImageWrapper handleLoad(String extension, String path) throws FileReadingException {
        if (extension.equalsIgnoreCase("PGM")) {

            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)
            ) {
                String tempLine = checkAndGetLine('#', bufferedReader);
                if (!tempLine.equals("P2")) {
                    throw new FileReadingException("Magic number is incorrect");
                }

                //width and height reading
                tempLine = checkAndGetLine('#', bufferedReader);
                String[] widthHeight = tempLine.split(" ");
                int width = Integer.parseInt(widthHeight[0]);
                int height = Integer.parseInt(widthHeight[1]);

                //scale of gray
                tempLine = checkAndGetLine('#', bufferedReader);
                int scaleOfGray = Integer.parseInt(tempLine);

                //data reading
                ColorWrapper[][] data = new ColorWrapper[height][width];
                for (int h = 0; h < height; h++) {
                    tempLine = checkAndGetLine('#', bufferedReader);
                    String tempLineReplaced = tempLine.replaceAll("\s+", " ");//starting with whitespace one or more
                    String[] tempLineArray = tempLineReplaced.split(" ");
                    for (int w = 0; w < width; w++) {
                        float grayColorInterpolated = InterpolateRGB.interpolateRGBtoFloat(255 / scaleOfGray) * Integer.parseInt(tempLineArray[w]);
                        data[h][w] = new ColorWrapper(grayColorInterpolated, grayColorInterpolated, grayColorInterpolated);
                    }
                }

                return new ImagePGM(width, height, data, scaleOfGray);

            } catch (IOException | NumberFormatException | StringIndexOutOfBoundsException e) {
                throw new FileReadingException("Error during image loading");
            }

        } else if (successor != null) {
            return successor.handleLoad(extension, path);
        }
        throw new FileReadingException("File extension not supported");
    }
}
