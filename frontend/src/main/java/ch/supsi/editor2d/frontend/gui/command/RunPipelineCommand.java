package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.receiver.RunPipelineReceiver;

public class RunPipelineCommand implements Command {

    protected RunPipelineReceiver receiver;

    protected RunPipelineCommand(RunPipelineReceiver receiver) {
        super();
        this.receiver = receiver;
    }

    // factory method
    public static RunPipelineCommand create(RunPipelineReceiver receiver) throws InstantiationException {
        if (receiver == null) {
            throw new InstantiationException("command receiver cannot be null!");
        }

        return new RunPipelineCommand(receiver);
    }

    @Override
    public void execute() {
        receiver.runPipeline();
    }

}

