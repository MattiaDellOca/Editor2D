package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public interface IImageRepository {

    public ImageWrapper loadImage(String path);
}
