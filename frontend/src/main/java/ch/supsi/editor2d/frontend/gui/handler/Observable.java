package ch.supsi.editor2d.frontend.gui.handler;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Observable implements Handler {

    protected boolean changed;

    protected PropertyChangeSupport pcs;

    public Observable() {
        this.pcs = new PropertyChangeSupport(this);
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return this.pcs;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }

}
