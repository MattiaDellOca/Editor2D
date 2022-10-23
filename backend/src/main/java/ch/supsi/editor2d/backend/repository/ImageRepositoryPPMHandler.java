package ch.supsi.editor2d.backend.repository;

/*
    handle load with the logic found here http://people.uncw.edu/tompkinsj/112/texnh/assignments/imageFormat.html (12.10.2022)
   Example PPM:
   P3
    3 4
    255
    #the P3 means colors are in ascii, then 3 columns and 4 rows, then 255 for max color, then RGB triplets
    255 0 0
    0 255 0
    0 0 255
    255 255 0
    255 255 255
    0 0 0
    0 255 255
    75 75 75
    127 127 127
    150 150 150
    150 150 150
    150 150 150
 */

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.helper.ColorInterpolation;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePPM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.repository.utils.DataValuesParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static ch.supsi.editor2d.backend.repository.utils.LineChecker.checkHeaderLine;

public class ImageRepositoryPPMHandler extends ImageRepositoryHandler {

    @Override
    public ImageWrapper handleLoad(String extension, String path) throws FileReadingException {
        if (extension.equalsIgnoreCase("PPM")) {

            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)
            ) {
                String tempLine = checkHeaderLine('#', bufferedReader);
                if (!tempLine.equals("P3")) {
                    throw new FileReadingException("Magic number is incorrect");
                }

                //width and height reading
                String[] widthHeight = checkHeaderLine('#', bufferedReader).split(" ");
                int width = Integer.parseInt(widthHeight[0]);
                int height = Integer.parseInt(widthHeight[1]);

                //scale of rgb
                tempLine = checkHeaderLine('#', bufferedReader);
                int scaleOfRGB = Integer.parseInt(tempLine);

                //data reading
                ColorWrapper[][] data = new ColorWrapper[height][width];
                float[] rgb = new float[3];
                DataValuesParser parser = new DataValuesParser(bufferedReader);

                for (int h = 0; h < height; h++) {
                    for(int w = 0; w < width; w ++) {
                        for (int c = 0; c < 3; c++) {
                            rgb[c] = ColorInterpolation.interpolateRGBtoFloat(255 / scaleOfRGB) * parser.getNext();
                        }
                        data[h][w] = new ColorWrapper(rgb[0], rgb[1], rgb[2]);
                    }
                }

                return new ImagePPM(width, height, data, scaleOfRGB);

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
