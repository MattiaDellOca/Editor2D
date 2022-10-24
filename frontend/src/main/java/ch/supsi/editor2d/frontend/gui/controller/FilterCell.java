package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.objectPresentation.FilterPresentable;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class FilterCell extends ListCell<Filter> {
    private Parent root;
    private FilterCellViewController filterCellViewController;

    public FilterCell(DataModel model) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/filterCellView.fxml"));
        try {
            root = loader.load();
            filterCellViewController = loader.getController();
            filterCellViewController.initModel(model);
        } catch (IOException ignored) {}
    }


    @Override
    protected void updateItem(Filter filter, boolean empty) {
        super.updateItem(filter, empty);

        if (empty || filter == null) {
            setGraphic(null);
        } else {
            FilterPresentable filterPresentable = new FilterPresentable();
            filterCellViewController.setFilterName(filterPresentable.present(filter)); //TODO not the best solution
            setGraphic(root);
        }
    }
}
