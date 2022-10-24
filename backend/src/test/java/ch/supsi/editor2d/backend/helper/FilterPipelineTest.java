package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.filter.GrayscaleFilter;
import ch.supsi.editor2d.backend.model.filter.SepiaFilter;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

// FIXME: Add tests for observers
public class FilterPipelineTest {

    private FilterPipeline pipeline;

    @BeforeEach
    public void reset() {
        // Creates a new FilterPipeline with some dummy data as image
        pipeline = new FilterPipeline();
    }

    @Test
    void addTasksIntoQueue() {
        // Try to add a couple of tasks into the pipeline
        // and check if the queue is not empty
        pipeline.add(new FilterTask(new FlipFilter()));
        pipeline.add(new FilterTask(new GrayscaleFilter()));
        pipeline.add(new FilterTask(new SepiaFilter()));

        assert !pipeline.isEmpty();
    }

    @Test
    void emptyTasksQueue() {
        // Try to add a couple of tasks into the pipeline
        // and check if the queue is not empty
        pipeline.add(new FilterTask(new FlipFilter()));
        pipeline.add(new FilterTask(new GrayscaleFilter()));
        pipeline.add(new FilterTask(new SepiaFilter()));

        // Empty the queue
        pipeline.clear();

        assert pipeline.isEmpty();
    }

    @Test
    void runPipelineFlipFilter() {
        // Get image sample
        ImageWrapper sample = getImageWrapperSample();
        pipeline.add(new FilterTask(new FlipFilter()));

        // Expected result
        final ColorWrapper[][] expectedData = new ColorWrapper[][]{
                {ColorWrapper.WHITE, ColorWrapper.YELLOW, ColorWrapper.BLACK},
                {ColorWrapper.WHITE, ColorWrapper.RED, ColorWrapper.BLACK},
                {ColorWrapper.WHITE, ColorWrapper.YELLOW, ColorWrapper.BLACK}
        };

        // Run the pipeline using the
        try {
            FilterTaskResult result = pipeline.run(sample);
            assert Arrays.deepEquals(result.getResult().getData(), expectedData);

        } catch (PipelineException e) {
            fail();
        }
    }


    public ImageWrapper getImageWrapperSample () {
        return new ImageWrapper(3,3, new ColorWrapper[][]{
            {ColorWrapper.BLACK, ColorWrapper.YELLOW, ColorWrapper.WHITE},
            {ColorWrapper.BLACK, ColorWrapper.RED, ColorWrapper.WHITE},
            {ColorWrapper.BLACK, ColorWrapper.YELLOW, ColorWrapper.WHITE}
        });
    }
}
