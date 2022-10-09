package ch.supsi.editor2d.service;

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
}
