package ch.supsi.editor2d.backend.repository;


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

    public abstract ImageWrapper handleLoad(String path);

}

