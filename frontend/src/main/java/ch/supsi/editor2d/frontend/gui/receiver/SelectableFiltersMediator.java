package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.frontend.gui.event.ImageLoadedEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.model.Observable;
import javafx.scene.control.ListView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SelectableFiltersMediator<T extends Observable> extends AbstractController<DataModel> implements PropertyChangeListener {
    private final ListView<Filter> selectableFilters;

    protected SelectableFiltersMediator(DataModel model, ListView<Filter> selectableFilters) {
        super(model);
        this.selectableFilters = selectableFilters;

        this.selectableFilters.setDisable(true);
    }

    // factory method
    public static SelectableFiltersMediator<DataModel> create(DataModel model, ListView<Filter> selectableFilters) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("model cannot be null!");
        }

        if (selectableFilters == null) {
            throw new IllegalArgumentException("undo item cannot be null!");
        }

        return new SelectableFiltersMediator<>(model, selectableFilters);
    }

    public void propertyChange(PropertyChangeEvent event) {
        if (event instanceof ImageLoadedEvent) {
            this.selectableFilters.setDisable(false);
        }
    }
}
