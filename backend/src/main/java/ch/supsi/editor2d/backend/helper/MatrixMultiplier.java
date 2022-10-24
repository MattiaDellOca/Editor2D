package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.ColorMatrixFilter;

import java.util.Arrays;

/**
 * Utility class used for matrix operations
 */
public final class MatrixMultiplier {

    // Apply a color based filter to an image
    public static ImageWrapper applyColorFilter(ImageWrapper image, ColorMatrixFilter filter) {
        ColorWrapper[][] I = image.getData();
        double[][] F = filter.getMatrix();

        // Create an RGB matrix where the value of the transformation are saved
        ColorWrapper[][] R = new ColorWrapper[I.length][I[0].length];
        for(int i = 0; i < I.length; i ++) {
            for (int t = 0; t < I[0].length; t++) {
                // Calculate the value of each pixel
                R[i][t] = matrixPerVector(F, I[i][t]);
            }
        }

        // Return the new image
        return new ImageWrapper(image.getWidth(), image.getHeight(), R);
    }

    // Calculate dot product of a row of ColorWrapper and a column of double
    private static ColorWrapper dotProduct(ColorWrapper[] row, double[] column) {
        float red = 0, green = 0, blue = 0;
        for(int i = 0; i < row.length; i ++) {
            red += row[i].getRed() * column[i];
            green += row[i].getGreen() * column[i];
            blue += row[i].getBlue() * column[i];
        }

        return new ColorWrapper(red, green, blue);
    }

    // Calculate the matrix multiplication of a matrix representing the filter and a vector containing pixel's RGB values
    private static ColorWrapper matrixPerVector(double[][] matrix, ColorWrapper vector) {
        float[] rgb = new float[3];
        Arrays.fill(rgb, 0);
        for(int i = 0; i < matrix.length; i ++) {
            for(int t = 0; t < 3; t ++) {
                rgb[i] += matrix[i][t] * vector.getRGB()[t];
            }
        }

        return new ColorWrapper(rgb[0], rgb[1], rgb[2]);
    }
}
