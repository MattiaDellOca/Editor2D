package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.repository.utils.DataValuesParser;

import java.io.*;

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

                ColorWrapper white = new ColorWrapper(255, 255, 255);
                ColorWrapper black = new ColorWrapper(0, 0, 0);

                for (int h = 0; h < height; h++) {
                    for (int w = 0; w < width; w++) {
                        data[h][w] = parser.getNext() == 0 ? white : black;
                    }
                }

                return new ImagePBM(width, height, data);
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                throw new FileReadingException("Error during image loading");
            } catch (IOException e) {
                throw new FileReadingException("File not found or not readable");
            }
        } else if (successor != null) {
            // Skip to the next successor
            return successor.handleLoad(extension, path);
        } else {
            throw new FileReadingException("File extension not supported");
        }
    }

    @Override
    public void handleSave(String extension, String filename, String path, ImageWrapper data) throws FileWritingException {
        if (extension.equalsIgnoreCase("PBM")) {
            try (FileWriter fileWriter = new FileWriter(new File(path, filename + "." + extension));
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
            ) {
                // Print the header
                bufferedWriter.write("P1\n");


                // Print the width and height
                bufferedWriter.write(data.getWidth() + " " + data.getHeight() + "\n");

                // Print the data
                for (int h = 0; h < data.getHeight(); h++) {
                    for (int w = 0; w < data.getWidth(); w++) {
                        // Convert RGB to black and white using average of the three values
                        ColorWrapper rgbColor = data.getData()[h][w];


                        float gray = 0.299f * rgbColor.getRed() + 0.587f * rgbColor.getGreen() + 0.114f * rgbColor.getBlue();
                        boolean isBlack = gray < 0.5;
                        bufferedWriter.write(isBlack ? "1 " : "0 ");
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
