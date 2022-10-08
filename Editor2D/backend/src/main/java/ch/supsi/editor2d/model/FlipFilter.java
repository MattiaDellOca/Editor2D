package ch.supsi.editor2d.model;

public class FlipFilter extends VariableMatrixFilter {

    // This method is temporary and it will be removed later
    public FlipFilter(int dimension) {
        matrix = new double[dimension][dimension];
        for(int i = 0; i < dimension; i ++) {
            for(int t = 0; t < dimension; t ++) {
                if(t == dimension - 1 - i) {
                    matrix[i][t] = 1;
                } else {
                    matrix[i][t] = 0;
                }
            }
        }
    }
}
