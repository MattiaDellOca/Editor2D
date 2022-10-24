package ch.supsi.editor2d.backend.model.filter;

public class SharpenFilter extends KernelMatrixFilter{
    public SharpenFilter() {
        super(generateMatrix());
    }

    private static double[][] generateMatrix(){
        return new double[][]{
                {0.,-1.,0.},
                {-1,5.,-1.},
                {0,-1.,0},
        };
    }
}
