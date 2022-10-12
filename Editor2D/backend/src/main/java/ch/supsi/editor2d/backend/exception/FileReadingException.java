package ch.supsi.editor2d.backend.exception;

import java.io.IOException;

/**
 * Exception for all kind of problem during the image loading
 */
public class FileReadingException extends Exception {

    public FileReadingException(String message) {
        super(message);
    }
}
