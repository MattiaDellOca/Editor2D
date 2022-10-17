package ch.supsi.editor2d.backend.model.filter;

// TODO: 10/10/22 abstract factory
public class FlipFilter extends TransformMatrixFilter {
    public FlipFilter(int width) {
        super(generateMatrix(width));
    }

    private static double[][] generateMatrix(int width) {
        if(width < 1)
            throw new IllegalArgumentException();

        double[][] flipMatrix = new double[width][width];
        for(int i = 0; i < width; i ++) {
            for(int t = 0; t < width; t ++) {
                if(t == width - 1 - i)
                    flipMatrix[i][t] = 1;
                else
                    flipMatrix[i][t] = 0;
            }
        }
        return flipMatrix;
    }
}
