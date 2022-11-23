package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.receiver.ZoomInReceiver;
import javafx.scene.image.ImageView;

public class ZoomInCommand implements CommandParam<ImageView> {
    protected ZoomInReceiver receiver;

    protected ZoomInCommand(ZoomInReceiver receiver) {
        super();
        this.receiver = receiver;
    }

    // factory method
    public static ZoomInCommand create(ZoomInReceiver receiver) throws InstantiationException {
        if (receiver == null) {
            throw new InstantiationException("command receiver cannot be null!");
        }

        return new ZoomInCommand(receiver);
    }

    @Override
    public void execute(ImageView imageView) {
        receiver.zoomIn(imageView);
    }
}
