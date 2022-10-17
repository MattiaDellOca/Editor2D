package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public interface Applicable {
    ImageWrapper apply(ImageWrapper image);
}
