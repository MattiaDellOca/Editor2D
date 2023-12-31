package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.model.ImageWrapper;

/**
 * Interface implemented in every ImageRepository for Dependency Inversion
 */
public interface IImageService {
    ImageWrapper handleLoad(String extension, String path) throws FileReadingException;
    void handleSave(String extension, String filename, String path, ImageWrapper data) throws FileWritingException;
}

