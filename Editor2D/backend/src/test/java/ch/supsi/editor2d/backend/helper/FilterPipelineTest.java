package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.filter.GrayscaleFilter;
import ch.supsi.editor2d.backend.model.filter.SepiaFilter;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
        pipeline.add(new FilterTask(new FlipFilter(10)));
        pipeline.add(new FilterTask(new GrayscaleFilter()));
        pipeline.add(new FilterTask(new SepiaFilter()));

        assert !pipeline.isEmpty();
    }

    @Test
    void emptyTasksQueue() {
        // Try to add a couple of tasks into the pipeline
        // and check if the queue is not empty
        pipeline.add(new FilterTask(new FlipFilter(10)));
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
        pipeline.add(new FilterTask(new FlipFilter(sample.getWidth())));

        // Expected result
        final Color[][] expectedData = new Color[][]{
                {Color.WHITE, Color.VIOLET, Color.BLACK},
                {Color.WHITE, Color.RED, Color.BLACK},
                {Color.WHITE, Color.VIOLET, Color.BLACK}
        };

        // Run the pipeline using the
        FilterTaskResult result = pipeline.run(sample);
        assert Arrays.deepEquals(result.getResult().getData(), expectedData);
    }


    public ImageWrapper getImageWrapperSample () {
        return new ImageWrapper(3,3, new Color[][]{
            {Color.BLACK, Color.VIOLET, Color.WHITE},
            {Color.BLACK, Color.RED, Color.WHITE},
            {Color.BLACK, Color.VIOLET, Color.WHITE}
        });
    }
}
