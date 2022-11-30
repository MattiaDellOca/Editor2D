package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.receiver.ExportReceiver;
import javafx.stage.Stage;

public class ExportPageCommand implements CommandParam<Stage> {
    protected ExportReceiver receiver;

    protected ExportPageCommand(ExportReceiver receiver) {
        super();
        this.receiver = receiver;
    }

    // factory method
    public static ExportPageCommand create(ExportReceiver receiver) throws InstantiationException {
        if (receiver == null) {
            throw new InstantiationException("command receiver cannot be null!");
        }

        return new ExportPageCommand(receiver);
    }

    @Override
    public void execute(Stage exportStage) {
        receiver.exportPage(exportStage);
    }
}

