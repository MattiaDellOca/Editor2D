package ch.supsi.editor2d.frontend.gui.receiver.mediator;

import ch.supsi.editor2d.frontend.gui.event.AddedFilterEvent;
import ch.supsi.editor2d.frontend.gui.event.RedoneEvent;
import ch.supsi.editor2d.frontend.gui.event.RemovedFilterEvent;
import ch.supsi.editor2d.frontend.gui.event.UndoneEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.model.Observable;
import ch.supsi.editor2d.frontend.gui.receiver.AbstractReceiver;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RunPipelineMediator<T extends Observable> extends AbstractReceiver<DataModel> implements PropertyChangeListener {

    private final MenuItem runPipelineItem;
    private final Button runPipelineButton;

    protected RunPipelineMediator(DataModel model, MenuItem runPipelineItem, Button runPipelineButton) {
        super(model);
        this.runPipelineItem = runPipelineItem;
        this.runPipelineButton = runPipelineButton;
        this.runPipelineItem.setDisable(true);
        this.runPipelineButton.setDisable(true);
    }

    //factory method
    public static RunPipelineMediator<DataModel> create(DataModel model, MenuItem runPipelineItem, Button runPipelineButton) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("model cannot be null!");
        }

        if (runPipelineItem == null) {
            throw new IllegalArgumentException("runPipelineItem item cannot be null!");
        }

        if (runPipelineButton == null) {
            throw new IllegalArgumentException("runPipelineButton item cannot be null!");
        }

        return new RunPipelineMediator<>(model, runPipelineItem, runPipelineButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if(event instanceof AddedFilterEvent ){
            enableDisableButtons();
        } else if (event instanceof RemovedFilterEvent){
            enableDisableButtons();
        } else if (event instanceof UndoneEvent){
            enableDisableButtons();
        } else if (event instanceof RedoneEvent){
            enableDisableButtons();
        }
    }

    private void enableDisableButtons(){
        this.runPipelineItem.setDisable(model.getFilterPipeline().isEmpty());
        this.runPipelineButton.setDisable(model.getFilterPipeline().isEmpty());
    }
}
