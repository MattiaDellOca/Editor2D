package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.event.AddedFilterEvent;
import ch.supsi.editor2d.frontend.gui.event.RedoneEvent;
import ch.supsi.editor2d.frontend.gui.event.UndoneEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.model.Observable;
import javafx.scene.control.MenuItem;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ToolbarMediator<T extends Observable> extends AbstractController<DataModel> implements PropertyChangeListener {
    private final MenuItem undoItem;
    private final MenuItem redoItem;

    protected ToolbarMediator(DataModel model, MenuItem undoItem, MenuItem redoItem) {
        super(model);
        this.undoItem = undoItem;
        this.redoItem = redoItem;

        this.undoItem.setDisable(true);
        this.redoItem.setDisable(true);
    }

    // factory method
    public static ToolbarMediator<DataModel> create(DataModel model, MenuItem undoItem, MenuItem redoItem) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("model cannot be null!");
        }

        if (undoItem == null) {
            throw new IllegalArgumentException("undo item cannot be null!");
        }

        if (redoItem == null) {
            throw new IllegalArgumentException("redo item cannot be null!");
        }

        return new ToolbarMediator<>(model, undoItem, redoItem);
    }

    public void propertyChange(PropertyChangeEvent event) {
        if (event instanceof AddedFilterEvent) {
            this.enableDisableButtons();
        }
        else if (event instanceof UndoneEvent) {
            this.enableDisableButtons();

        } else if (event instanceof RedoneEvent) {
            this.enableDisableButtons();
        }
    }

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
