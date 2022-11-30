package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.handler.ZoomOutHandler;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.scene.image.ImageView;

public class ZoomOutReceiver<T extends Observable> extends AbstractReceiver<DataModel>  implements ZoomOutHandler {

    protected ZoomOutReceiver(DataModel model) {
        super(model);
    }
    //factory method
    public static ZoomOutReceiver<DataModel> create(DataModel model) throws InstantiationException {
        if (model == null) {
            throw new InstantiationException("controller model cannot be null!");
        }

        return new ZoomOutReceiver<>(model);
    }

    @Override
    public void zoomOut(ImageView imageView) {
        model.zoomOut(imageView);
    }
}
