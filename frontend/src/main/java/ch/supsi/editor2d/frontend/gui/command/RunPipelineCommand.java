package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.handler.OkHandler;
import ch.supsi.editor2d.frontend.gui.handler.RunPipelineHandler;
import ch.supsi.editor2d.frontend.gui.receiver.RunPipelineReceiver;

public class RunPipelineCommand<T extends RunPipelineHandler> extends AbstractCommand<RunPipelineHandler>  {

    protected RunPipelineCommand(RunPipelineHandler handler) {
        super(handler);
    }

    // factory method
    public static RunPipelineCommand<RunPipelineHandler> create(RunPipelineHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new RunPipelineCommand<>(handler);
    }

    @Override
    public void execute() {
        handler.runPipeline();
    }

}

