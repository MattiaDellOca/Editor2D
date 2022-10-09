package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public interface IImageService {
    public ImageWrapper getImage(String path);
}
