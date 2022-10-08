package ch.supsi.editor2d.backend.model.task;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public class FilterTask implements Task<ImageWrapper, FilterTaskResult> {
    @Override
    public FilterTaskResult execute(ImageWrapper input) {
        // Read last letter of exampleData + compute next letter
        char lastLetter = input.exampleData.charAt(input.exampleData.length() - 1);
        char nextLetter = (char) (lastLetter + 1);
        input.exampleData += nextLetter;
        return new FilterTaskResult(input);
    }
}
