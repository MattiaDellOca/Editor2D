package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.handler.AboutHandler;
import javafx.stage.Stage;

public class AboutCommand<T extends AboutHandler> extends AbstractCommandParam<AboutHandler, Stage> {

    protected AboutCommand(AboutHandler handler) {
        super(handler);
    }

    // factory method
    public static AboutCommand<AboutHandler> create(AboutHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new AboutCommand<>(handler);
    }

    @Override
    public void execute(Stage aboutStage) {
        handler.about(aboutStage);
    }
}
