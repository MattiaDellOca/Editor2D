package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.filter.*;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class FiltersSelectionViewController {
    @FXML
    private ListView<Filter> filterSelectionList;

    private DataModel model;

    /*private EventHandler<ImageUpdatedEvent> imageUpdated = event -> {};

    public EventHandler<ImageUpdatedEvent> getOnImageUpdated() { return this.imageUpdated; }

    public void setOnImageUpdated(EventHandler<ImageUpdatedEvent> event) { this.imageUpdated = event; }*/

    public ListView<Filter> getFilterSelectionList() {
        return filterSelectionList;
    }
}
