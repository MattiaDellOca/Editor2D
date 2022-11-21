package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.model.RunPipelineHandler;

public class RunPipelineReceiver {

    protected RunPipelineHandler handler;

    protected RunPipelineReceiver(RunPipelineHandler handler) {
        this.handler = handler;
    }

    //factory method
    public static RunPipelineReceiver create(RunPipelineHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("receiver handler cannot be null!");
        }

        return new RunPipelineReceiver(handler);
    }

    public void runPipeline() {
        handler.runPipeline();
    }
}

