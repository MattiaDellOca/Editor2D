package ch.supsi.editor2d.frontend.gui.receiver.mediator;

import ch.supsi.editor2d.frontend.gui.command.RedoCommand;
import ch.supsi.editor2d.frontend.gui.command.UndoCommand;
import ch.supsi.editor2d.frontend.gui.event.*;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.handler.UndoRedoHandler;
import ch.supsi.editor2d.frontend.gui.receiver.AbstractReceiver;
import javafx.scene.Parent;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MementoShortcutMediator<T extends Observable> extends AbstractReceiver<DataModel> implements PropertyChangeListener {

    private final Parent parent;
    private final KeyCombination undoKeyCombination;
    private final KeyCombination redoKeyCombination;
    private final UndoCommand<UndoRedoHandler> undoCommand;
    private final RedoCommand<UndoRedoHandler> redoCommand;

    protected MementoShortcutMediator(DataModel model, Parent parent, KeyCombination undoKeyCombination, KeyCombination redoKeyCombination, UndoCommand<UndoRedoHandler> undoCommand, RedoCommand<UndoRedoHandler> redoCommand) {
        super(model);
        this.parent = parent;
        this.undoKeyCombination = undoKeyCombination;
        this.redoKeyCombination = redoKeyCombination;
        this.undoCommand = undoCommand;
        this.redoCommand = redoCommand;
    }

    // factory method
    public static MementoShortcutMediator<DataModel> create(DataModel model, Parent parent, KeyCombination undoKeyCombination, KeyCombination redoKeyCombination, UndoCommand<UndoRedoHandler> undoCommand, RedoCommand<UndoRedoHandler> redoCommand) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("model cannot be null!");
        }

        if (parent == null) {
            throw new IllegalArgumentException("parent cannot be null!");
        }

        if (undoKeyCombination == null) {
            throw new IllegalArgumentException("undoKeyCombination cannot be null!");
        }

        if (redoKeyCombination == null) {
            throw new IllegalArgumentException("redoKeyCombination cannot be null!");
        }

        if (undoCommand == null) {
            throw new IllegalArgumentException("undoCommand cannot be null!");
        }

        if (redoCommand == null) {
            throw new IllegalArgumentException("redoCommand cannot be null!");
        }

        return new MementoShortcutMediator<>(model, parent, undoKeyCombination, redoKeyCombination, undoCommand, redoCommand);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event instanceof AddedFilterEvent || event instanceof RemovedFilterEvent || event instanceof UndoneEvent || event instanceof RedoneEvent) {
            enableShortcuts();
        }
    }

    private void enableShortcuts() {

        // undo
        if (model.canUndo()) {
            //active undo shortcut
            //check if shortcut is already active
            if (!parent.getScene().getAccelerators().containsKey(undoKeyCombination)) {
                parent.getScene().getAccelerators().put(undoKeyCombination, undoCommand::execute);
            }
        } else {
            //disable undo shortcut
            parent.getScene().getAccelerators().remove(undoKeyCombination);
        }

        // redo
        if (model.canRedo()) {
            //active redo shortcut
            //check if shortcut is already active
            if (!parent.getScene().getAccelerators().containsKey(redoKeyCombination)) {
                parent.getScene().getAccelerators().put(redoKeyCombination, redoCommand::execute);
            }
        } else {
            //disable redo shortcut
            parent.getScene().getAccelerators().remove(redoKeyCombination);
        }

    }
}
