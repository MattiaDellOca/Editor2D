package ch.supsi.editor2d.backend.exception;

/**
 * Exception thrown when occurs an error during the writing of a file
 */
public class FileWritingException extends Exception {
    public FileWritingException(String message) {
        super(message);
    }
}
