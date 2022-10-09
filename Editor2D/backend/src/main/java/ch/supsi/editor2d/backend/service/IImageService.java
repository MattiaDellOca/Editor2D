package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.model.ImageWrapper;

/**
 * Interface for every image service
 */
public interface IImageService {
    ImageWrapper getImage(String path);
}
