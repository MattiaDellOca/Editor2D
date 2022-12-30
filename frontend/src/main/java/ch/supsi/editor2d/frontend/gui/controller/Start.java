package ch.supsi.editor2d.frontend.gui.controller;


import ch.supsi.editor2d.backend.model.filter.*;
import ch.supsi.editor2d.frontend.gui.util.UIToolkit;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collection;

public class Start extends Application {

    public static final Collection<String> SUPPORTED_FORMATS = Arrays.asList("ppm", "pgm", "pbm");

    private static final Collection<Filter> FILTERS = Arrays.asList(new FlipFilter(), new GrayscaleFilter(),
            new SepiaFilter(), new SharpenFilter());

    @Override
    public void start(Stage stage) throws Exception {
        UIToolkit.buildAndStartStage(stage, FILTERS);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
