package ch.supsi.editor2d.backend.controller;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.service.ImageService;

import java.io.File;

public class ImageController {

    /**
     * Interface imageService
     */
    private final IImageController serviceLayer;

    /**
     * Constructor
     */
    public ImageController() {
        this.serviceLayer = new ImageService();
    }

    /**
     * Load an image from a file
     * @param path the path of the file
     * @return the image wrapper containing the image data
     * @throws FileReadingException if the file is not readable
     */
    public ImageWrapper getImage(String path) throws FileReadingException {
        return serviceLayer.getImage(path);
    }

    /**
     * Export the image to a specific directory
     * @param directory the directory where the image will be saved
     * @param imageData the image data
     * @throws FileWritingException if occurs an error during the writing process
     */
    public void exportImage(File directory, ImageWrapper imageData) throws FileWritingException {
        serviceLayer.exportImage(directory, imageData);
    }
}
