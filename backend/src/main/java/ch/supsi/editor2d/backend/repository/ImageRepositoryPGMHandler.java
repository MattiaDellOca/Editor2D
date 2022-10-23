package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.helper.ColorInterpolation;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePGM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.repository.utils.DataValuesParser;
import jdk.jshell.spi.ExecutionControl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static ch.supsi.editor2d.backend.repository.utils.LineChecker.checkHeaderLine;

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
                String tempLine = checkHeaderLine('#', bufferedReader);
                if (!tempLine.equals("P2")) {
                    throw new FileReadingException("Magic number is incorrect");
                }

                //width and height reading
                String[] widthHeight = checkHeaderLine('#', bufferedReader).split(" ");
                int width = Integer.parseInt(widthHeight[0]);
                int height = Integer.parseInt(widthHeight[1]);

                //scale of gray
                tempLine = checkHeaderLine('#', bufferedReader);
                int scaleOfGray = Integer.parseInt(tempLine);

                //data reading
                ColorWrapper[][] data = new ColorWrapper[height][width];
                DataValuesParser parser = new DataValuesParser(bufferedReader);

                for (int h = 0; h < height; h++) {
                    for (int w = 0; w < width; w++) {
                        float grayColorInterpolated = ColorInterpolation.interpolateRGBtoFloat(255 / scaleOfGray) *
                                parser.getNext();
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

    @Override
    public void handleSave(String path, ImageWrapper data) throws FileWritingException {
        throw new RuntimeException("Not implemented yet");
    }
}
