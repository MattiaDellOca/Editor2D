package ch.supsi.editor2d.backend.repository;


import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.service.IImageService;

/**
 * Father handler for Chain of responsibility pattern
 */
public abstract class ImageRepositoryHandler implements IImageService {
    protected ImageRepositoryHandler successor;

    public void setSuccessor(ImageRepositoryHandler successor) {
        this.successor = successor;
    }

    @Override
    public abstract ImageWrapper handleLoad(String extension, String path) throws FileReadingException;

}

