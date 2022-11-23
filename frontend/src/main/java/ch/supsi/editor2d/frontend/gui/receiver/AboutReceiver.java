package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.model.AboutHandler;
import javafx.stage.Stage;

public class AboutReceiver {
    protected AboutHandler handler;

    protected AboutReceiver(AboutHandler handler) {
        this.handler = handler;
    }

    //factory method
    public static AboutReceiver create(AboutHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("receiver handler cannot be null!");
        }

        return new AboutReceiver(handler);
    }

    public void about(Stage aboutStage) {
        handler.about(aboutStage);
    }
}
