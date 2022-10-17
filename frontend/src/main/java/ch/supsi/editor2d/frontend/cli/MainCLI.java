package ch.supsi.editor2d.frontend.cli;

import ch.supsi.editor2d.backend.helper.FilterPipeline;
import ch.supsi.editor2d.backend.model.pipeline.PipelineObserver;

public class MainCLI implements PipelineObserver {
    public static void main(String[] args) {
        // Just to showcase how to use observers until the actual test is written
        MainCLI mainCLI = new MainCLI();
        FilterPipeline pipeline = new FilterPipeline();
        pipeline.addObserver(mainCLI);

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
