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

    public static ImageWrapper applyKernelFilter(ImageWrapper image, KernelMatrixFilter filter) {

        ColorWrapper[][] originalImage = image.getData();
        ColorWrapper[][] newImage = new ColorWrapper[image.getWidth() - 1][image.getHeight() - 1];

        for (int w = 1; w < image.getWidth() - 1; w++) {
            for (int h = 1; h < image.getHeight() - 1; h++) {

                /*ColorWrapper w_1h_1 = originalImage[h - 1][w - 1];
                ColorWrapper wh_1 = originalImage[h][w - 1];
                ColorWrapper w1h_1 = originalImage[h + 1][w - 1];
                ColorWrapper w_1h = originalImage[h - 1][w];
                ColorWrapper wh = originalImage[h][w];
                ColorWrapper w1h = originalImage[h + 1][w];
                ColorWrapper w_1h1 = originalImage[h - 1][w + 1];
                ColorWrapper wh1 = originalImage[h][w + 1];
                ColorWrapper w1h1 = originalImage[h + 1][w + 1];*/

                ColorWrapper[][] nearColors = new ColorWrapper[3][3];
                nearColors[0][0] = originalImage[h - 1][w - 1];
                nearColors[0][1] = originalImage[h][w - 1];
                nearColors[0][2] = originalImage[h + 1][w - 1];
                nearColors[1][0] = originalImage[h - 1][w];
                nearColors[1][1] = originalImage[h][w];
                nearColors[1][2] = originalImage[h + 1][w];
                nearColors[2][0] = originalImage[h - 1][w + 1];
                nearColors[2][1] = originalImage[h][w + 1];
                nearColors[2][2] = originalImage[h + 1][w + 1];

                float red = 0, green = 0, blue = 0;
                for (int i = 0; i < nearColors.length; i++) {
                    for(int j = 0; j< nearColors[i].length; j++){
                        red += nearColors[i][j].getRed();
                        green += nearColors[i][j].getRed();
                        blue += nearColors[i][j].getRed();
                    }
                }

                newImage[h][w] = new ColorWrapper(red/9,green/9,blue/9);
            }
        }

        return new ImageWrapper(newImage.length, newImage[0].length, newImage);
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
