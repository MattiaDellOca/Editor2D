package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public abstract class Filter implements Applicable {
    @Override
    public abstract ImageWrapper apply(ImageWrapper image);
}
