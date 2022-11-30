package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.handler.OpenFileHandler;

public class OpenFileCommand<T extends OpenFileHandler> extends AbstractCommand<OpenFileHandler>  {

    protected OpenFileCommand(OpenFileHandler handler) {
        super(handler);
    }

    //factory method
    public static OpenFileCommand<OpenFileHandler> create(OpenFileHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new OpenFileCommand<>(handler);
    }

    @Override
    public void execute() {
        handler.openFile();
    }
}
