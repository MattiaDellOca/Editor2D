package ch.supsi.editor2d.backend.model.filter;

public class BoxBlurFilter extends KernelMatrixFilter{
    public BoxBlurFilter() {
        super(generateMatrix());
    }

    /*
    Gaussian blur 5x5 Approximation
     */
    private static double[][] generateMatrix(){
       // double[][] blurMatrix = {{1./16,2./16,1./16},{2./16,4./16,2./16},{1./16,2./16,1./16}};
        /*double[][] blurMatrix = {
                {1./9,1./9,1./9},
                {1./9,1./9,1./9},
                {1./9,1./9,1./9},
        };*/
        double[][] blurMatrix = {
                {0.,-1.,0.},
                {-1,5.,-1.},
                {0,-1.,0},
        };
        return blurMatrix;
    }
}
