package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.filter.SharpenFilter;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.filter.GrayscaleFilter;
import ch.supsi.editor2d.backend.model.filter.SepiaFilter;
import ch.supsi.editor2d.frontend.gui.command.*;
import ch.supsi.editor2d.frontend.gui.model.*;
import ch.supsi.editor2d.frontend.gui.mycontroller.ExitDialogReceiver;
import ch.supsi.editor2d.frontend.gui.mycontroller.ToolbarMediator;
import ch.supsi.editor2d.frontend.gui.mycontroller.UndoRedoReceiver;
import ch.supsi.editor2d.frontend.gui.receiver.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collection;

public class Start extends Application {

    private static final Collection<String> SUPPORTED_FORMATS = Arrays.asList("ppm", "pgm", "pbm", "tga");

    @Override
    public void start(Stage stage) throws Exception {

        /** MODEL */
        DataModel model = new DataModel();

        /** VIEWS */

        // Image View page
        FXMLLoader imageViewLoader = new FXMLLoader(getClass().getResource("/view/imageView.fxml"));
        Parent imageView = imageViewLoader.load();
        imageViewLoader.<ImageViewController>getController().initModel(model);

        // About view page
        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("/view/aboutView.fxml"));
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About Editor2D");
        aboutStage.setScene(new Scene(aboutLoader.load()));

        // Exit view page
        FXMLLoader exitLoader = new FXMLLoader(getClass().getResource("/view/exitView.fxml"));
        Stage exitStage = new Stage();
        exitStage.setTitle("Exit");
        exitStage.setScene(new Scene(exitLoader.load()));

        // Main View page
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
        Parent mainView = mainViewLoader.load();
        MainViewController mainViewController = mainViewLoader.getController();


        /**
         * MEDIATOR
         */
        MenuItem undoMenuItem = mainViewController.getUndoMenuItem();
        MenuItem redoMenuItem = mainViewController.getRedoMenuItem();
        ToolbarMediator<DataModel> toolbarMediator = ToolbarMediator.create(model, undoMenuItem, redoMenuItem);
        model.addPropertyChangeListener(toolbarMediator);


        /** RECEIVERS */

        RunPipelineReceiver runPipelineReceiver = RunPipelineReceiver.create(model);
        AboutReceiver aboutReceiver = AboutReceiver.create(model);
        ZoomInReceiver zoomInReceiver = ZoomInReceiver.create(model);
        ZoomOutReceiver zoomOutReceiver = ZoomOutReceiver.create(model);
        ExitDialogReceiver<Observable> exitDialogReceiver = ExitDialogReceiver.create(model, exitStage, stage);
        UndoRedoReceiver<DataModel> undoRedoReceiver = UndoRedoReceiver.create(model);


        /** COMMANDS */

        ExitCommand<ExitHandler> exitCommand = ExitCommand.create(exitDialogReceiver);
        CancelCommand<CancelHandler> cancelCommand = CancelCommand.create(exitDialogReceiver);
        OkCommand<OkHandler> okCommand = OkCommand.create(exitDialogReceiver);

        UndoCommand<UndoRedoHandler> undoCommand = UndoCommand.create(undoRedoReceiver);
        RedoCommand<UndoRedoHandler> redoCommand = RedoCommand.create(undoRedoReceiver);

        RunPipelineCommand runPipelineCommand = RunPipelineCommand.create(runPipelineReceiver);
        AboutCommand aboutCommand = AboutCommand.create(aboutReceiver);
        ZoomInCommand zoomInCommand = ZoomInCommand.create(zoomInReceiver);
        ZoomOutCommand zoomOutCommand = ZoomOutCommand.create(zoomOutReceiver);


        // TODO: 26/11/2022 Remove this later, only for test purposes!
        model.setChanged(true);

        // Set commands
        exitDialogReceiver.setCancelCommand(cancelCommand);
        exitDialogReceiver.setOkCommand(okCommand);
        undoMenuItem.setOnAction(actionEvent -> undoCommand.execute());
        redoMenuItem.setOnAction(actionEvent -> redoCommand.execute());

        mainViewController.runPipelineMenuItem.setOnAction(actionEvent -> runPipelineCommand.execute());
        mainViewController.exitMenuItem.setOnAction(actionEvent -> exitCommand.execute());
        mainViewController.aboutMenuItem.setOnAction(actionEvent -> aboutCommand.execute(aboutStage));
        mainViewController.zoomInButton.setOnAction(actionEvent ->
                zoomInCommand.execute(imageViewLoader.<ImageViewController>getController().getImageView()));
        mainViewController.zoomOutButton.setOnAction(actionEvent ->
                zoomOutCommand.execute(imageViewLoader.<ImageViewController>getController().getImageView()));

        mainViewController.init(model, SUPPORTED_FORMATS);

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
        AnchorPane imagePane = mainViewController.getImagePane();
        imagePane.getChildren().add(imageView);
        AnchorPane.setBottomAnchor(imageView, 0.0);
        AnchorPane.setTopAnchor(imageView, 0.0);
        AnchorPane.setLeftAnchor(imageView, 0.0);
        AnchorPane.setRightAnchor(imageView, 0.0);

        // File visualization handling
        mainViewController.setOnFileOpen(e -> {
            // Load image + refresh view
            model.loadImage(e.getFile().getAbsolutePath());
            model.clearPipeline();
            imageViewLoader.<ImageViewController>getController().refresh();
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
        AnchorPane.setTopAnchor(filterSelectionView, 0.0);
        AnchorPane.setLeftAnchor(filterSelectionView, 0.0);
        AnchorPane.setRightAnchor(filterSelectionView, 0.0);

        /*
        // Image updated handling
        filtersSelectionViewController.setOnImageUpdated(e -> {
            model.setImageComponent(e.getImage());
            imageViewLoader.<ImageViewController>getController().refresh();
        });*/

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
        AnchorPane.setBottomAnchor(pipelineView, 0.0);
        AnchorPane.setTopAnchor(pipelineView, 0.0);
        AnchorPane.setLeftAnchor(pipelineView, 0.0);
        AnchorPane.setRightAnchor(pipelineView, 0.0);

        // Set main window title + show page
        stage.setTitle("Editor2D");
        stage.setScene(new Scene(mainView));
        stage.show();

        model.addFilterSelection(new FlipFilter());
        model.addFilterSelection(new SepiaFilter());
        model.addFilterSelection(new GrayscaleFilter());
        model.addFilterSelection(new SharpenFilter());

        // observers
        model.addPropertyChangeListener(imageViewLoader.getController());
        model.addPropertyChangeListener(mainViewLoader.getController());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
