package ch.supsi.editor2d.frontend.gui.model;

import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.objectPresentation.FilterPresentable;
import ch.supsi.editor2d.frontend.gui.controller.CellViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class FilterCell extends ListCell<Filter> {
    private Parent root;
    private CellViewController cellViewController;

    public FilterCell() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cellView.fxml"));
        try {
            root = loader.load();
            cellViewController = loader.getController();
        } catch (IOException ignored) {}
    }


    @Override
    protected void updateItem(Filter filter, boolean empty) {
        super.updateItem(filter, empty);
        if (empty || filter == null) {
            setGraphic(null);
        } else {
            FilterPresentable filterPresentable = new FilterPresentable();
            cellViewController.setFilterName(filterPresentable.present(filter));
            setGraphic(root);
        }
    }
}
