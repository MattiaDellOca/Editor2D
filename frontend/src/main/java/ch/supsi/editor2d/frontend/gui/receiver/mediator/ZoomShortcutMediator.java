package ch.supsi.editor2d.frontend.gui.receiver.mediator;

import ch.supsi.editor2d.frontend.gui.command.ZoomInCommand;
import ch.supsi.editor2d.frontend.gui.command.ZoomOutCommand;
import ch.supsi.editor2d.frontend.gui.controller.ImageViewController;
import ch.supsi.editor2d.frontend.gui.event.ImageLoadedEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.model.Observable;
import ch.supsi.editor2d.frontend.gui.receiver.AbstractReceiver;
import javafx.scene.Parent;
import javafx.scene.input.KeyCombination;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ZoomShortcutMediator<T extends Observable> extends AbstractReceiver<DataModel> implements PropertyChangeListener {

    private final Parent parent;
    private final KeyCombination zoomInKeyCombination;
    private final KeyCombination zoomOutKeyCombination;
    private final ZoomInCommand zoomInCommand;
    private final ZoomOutCommand zoomOutCommand;
    private final ImageViewController imageViewController;

    protected ZoomShortcutMediator(DataModel model, Parent parent, KeyCombination zoomInKeyCombination, KeyCombination zoomOutKeyCombination, ZoomInCommand zoomInCommand, ZoomOutCommand zoomOutCommand, ImageViewController imageViewController) {
        super(model);
        this.parent = parent;
        this.zoomInKeyCombination = zoomInKeyCombination;
        this.zoomOutKeyCombination = zoomOutKeyCombination;
        this.zoomInCommand = zoomInCommand;
        this.zoomOutCommand = zoomOutCommand;
        this.imageViewController = imageViewController;
    }

    // factory method
    public static ZoomShortcutMediator<DataModel> create(DataModel model, Parent parent, KeyCombination zoomInKeyCombination, KeyCombination zoomOutKeyCombination, ZoomInCommand zoomInCommand, ZoomOutCommand zoomOutCommand, ImageViewController imageViewController) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("model cannot be null!");
        }

        if (parent == null) {
            throw new IllegalArgumentException("parent cannot be null!");
        }

        if (zoomInKeyCombination == null) {
            throw new IllegalArgumentException("zoomInKeyCombination cannot be null!");
        }

        if (zoomOutKeyCombination == null) {
            throw new IllegalArgumentException("zoomOutKeyCombination cannot be null!");
        }

        if (zoomInCommand == null) {
            throw new IllegalArgumentException("zoomInCommand cannot be null!");
        }

        if (zoomOutCommand == null) {
            throw new IllegalArgumentException("zoomOutCommand cannot be null!");
        }

        if (imageViewController == null) {
            throw new IllegalArgumentException("imageViewController cannot be null!");
        }

        return new ZoomShortcutMediator<>(model, parent, zoomInKeyCombination, zoomOutKeyCombination, zoomInCommand, zoomOutCommand, imageViewController);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if(event instanceof ImageLoadedEvent){
            parent.setOnKeyPressed(event1 -> {
                if(zoomInKeyCombination.match(event1)){
                    zoomInCommand.execute(imageViewController.getImageView());
                }
                if(zoomOutKeyCombination.match(event1)){
                    zoomOutCommand.execute(imageViewController.getImageView());
                }
            });
        }
    }
}
