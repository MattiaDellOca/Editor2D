package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.receiver.AboutReceiver;
import javafx.stage.Stage;

public class AboutCommand implements CommandParam<Stage> {
    protected AboutReceiver receiver;

    protected AboutCommand(AboutReceiver receiver) {
        super();
        this.receiver = receiver;
    }

    // factory method
    public static AboutCommand create(AboutReceiver receiver) throws InstantiationException {
        if (receiver == null) {
            throw new InstantiationException("command receiver cannot be null!");
        }

        return new AboutCommand(receiver);
    }

    @Override
    public void execute(Stage aboutStage) {
        receiver.about(aboutStage);
    }
}
