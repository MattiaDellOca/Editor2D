package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.handler.RunPipelineHandler;
import ch.supsi.editor2d.frontend.gui.model.DataModel;

public class RunPipelineReceiver<T extends Observable> extends AbstractReceiver<DataModel> implements RunPipelineHandler {

    protected RunPipelineReceiver(DataModel model) {
        super(model);
    }
    //factory method
public static RunPipelineReceiver<DataModel> create(DataModel model) throws InstantiationException {
        if (model == null) {
            throw new InstantiationException("controller model cannot be null!");
        }
        return new RunPipelineReceiver<>(model);
    }

    @Override
    public void runPipeline() {
        model.runPipeline();
    }
}

