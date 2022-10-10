package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.model.FlipFilter;
import ch.supsi.editor2d.backend.model.ImageWrapper;

public class FlipFilterApplicator implements Applicable {
    @Override
    public ImageWrapper apply(ImageWrapper image) {
        int width = image.getWidth();
        FlipFilter flipFilter = new FlipFilter(width);
        return MatrixMultiplier.applyScalarFilter(image, flipFilter);
    }

}
