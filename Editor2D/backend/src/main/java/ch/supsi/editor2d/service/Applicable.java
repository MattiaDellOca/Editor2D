package ch.supsi.editor2d.service;

import ch.supsi.editor2d.model.ImageWrapper;
import ch.supsi.editor2d.model.MatrixFilter;

public interface Applicable {
    public ImageWrapper apply(MatrixFilter filter, ImageWrapper image);
}
