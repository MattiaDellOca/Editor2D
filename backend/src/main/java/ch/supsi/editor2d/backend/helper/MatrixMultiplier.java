package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.KernelMatrixFilter;
import ch.supsi.editor2d.backend.model.filter.MatrixFilter;

import java.util.Arrays;

public final class MatrixMultiplier {
    public static ImageWrapper applyScalarFilter(ImageWrapper image, MatrixFilter filter) {
        ColorWrapper[][] I = image.getData();
        double[][] F = filter.getMatrix();
        if (I[0].length != F.length)
            return null;

        ColorWrapper[][] R = new ColorWrapper[I.length][F[0].length];
        for (int i = 0; i < I.length; i++) {
            for (int t = 0; t < F[0].length; t++) {
                ColorWrapper[] row = new ColorWrapper[I[0].length];
                System.arraycopy(I[i], 0, row, 0, row.length);

                double[] column = new double[F.length];
                for (int k = 0; k < column.length; k++)
                    column[k] = F[k][t];

                R[i][t] = dotProduct(row, column);
            }
        }

        return new ImageWrapper(image.getWidth(), image.getHeight(), R);
    }

    public static ImageWrapper applyColorFilter(ImageWrapper image, MatrixFilter filter) {
        ColorWrapper[][] I = image.getData();
        double[][] F = filter.getMatrix();
        if (I[0].length != F.length)
            return null;

        ColorWrapper[][] R = new ColorWrapper[I.length][F[0].length];
        for (int i = 0; i < I.length; i++) {
            for (int t = 0; t < F[0].length; t++) {
                R[i][t] = matrixPerVector(F, I[i][t]);
            }
        }

        return new ImageWrapper(image.getWidth(), image.getHeight(), R);
    }

    //TODO: chiedere a Mattia perchè tornare null e non un exception

    /**
     * Method for apply 3x3 filter kernel
     *
     * @param image  original image
     * @param filter kernel filter 3x3
     * @return a new image with the filter applied
     */
    public static ImageWrapper applyKernelFilter(ImageWrapper image, KernelMatrixFilter filter) {

        //Image has to be at least 3x3 because the border aren't count
        if (image.getWidth() < 3 || image.getHeight() < 3) {
            return null;
        }

        //get colors data
        ColorWrapper[][] originalImage = image.getData();

        //create newImage with cropped edges
        ColorWrapper[][] newImage = new ColorWrapper[image.getHeight() - 2][image.getWidth() - 2];

        double[][] filterMatrix = filter.getMatrix();

        for (int w = 1; w < image.getWidth() - 1; w++) {
            for (int h = 1; h < image.getHeight() - 1; h++) {

                // Get all the near colors
                ColorWrapper[][] nearColors = new ColorWrapper[3][3];
                nearColors[0][0] = originalImage[h - 1][w - 1];
                nearColors[0][1] = originalImage[h - 1][w];
                nearColors[0][2] = originalImage[h - 1][w + 1];
                nearColors[1][0] = originalImage[h][w - 1];
                nearColors[1][1] = originalImage[h][w];
                nearColors[1][2] = originalImage[h][w + 1];
                nearColors[2][0] = originalImage[h + 1][w - 1];
                nearColors[2][1] = originalImage[h + 1][w];
                nearColors[2][2] = originalImage[h + 1][w + 1];

                //Apply the filter to the different RGB color
                float red = 0, green = 0, blue = 0;
                for (int i = 0; i < nearColors.length; i++) {
                    for (int j = 0; j < nearColors[i].length; j++) {
                        red += nearColors[i][j].getRed() * filterMatrix[i][j];
                        green += nearColors[i][j].getGreen() * filterMatrix[i][j];
                        blue += nearColors[i][j].getBlue() * filterMatrix[i][j];
                    }
                }
                //set the pixel to the newImage
                newImage[h - 1][w - 1] = new ColorWrapper(red, green, blue);
            }
        }
        return new ImageWrapper(newImage[0].length, newImage.length, newImage);
    }

    private static ColorWrapper dotProduct(ColorWrapper[] row, double[] column) {
        float red = 0, green = 0, blue = 0;
        for (int i = 0; i < row.length; i++) {
            red += row[i].getRed() * column[i];
            green += row[i].getGreen() * column[i];
            blue += row[i].getBlue() * column[i];
        }

        return new ColorWrapper(red, green, blue);
    }

    private static ColorWrapper matrixPerVector(double[][] matrix, ColorWrapper vector) {
        float[] rgb = new float[3];
        Arrays.fill(rgb, 0);
        for (int i = 0; i < matrix.length; i++) {
            for (int t = 0; t < 3; t++) {
                rgb[i] += matrix[i][t] * vector.getRGB()[t];
            }
        }

        return new ColorWrapper(rgb[0], rgb[1], rgb[2]);
    }
}
