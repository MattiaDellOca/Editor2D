package ch.supsi.editor2d.backend.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;

/**
 * Interface for every image controller
 */
public interface IImageController {

    ImageWrapper getImage(String path);
}
