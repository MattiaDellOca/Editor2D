package ch.supsi.editor2d.backend.model.task;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.MatrixFilter;

public class FilterTask implements Task<ImageWrapper, FilterTaskResult> {

    private final MatrixFilter filter;

    public FilterTask (MatrixFilter filter) {
        this.filter = filter;
    }

    @Override
    public FilterTaskResult execute(ImageWrapper input) {
        // Read last letter of exampleData + compute next letter
        /*
            char lastLetter = input.exampleData.charAt(input.exampleData.length() - 1);
            char nextLetter = (char) (lastLetter + 1);
            input.exampleData += nextLetter;
            return new FilterTaskResult(input);
         */
        return new FilterTaskResult(input);
    }
}
