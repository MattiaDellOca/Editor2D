package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.handler.OkHandler;
import ch.supsi.editor2d.frontend.gui.handler.ZoomOutHandler;
import ch.supsi.editor2d.frontend.gui.receiver.ZoomOutReceiver;
import javafx.scene.image.ImageView;

public class ZoomOutCommand<T extends ZoomOutHandler> extends AbstractCommandParam<ZoomOutHandler, ImageView> {

    protected ZoomOutCommand(ZoomOutHandler handler) {
        super(handler);
    }

    // factory method
    public static ZoomOutCommand<ZoomOutHandler> create(ZoomOutHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new ZoomOutCommand<>(handler);
    }

    @Override
    public void execute(ImageView imageView) {
        handler.zoomOut(imageView);
    }
}
