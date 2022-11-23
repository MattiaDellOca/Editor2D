package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.model.ZoomOutHandler;
import javafx.scene.image.ImageView;

public class ZoomOutReceiver {
    protected ZoomOutHandler handler;

    protected ZoomOutReceiver(ZoomOutHandler handler) {
        this.handler = handler;
    }

    //factory method
    public static ZoomOutReceiver create(ZoomOutHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("receiver handler cannot be null!");
        }

        return new ZoomOutReceiver(handler);
    }

    public void zoomOut(ImageView imageView) {
        handler.zoomOut(imageView);
    }
}
