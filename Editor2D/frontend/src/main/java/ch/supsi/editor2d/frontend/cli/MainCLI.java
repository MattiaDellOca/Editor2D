package ch.supsi.editor2d.frontend.cli;

import ch.supsi.editor2d.backend.helper.FilterPipeline;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.pipeline.PipelineObserver;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;

import java.util.Arrays;

public class MainCLI implements PipelineObserver {
    public static void main(String[] args) {
        MainCLI mainCLI = new MainCLI();

        // Image to edit
        ImageWrapper wrapper = new ImageWrapper();

        // Create FilterPipeline
        FilterPipeline pipeline = new FilterPipeline();
        pipeline.addObserver(mainCLI);

        // add tasks to pipeline
        FilterTask task1 = new FilterTask();
        FilterTask task2 = new FilterTask();
        FilterTask task3 = new FilterTask();
        FilterTask task4 = new FilterTask();
        pipeline.add(Arrays.asList(task1, task2, task3, task4));

        // Print start value
        System.out.println("START: " + wrapper.exampleData);

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
