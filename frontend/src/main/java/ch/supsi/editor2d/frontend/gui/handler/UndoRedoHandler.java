package ch.supsi.editor2d.frontend.gui.handler;

public interface UndoRedoHandler extends Handler {
    void undo();

    void redo();
}
