package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.handler.AboutHandler;
import ch.supsi.editor2d.frontend.gui.handler.AddFilterHandler;
import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.stage.Stage;

public class AboutReceiver <T extends Observable> extends AbstractReceiver<DataModel> implements AboutHandler {
    protected AboutHandler handler;

    protected AboutReceiver(DataModel model) {
        super(model);
    }
    //factory method
    public static AboutReceiver<DataModel> create(DataModel model) throws InstantiationException {
        if (model == null) {
            throw new InstantiationException("controller model cannot be null!");
        }

        return new AboutReceiver<>(model);
    }

    public void about(Stage aboutStage) {
        handler.about(aboutStage);
    }
}
