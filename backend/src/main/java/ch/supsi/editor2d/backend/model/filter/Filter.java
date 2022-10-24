package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.model.ImageWrapper;

public abstract class Filter implements Applicable {
    protected final String name;

    public Filter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public abstract ImageWrapper apply(ImageWrapper image) throws FilterApplyException;
}
