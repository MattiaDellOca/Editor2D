package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.controller.IImageController;
import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.repository.*;

import java.io.File;

public class ImageService implements IImageController {
    private final IImageService repositoryLayer;

    public ImageService() {
        //Creation of the Chain Responsibility patter for loading image
        ImageRepositoryHandler h1 = new ImageRepositoryPBMHandler();
        ImageRepositoryHandler h2 = new ImageRepositoryPGMHandler();
        ImageRepositoryHandler h3 = new ImageRepositoryPPMHandler();
        h1.setSuccessor(h2);
        h2.setSuccessor(h3);
        repositoryLayer = h1;
    }

    @Override
    public ImageWrapper getImage(String path) throws FileReadingException {
        // The chain handler check if they are the responsible for loading the image with a specific extension

        int indexDot = path.lastIndexOf('.');
        if (indexDot == -1) {//dot not found
            throw new FileReadingException("This file does not have an extension");
        }
        //find extension and pass the process of loading to the chain
        String extension = path.substring(indexDot + 1);
        return repositoryLayer.handleLoad(extension, path);
    }

    @Override
    public void exportImage(File directory, ImageWrapper data) throws FileWritingException {
        // Check that the directory is actually a directory + check that is writable
        if (!directory.isDirectory() || !directory.canWrite()) {
            throw new FileWritingException("The directory is not writable");
        } else {
            // Pass data to the repository layer
            repositoryLayer.handleSave(directory.getAbsolutePath(), data);
        }
    }
}
