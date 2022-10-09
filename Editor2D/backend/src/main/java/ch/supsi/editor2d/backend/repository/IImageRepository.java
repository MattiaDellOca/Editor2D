package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.model.ImageWrapper;

/**
 * Interface for every image repository
 */
public interface IImageRepository {

    ImageWrapper loadImage(String path);
}
