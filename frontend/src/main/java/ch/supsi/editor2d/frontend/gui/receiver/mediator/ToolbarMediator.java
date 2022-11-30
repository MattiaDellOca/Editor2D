package ch.supsi.editor2d.frontend.gui.receiver.mediator;

import ch.supsi.editor2d.frontend.gui.event.*;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.receiver.AbstractReceiver;
import javafx.scene.control.MenuItem;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ToolbarMediator<T extends Observable> extends AbstractReceiver<DataModel> implements PropertyChangeListener {
    private final MenuItem undoItem;
    private final MenuItem redoItem;
    private final MenuItem exportItem;

    protected ToolbarMediator(DataModel model, MenuItem undoItem, MenuItem redoItem,MenuItem exportItem) {
        super(model);
        this.undoItem = undoItem;
        this.redoItem = redoItem;
        this.exportItem = exportItem;

        this.undoItem.setDisable(true);
        this.redoItem.setDisable(true);
        this.exportItem.setDisable(true);
    }

    // factory method
    public static ToolbarMediator<DataModel> create(DataModel model, MenuItem undoItem, MenuItem redoItem,MenuItem exportItem) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("model cannot be null!");
        }

        if (undoItem == null) {
            throw new IllegalArgumentException("undo item cannot be null!");
        }

        if (redoItem == null) {
            throw new IllegalArgumentException("redo item cannot be null!");
        }

        if (exportItem == null) {
            throw new IllegalArgumentException("export item cannot be null!");
        }

        return new ToolbarMediator<>(model, undoItem, redoItem,exportItem);
    }

    public void propertyChange(PropertyChangeEvent event) {
        if (event instanceof AddedFilterEvent) {
            this.enableDisableButtons();
        }else if (event instanceof RemovedFilterEvent) {
            this.enableDisableButtons();
        } else if (event instanceof UndoneEvent) {
            this.enableDisableButtons();
        } else if (event instanceof RedoneEvent) {
            this.enableDisableButtons();
        } else if(event instanceof ImageLoadedEvent){
            exportItem.setDisable(false);
        }
    }
    //TODO: when implement memento pattern check this method to enable/disable runPipeline
    private void enableDisableButtons() {
        // undo
        if (model.getUndoRedoPointer() > 0) {
            this.undoItem.setDisable(false);
        } else {
            this.undoItem.setDisable(true);
        }

        // redo
        if (model.getUndoRedoPointer() < model.getSavedStatesCount()) {
            this.redoItem.setDisable(false);
        } else {
            this.redoItem.setDisable(true);
        }
    }
}
