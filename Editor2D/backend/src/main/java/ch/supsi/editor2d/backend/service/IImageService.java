package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public interface IImageService<T extends ImageWrapper> {
    public T getImage();
}
