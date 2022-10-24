package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.model.ImageWrapper;

/**
 * Interface which declared a method that defines how a filter has to be applied mathematically
 */
public interface Applicable {
    ImageWrapper apply(ImageWrapper image);
}
