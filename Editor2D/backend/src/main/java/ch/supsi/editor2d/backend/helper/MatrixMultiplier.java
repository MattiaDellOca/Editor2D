package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.MatrixFilter;
import javafx.scene.paint.Color;

public class MatrixMultiplier {
    public static double[][] multiplyMatrices(double[][] A, double[][] B) {
        if(A[0].length != B.length)
            return null;

        double[][] R = new double[A.length][B[0].length];
        for(int i = 0; i < A.length; i ++) {
            for(int t = 0; t < B[0].length; t ++) {
                for(int k = 0; k < A[0].length; k ++) {
                    R[i][t] += A[i][k] * B[k][t];
                }
            }
        }

        return R;
    }

    public static ImageWrapper applyScalarFilter(ImageWrapper image, MatrixFilter filter) {
        Color[][] I = image.getData();
        double[][] F = filter.getMatrix();
        if(I[0].length != F.length)
            return null;

        Color[][] R = new Color[I.length][F[0].length];
        for(int i = 0; i < I.length; i ++) {
            for(int t = 0; t < F[0].length; t ++) {
                Color[] row = new Color[I[0].length];
                System.out.println(I[0].length + " " + row.length);
                for(int k = 0; k < row.length; k ++)
                    row[k] = I[i][k];

                double[] column = new double[F.length];
                for(int k = 0; k < column.length; k ++)
                    column[k] = F[k][t];

                R[i][t] = dotProduct(row, column);
            }
        }

        return new ImageWrapper(image.getWidth(), image.getHeight(), R);
    }

    private static Color dotProduct(Color[] row, double[] column) {
        double red = 0, green = 0, blue = 0, opacity = 0;
        for(int i = 0; i < row.length; i ++) {
            red += row[i].getRed() * column[i];
            green += row[i].getGreen() * column[i];
            blue += row[i].getBlue() * column[i];
            opacity += row[i].getOpacity() * column[i];
        }

        return new Color(red, green, blue, opacity);
    }
}
