package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.filter.GrayscaleFilter;
import ch.supsi.editor2d.backend.model.filter.SepiaFilter;
import ch.supsi.editor2d.frontend.gui.event.ImageUpdatedEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class FiltersSelectionViewController {
    @FXML
    private ListView<Filter> filterSelectionList;

    @FXML
    private Pane imagePane;
    private DataModel model;

    private EventHandler<ImageUpdatedEvent> imageUpdated = event -> {};

    public EventHandler<ImageUpdatedEvent> getOnImageUpdated() { return this.imageUpdated; }

    public void setOnImageUpdated(EventHandler<ImageUpdatedEvent> event) { this.imageUpdated = event; }

    public void initModel(DataModel model) {
        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;

        //Setting filter pipeline as dataModel list
        filterSelectionList.setItems(model.getActualFiltersList());

        //Setting personalized ListViewCell
        filterSelectionList.setCellFactory(taskListView -> new FilterCell(model));

        // TODO: 24/10/22 Change listener so that an event is generated every time an item is clicked
        // Add ChangeListener to filtersSelectionView
        filterSelectionList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, filter) -> {
            ImageWrapper image = model.getImageLoaded();

            switch (filter.getName()) {
                case "Flip" -> model.addFilterPipeline(new FlipFilter());
                case "Grayscale" -> model.addFilterPipeline(new GrayscaleFilter());
                case "Sepia" -> model.addFilterPipeline(new SepiaFilter());
            }

            ImageWrapper i;
            try {
                i = model.runPipeline(image).getResult();
            } catch (PipelineException e) {
                throw new RuntimeException(e);
            }
            System.out.println(filterSelectionList.getParent());
            getOnImageUpdated().handle(new ImageUpdatedEvent(i, imagePane));
        });
    }
}
