package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.receiver.ZoomOutReceiver;
import javafx.scene.image.ImageView;

public class ZoomOutCommand implements CommandParam<ImageView> {
    protected ZoomOutReceiver receiver;

    protected ZoomOutCommand(ZoomOutReceiver receiver) {
        super();
        this.receiver = receiver;
    }

    // factory method
    public static ZoomOutCommand create(ZoomOutReceiver receiver) throws InstantiationException {
        if (receiver == null) {
            throw new InstantiationException("command receiver cannot be null!");
        }

        return new ZoomOutCommand(receiver);
    }

    @Override
    public void execute(ImageView imageView) {
        receiver.zoomOut(imageView);
    }
}
