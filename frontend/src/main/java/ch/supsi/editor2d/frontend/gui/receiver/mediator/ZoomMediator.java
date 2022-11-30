package ch.supsi.editor2d.frontend.gui.receiver.mediator;

import ch.supsi.editor2d.frontend.gui.event.ImageLoadedEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.model.Observable;
import ch.supsi.editor2d.frontend.gui.receiver.AbstractReceiver;
import javafx.scene.control.Button;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ZoomMediator<T extends Observable> extends AbstractReceiver<DataModel> implements PropertyChangeListener {

    private final Button zoomInButton;
    private final Button zoomOutButton;

    protected ZoomMediator(DataModel model, Button zoomInButton, Button zoomOutButton) {
        super(model);
        this.zoomInButton = zoomInButton;
        this.zoomOutButton = zoomOutButton;

        this.zoomInButton.setDisable(true);
        this.zoomOutButton.setDisable(true);
    }

    // factory method
    public static ZoomMediator<DataModel> create(DataModel model, Button zoomInButton, Button zoomOutButton) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("model cannot be null!");
        }

        if (zoomInButton == null) {
            throw new IllegalArgumentException("zoomInButton cannot be null!");
        }

        if (zoomOutButton == null) {
            throw new IllegalArgumentException("zoomOutButton cannot be null!");
        }


        return new ZoomMediator<>(model, zoomInButton, zoomOutButton);
    }

    public void propertyChange(PropertyChangeEvent event) {
        if (event instanceof ImageLoadedEvent) {
            this.zoomInButton.setDisable(false);
            this.zoomOutButton.setDisable(false);
         }
    }

}
