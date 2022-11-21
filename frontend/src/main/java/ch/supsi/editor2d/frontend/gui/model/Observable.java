package ch.supsi.editor2d.frontend.gui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Observable {

        protected PropertyChangeSupport pcs;

        public Observable() {
            this.pcs = new PropertyChangeSupport(this);
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
