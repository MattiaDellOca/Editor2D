package ch.supsi.editor2d.backend.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;

/**
 * Interface implemented in ImageService for Dependency Inversion
 */
public interface IImageController {

    ImageWrapper getImage(String path);
}
