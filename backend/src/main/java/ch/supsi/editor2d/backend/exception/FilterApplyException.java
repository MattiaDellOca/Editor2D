package ch.supsi.editor2d.backend.exception;

/**
 * Exception for all kind of problem during filter application
 */
public class FilterApplyException extends PipelineException {

    public FilterApplyException(String message) {
        super(message);
    }
}
