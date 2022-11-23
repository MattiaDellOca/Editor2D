package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.model.ExitHandler;
import javafx.stage.Stage;

public class ExitReceiver {
    protected ExitHandler handler;

    protected ExitReceiver(ExitHandler handler) {
        this.handler = handler;
    }

    //factory method
    public static ExitReceiver create(ExitHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("receiver handler cannot be null!");
        }

        return new ExitReceiver(handler);
    }

    public void exit(Stage stage) {
        handler.exit(stage);
    }
}
