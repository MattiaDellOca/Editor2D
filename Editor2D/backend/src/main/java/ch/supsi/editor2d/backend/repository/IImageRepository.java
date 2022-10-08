package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public interface IImageRepository<T extends ImageWrapper> {

    public T loadImage();
}
