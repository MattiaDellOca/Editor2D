package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.handler.ZoomInHandler;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.scene.image.ImageView;

public class ZoomInReceiver  <T extends Observable> extends AbstractReceiver<DataModel>  implements ZoomInHandler {

    protected ZoomInReceiver(DataModel model) {
        super(model);
    }
    //factory method
    public static ZoomInReceiver<DataModel> create(DataModel model) throws InstantiationException {
        if (model == null) {
            throw new InstantiationException("controller model cannot be null!");
        }

        return new ZoomInReceiver<>(model);
    }

    @Override
    public void zoomIn(ImageView imageView) {
        model.zoomIn(imageView);
    }
}
