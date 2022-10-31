package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.filter.SharpenFilter;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.filter.GrayscaleFilter;
import ch.supsi.editor2d.backend.model.filter.SepiaFilter;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collection;

public class Start extends Application {

    private static final Collection<String> SUPPORTED_FORMATS = Arrays.asList("ppm", "pgm", "pbm", "tga");

    @Override
    public void start(Stage stage) throws Exception {
        DataModel model = new DataModel();

        // Main View page
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
        Parent mainView = mainViewLoader.load();
        MainViewController mainViewController = mainViewLoader.getController();
        mainViewController.init(model, SUPPORTED_FORMATS);

        // Image View page
        FXMLLoader imageViewLoader = new FXMLLoader(getClass().getResource("/view/imageView.fxml"));
        Parent imageView = imageViewLoader.load();
        imageViewLoader.<ImageViewController>getController().initModel(model);

        // About view page
        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("/view/aboutView.fxml"));
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About Editor2D");
        aboutStage.setScene(new Scene(aboutLoader.load()));

        // Export stage page
        FXMLLoader exportLoader = new FXMLLoader(getClass().getResource("/view/exportView.fxml"));
        Stage exportStage = new Stage();
        exportStage.setTitle("Export Image");
        exportStage.setScene(new Scene(exportLoader.load()));
        ExportViewController exportViewController = exportLoader.getController();
        exportViewController.init(SUPPORTED_FORMATS);
        exportViewController.setOnExport(event -> {
            // Close the export stage
            exportStage.close();
            // Start exporting image
            model.exportImage(event.getExport());
        });

        // Set imageView.fxml inside mainView.fxml
        mainViewController.getImagePane().getChildren().add(imageView);

        // File visualization handling
        mainViewController.setOnFileOpen(e -> {
            // Load image + refresh view
            model.loadImage(e.getFile().getAbsolutePath());
            imageViewLoader.<ImageViewController>getController().refresh();
        });

        // About view handling
        mainViewController.setOnAboutClicked(e -> {
            // Show about stage
            aboutStage.show();
        });

        // Export handling
        mainViewController.setOnExportClicked(e -> {
            // Check if image is load
            if (model.getImageData() != null) {
                // Show export stage
                exportStage.show();
            } else {
                ErrorAlert.showError("Please load an image before exporting it.");
            }
        });

        // Filter selection View page
        FXMLLoader filterSelectionViewLoader = new FXMLLoader(getClass().getResource("/view/filtersListView.fxml"));
        Parent filterSelectionView = filterSelectionViewLoader.load();
        FiltersSelectionViewController filtersSelectionViewController = filterSelectionViewLoader.getController();
        filtersSelectionViewController.initModel(model);

        // Set FilterSelectionView inside mainView
        AnchorPane filtersSelectionPane = mainViewController.getFiltersListPane();
        filtersSelectionPane.getChildren().setAll(filterSelectionView);
        AnchorPane.setBottomAnchor(filterSelectionView, 0.0);
        AnchorPane.setTopAnchor(filterSelectionView,0.0);
        AnchorPane.setLeftAnchor(filterSelectionView,0.0);
        AnchorPane.setRightAnchor(filterSelectionView,0.0);

        // Image updated handling
        filtersSelectionViewController.setOnImageUpdated(e -> {
            model.setImageComponent(e.getImage());
            imageViewLoader.<ImageViewController>getController().refresh();
        });

        //Pipeline View page
        FXMLLoader pipelineViewLoader = new FXMLLoader(getClass().getResource("/view/pipelineView.fxml"));
        Parent pipelineView = pipelineViewLoader.load();
        PipelineViewController pipelineViewController = pipelineViewLoader.getController();
        pipelineViewController.initModel(model);
        pipelineViewController.setOnFilterRemovedSuccessfully(e -> {
            // Refresh image view
            imageViewLoader.<ImageViewController>getController().refresh();
        });

        // Set PipelineView inside mainView
        AnchorPane pipelinePane = mainViewController.getPipelinePane();
        pipelinePane.getChildren().setAll(pipelineView);
        AnchorPane.setBottomAnchor(pipelineView,0.0);
        AnchorPane.setTopAnchor(pipelineView,0.0);
        AnchorPane.setLeftAnchor(pipelineView,0.0);
        AnchorPane.setRightAnchor(pipelineView,0.0);

        // Set main window title + show page
        stage.setTitle("Editor2D");
        stage.setScene(new Scene(mainView));
        stage.show();

        model.addFilterSelection(new FlipFilter());
        model.addFilterSelection(new SepiaFilter());
        model.addFilterSelection(new GrayscaleFilter());
        model.addFilterSelection(new SharpenFilter());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
