package ch.supsi.editor2d.frontend.exception;

/**
 * Exception thrown when the user request to apply a filter before having loaded an image
 */
public class ImageNotLoadedException extends Exception {
    public ImageNotLoadedException() {
        super("You need to load an image before applying filters!");
    }
}
