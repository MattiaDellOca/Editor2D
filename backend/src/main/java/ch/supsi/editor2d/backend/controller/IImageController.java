package ch.supsi.editor2d.backend.controller;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.model.ImageWrapper;

import java.io.File;

/**
 * Interface implemented in ImageService for Dependency Inversion
 */
public interface IImageController {

    ImageWrapper getImage(String path) throws FileReadingException;

    void exportImage(File directory, ImageWrapper data) throws FileWritingException;
}
