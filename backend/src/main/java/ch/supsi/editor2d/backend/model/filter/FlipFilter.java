package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;

/**
 * This filter mirror the given image
 */
public class FlipFilter extends Filter {
    @Override
    public ImageWrapper apply(ImageWrapper image) {
        ColorWrapper[][] I = image.getData();
        ColorWrapper[][] R = new ColorWrapper[image.getHeight()][image.getWidth()];
        for(int h = 0; h < image.getHeight(); h ++) {
            for(int w = 0; w < image.getWidth(); w ++) {
                R[h][image.getWidth() - w - 1] = I[h][w];
            }
        }

        return new ImageWrapper(image.getWidth(), image.getHeight(), R);
    }
}
