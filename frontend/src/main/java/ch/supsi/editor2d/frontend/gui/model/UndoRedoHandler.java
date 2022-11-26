package ch.supsi.editor2d.frontend.gui.model;

public interface UndoRedoHandler extends Handler {
    void undo();

    void redo();
}
