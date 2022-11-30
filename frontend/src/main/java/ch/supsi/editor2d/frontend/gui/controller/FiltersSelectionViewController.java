package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.filter.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class FiltersSelectionViewController {
    @FXML
    private ListView<Filter> filterSelectionList;

    public ListView<Filter> getFilterSelectionList() {
        return filterSelectionList;
    }
}
