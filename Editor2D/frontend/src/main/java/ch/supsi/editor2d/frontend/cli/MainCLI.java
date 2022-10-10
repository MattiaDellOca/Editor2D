package ch.supsi.editor2d.frontend.cli;

import ch.supsi.editor2d.backend.helper.FilterPipeline;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.IdentityFilter;
import ch.supsi.editor2d.backend.model.pipeline.PipelineObserver;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class MainCLI implements PipelineObserver {
    public static void main(String[] args) {
        MainCLI mainCLI = new MainCLI();

        // Image to edit
        ImageWrapper wrapper = new ImageWrapper(10, 10, new Color[10][10]);

        // Create FilterPipeline
        FilterPipeline pipeline = new FilterPipeline();
        pipeline.addObserver(mainCLI);

        // add tasks to pipeline
        FilterTask task1 = new FilterTask(new IdentityFilter());
        FilterTask task2 = new FilterTask(new IdentityFilter());
        FilterTask task3 = new FilterTask(new IdentityFilter());
        FilterTask task4 = new FilterTask(new IdentityFilter());
        pipeline.add(Arrays.asList(task1, task2, task3, task4));

        // Start pipeline
        FilterTaskResult result = pipeline.run(wrapper);
        System.out.println(result);
    }

    @Override
    public void finished() {
        System.out.println("=== OBSERVER: PIPELINE HAS FINISHED ===");
    }

    @Override
    public void update(float progress) {
        System.out.println("=== OBSERVER: " + (progress*100) + "%");
    }
}
