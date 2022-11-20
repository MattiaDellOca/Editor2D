package ch.supsi.editor2d.frontend.gui.command;

public abstract class AbstractCommand<T extends Receiver> implements Command{

        protected T receiver;

        protected AbstractCommand(T receiver) {
            this.receiver = receiver;
        }

}
