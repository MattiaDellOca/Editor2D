package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.model.ImageWrapper;

/**
 * Interface implemented in every ImageRepository for Dependency Inversion
 */
public interface IImageService {
    ImageWrapper loadImage(String path);
}
