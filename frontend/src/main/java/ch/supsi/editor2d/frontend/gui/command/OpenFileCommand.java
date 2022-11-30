package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.frontend.gui.model.AddFilterHandler;
import ch.supsi.editor2d.frontend.gui.model.OpenFileHandler;

import java.io.File;

public class OpenFileCommand extends AbstractCommand<OpenFileHandler> implements Command {

    protected OpenFileCommand(OpenFileHandler handler) {
        super(handler);
    }

    //factory method
    public static OpenFileCommand create(OpenFileHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new OpenFileCommand(handler);
    }

    @Override
    public void execute() {
        handler.openFile();
    }
}
