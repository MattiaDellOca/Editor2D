package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.handler.ZoomInHandler;
import javafx.scene.image.ImageView;

public class ZoomInReceiver implements ZoomInHandler {
    protected ZoomInHandler handler;

    protected ZoomInReceiver(ZoomInHandler handler) {
        this.handler = handler;
    }

    //factory method
    public static ZoomInReceiver create(ZoomInHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("receiver handler cannot be null!");
        }

        return new ZoomInReceiver(handler);
    }

    @Override
    public void zoomIn(ImageView imageView) {
        handler.zoomIn(imageView);
    }
}
