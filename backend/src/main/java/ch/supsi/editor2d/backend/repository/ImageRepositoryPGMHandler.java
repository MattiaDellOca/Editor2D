package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.helper.ColorInterpolation;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePGM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.repository.utils.DataValuesParser;

import java.io.*;

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
        } else {
            throw new FileReadingException("File extension not supported");
        }
    }

    @Override
    public void handleSave(String extension, String filename, String path, ImageWrapper data) throws FileWritingException {
        if (extension.equalsIgnoreCase("PGM")) {
            try (FileWriter fileWriter = new FileWriter(new File(path,filename + "." + extension));
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
            ) {
                // Print the header
                bufferedWriter.write("P2\n");

                // Print the width and height
                bufferedWriter.write(data.getWidth() + " " + data.getHeight() + "\n");

                // Print the RGB scale
                bufferedWriter.write(255 + "\n");

                // Print the data
                for (int h = 0; h < data.getHeight(); h++) {
                    for (int w = 0; w < data.getWidth(); w++) {
                        // Convert RGB to black and white using average of the three values
                        ColorWrapper rgbColor = data.getData()[h][w];

                        float gray = 0.299f * rgbColor.getRed() + 0.587f * rgbColor.getGreen() + 0.114f * rgbColor.getBlue();
                        bufferedWriter.write(ColorInterpolation.interpolateRGBtoInt(gray) + " ");
                    }
                    // New line
                    bufferedWriter.write("\n");
                }

                // Save the image
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new FileWritingException("Error during image saving");
            }
        } else if (successor != null) {
            successor.handleSave(extension, filename, path, data);
        } else {
            throw new FileWritingException("File extension not supported");
        }
    }
}
