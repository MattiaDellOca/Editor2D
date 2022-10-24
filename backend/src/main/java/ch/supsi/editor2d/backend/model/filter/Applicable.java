package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.model.ImageWrapper;

public interface Applicable {
    ImageWrapper apply(ImageWrapper image) throws FilterApplyException;
}
