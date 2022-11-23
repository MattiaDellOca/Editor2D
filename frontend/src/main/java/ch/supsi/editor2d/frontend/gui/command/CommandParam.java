package ch.supsi.editor2d.frontend.gui.command;

public interface CommandParam<T> {
    void execute(T t);
}
