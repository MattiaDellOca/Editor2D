package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.model.ColorTest;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.filter.GrayscaleFilter;
import ch.supsi.editor2d.backend.model.filter.SepiaFilter;
import ch.supsi.editor2d.backend.model.filter.SharpenFilter;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

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
    void addRemoveTasksIntoQueue() {
        // Try to add a couple of tasks into the pipeline
        // and check if the queue is not empty
        pipeline.add(new FilterTask(new FlipFilter()));

        FilterTask willBeRemoved = new FilterTask(new SharpenFilter());
        pipeline.add(willBeRemoved);
        pipeline.remove(willBeRemoved);

        pipeline.add(new FilterTask(new SepiaFilter()));

        assert pipeline.getTasks().size() == 2;
    }

    @Test
    void runPipelineFlipFilter() {
        // Get image sample
        ImageWrapper sample = getImageWrapperSample();
        pipeline.add(new FilterTask(new FlipFilter()));
        pipeline.add(new FilterTask(new FlipFilter()));

        // Run the pipeline using the
        try {
            FilterTaskResult result = pipeline.run(sample);
            assert Arrays.deepEquals(result.getResult().getData(), sample.getData());

        } catch (PipelineException e) {
            fail();
        }
    }


    public ImageWrapper getImageWrapperSample() {
        return new ImageWrapper(3, 3, new ColorWrapper[][]{
                {ColorTest.BLACK, ColorTest.YELLOW, ColorTest.WHITE},
                {ColorTest.BLACK, ColorTest.RED, ColorTest.WHITE},
                {ColorTest.BLACK, ColorTest.YELLOW, ColorTest.WHITE}
        });
    }
}
