package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.receiver.ExitReceiver;
import javafx.stage.Stage;

public class ExitCommand implements CommandParam<Stage> {
    protected ExitReceiver receiver;

    public ExitCommand(ExitReceiver receiver) {
        super();
        this.receiver = receiver;
    }

    // factory method
    public static ExitCommand create(ExitReceiver receiver) throws InstantiationException {
        if (receiver == null) {
            throw new InstantiationException("command receiver cannot be null!");
        }

        return new ExitCommand(receiver);
    }

    @Override
    public void execute(Stage stage) {
        receiver.exit(stage);
    }
}
