package ch.supsi.editor2d.backend.model.task;

import ch.supsi.editor2d.backend.model.ImageWrapper;

// FIXME: Change the value from String to ImageWrapper (will be added soon)
public class FilterTaskResult extends TaskResult<ImageWrapper> {
    public FilterTaskResult(ImageWrapper result) {
        this.setResult(result);
    }
}



