package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.handler.ZoomInHandler;
import ch.supsi.editor2d.frontend.gui.receiver.ZoomInReceiver;
import javafx.scene.image.ImageView;

public class ZoomInCommand<T extends ZoomInHandler> extends AbstractCommandParam<ZoomInHandler,ImageView> {

    protected ZoomInCommand(ZoomInHandler handler) {
        super(handler);
    }

    // factory method
    public static ZoomInCommand<ZoomInHandler> create(ZoomInHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new ZoomInCommand<>(handler);
    }

    @Override
    public void execute(ImageView imageView) {
        handler.zoomIn(imageView);
    }
}
