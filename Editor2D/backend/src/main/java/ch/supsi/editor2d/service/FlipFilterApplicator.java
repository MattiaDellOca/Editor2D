package ch.supsi.editor2d.service;

import ch.supsi.editor2d.model.ImageWrapper;
import ch.supsi.editor2d.model.MatrixFilter;

public class FlipFilterApplicator implements Applicable {
    @Override
    public ImageWrapper apply(MatrixFilter filter, ImageWrapper image) {
        int imageWidth = image.getImage().length;
        double[][] flipMatrix = generateFlipMatrix(imageWidth);

        return null;
    }

    public double[][] generateFlipMatrix(int imageWidth) {
        if(imageWidth < 1)
            throw new IllegalArgumentException();

        double[][] flipMatrix = new double[imageWidth][imageWidth];
        for(int i = 0; i < imageWidth; i ++) {
            for(int t = 0; t < imageWidth; t ++) {
                if(t == imageWidth - 1 - i)
                    flipMatrix[i][t] = 1;
                else
                    flipMatrix[i][t] = 0;
            }
        }

        return flipMatrix;
    }

}
