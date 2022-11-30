package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.*;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.frontend.gui.command.*;
import ch.supsi.editor2d.frontend.gui.receiver.mediator.*;
import ch.supsi.editor2d.frontend.gui.model.*;
import ch.supsi.editor2d.frontend.gui.receiver.ExitDialogReceiver;
import ch.supsi.editor2d.frontend.gui.receiver.UndoRedoReceiver;
import ch.supsi.editor2d.frontend.gui.receiver.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Arrays;
import java.util.Collection;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class Start extends Application {

    private static final Collection<String> SUPPORTED_FORMATS = Arrays.asList("ppm", "pgm", "pbm", "tga");

    private static final Collection<Filter> FILTERS = Arrays.asList(new FlipFilter(), new GrayscaleFilter(),
            new SepiaFilter(), new SharpenFilter());

    @Override
    public void start(Stage stage) throws Exception {

        /*
        ==================================
        =========  MODEL SETUP  ==========
        ==================================
         */
        DataModel model = new DataModel();

        /*
        ==================================
        =========  VIEW SETUP  ===========
        ==================================
         */
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

        // Filter selection View page
        FXMLLoader filterSelectionViewLoader = new FXMLLoader(getClass().getResource("/view/filtersListView.fxml"));
        Parent filterSelectionView = filterSelectionViewLoader.load();
        FiltersSelectionViewController filtersSelectionViewController = filterSelectionViewLoader.getController();

        //Pipeline View page
        FXMLLoader pipelineViewLoader = new FXMLLoader(getClass().getResource("/view/pipelineView.fxml"));
        Parent pipelineView = pipelineViewLoader.load();
        PipelineViewController pipelineViewController = pipelineViewLoader.getController();
        pipelineViewController.initModel(model);
        Button removeFilterButton = pipelineViewController.getRemoveFilter();

        // Set imageView.fxml inside mainView.fxml
        AnchorPane imagePane = mainViewController.getImagePane();
        imagePane.getChildren().add(imageView);
        AnchorPane.setBottomAnchor(imageView, 0.0);
        AnchorPane.setTopAnchor(imageView, 0.0);
        AnchorPane.setLeftAnchor(imageView, 0.0);
        AnchorPane.setRightAnchor(imageView, 0.0);

        // Set FilterSelectionView inside mainView
        AnchorPane filtersSelectionPane = mainViewController.getFiltersListPane();
        filtersSelectionPane.getChildren().setAll(filterSelectionView);
        AnchorPane.setBottomAnchor(filterSelectionView, 0.0);
        AnchorPane.setTopAnchor(filterSelectionView, 0.0);
        AnchorPane.setLeftAnchor(filterSelectionView, 0.0);
        AnchorPane.setRightAnchor(filterSelectionView, 0.0);


        // Set PipelineView inside mainView
        AnchorPane pipelinePane = mainViewController.getPipelinePane();
        pipelinePane.getChildren().setAll(pipelineView);
        AnchorPane.setBottomAnchor(pipelineView, 0.0);
        AnchorPane.setTopAnchor(pipelineView, 0.0);
        AnchorPane.setLeftAnchor(pipelineView, 0.0);
        AnchorPane.setRightAnchor(pipelineView, 0.0);


        /*
        ==================================
        ===========  COMPONENTS  =========
        ==================================
         */
        MenuItem undoMenuItem = mainViewController.getUndoMenuItem();
        MenuItem redoMenuItem = mainViewController.getRedoMenuItem();
        ListView<Filter> selectableFilters = filtersSelectionViewController.getFilterSelectionList();
        MenuItem runPipelineMenuItem = mainViewController.getRunPipelineMenuItem();
        Button runPipelineButton = pipelineViewController.getRunPipeline();
        Button zoomInButton = mainViewController.zoomInButton;
        Button zoomOutButton = mainViewController.zoomOutButton;


        /*
        ==================================
        =========  RECEIVER SETUP  =======
        ==================================
         */
        RunPipelineReceiver runPipelineReceiver = RunPipelineReceiver.create(model);
        AboutReceiver aboutReceiver = AboutReceiver.create(model);
        ZoomInReceiver zoomInReceiver = ZoomInReceiver.create(model);
        ZoomOutReceiver zoomOutReceiver = ZoomOutReceiver.create(model);
        ExitDialogReceiver<Observable> exitDialogReceiver = ExitDialogReceiver.create(model, exitStage, stage);
        UndoRedoReceiver<DataModel> undoRedoReceiver = UndoRedoReceiver.create(model);
        AddFilterReceiver<DataModel> addFilterReceiver = AddFilterReceiver.create(model);
        RemoveFilterReceiver<DataModel> removeFilterReceiver = RemoveFilterReceiver.create(model);

        /*
        ==================================
        =========  COMMAND SETUP  ========
        ==================================
         */
        ExitCommand<ExitHandler> exitCommand = ExitCommand.create(exitDialogReceiver);
        CancelCommand<CancelHandler> cancelCommand = CancelCommand.create(exitDialogReceiver);
        OkCommand<OkHandler> okCommand = OkCommand.create(exitDialogReceiver);

        UndoCommand<UndoRedoHandler> undoCommand = UndoCommand.create(undoRedoReceiver);
        RedoCommand<UndoRedoHandler> redoCommand = RedoCommand.create(undoRedoReceiver);

        AddFilterCommand<AddFilterHandler> addFilterCommand = AddFilterCommand.create(addFilterReceiver);
        RemoveFilterCommand<RemoveFilterHandler> removeFilterCommand = RemoveFilterCommand.create(removeFilterReceiver);

        RunPipelineCommand runPipelineCommand = RunPipelineCommand.create(runPipelineReceiver);
        AboutCommand aboutCommand = AboutCommand.create(aboutReceiver);
        ZoomInCommand zoomInCommand = ZoomInCommand.create(zoomInReceiver);
        ZoomOutCommand zoomOutCommand = ZoomOutCommand.create(zoomOutReceiver);


        // Set commands
        exitDialogReceiver.setCancelCommand(cancelCommand);
        exitDialogReceiver.setOkCommand(okCommand);
        undoMenuItem.setOnAction(actionEvent -> undoCommand.execute());
        redoMenuItem.setOnAction(actionEvent -> redoCommand.execute());
        removeFilterButton.setOnAction(actionEvent -> {
            Task<ImageWrapper, FilterTaskResult> selectedTask = pipelineViewController.getSelectedTask();
            removeFilterCommand.execute(selectedTask);
        });

        // Selectable filters
        ObservableList<Filter> filters = FXCollections.observableArrayList(FILTERS);
        selectableFilters.setItems(filters);
        selectableFilters.setCellFactory(taskListView -> new FilterCell(model));
        selectableFilters.setOnMouseClicked(mouseEvent -> {
            Filter filter = selectableFilters.getSelectionModel().getSelectedItem();
            addFilterCommand.execute(filter);
        });


        mainViewController.runPipelineMenuItem.setOnAction(actionEvent -> runPipelineCommand.execute());
        mainViewController.exitMenuItem.setOnAction(actionEvent -> exitCommand.execute());
        mainViewController.aboutMenuItem.setOnAction(actionEvent -> aboutCommand.execute(aboutStage));
        mainViewController.zoomInButton.setOnAction(actionEvent ->
                zoomInCommand.execute(imageViewLoader.<ImageViewController>getController().getImageView()));
        mainViewController.zoomOutButton.setOnAction(actionEvent ->
                zoomOutCommand.execute(imageViewLoader.<ImageViewController>getController().getImageView()));

        mainViewController.init(model, SUPPORTED_FORMATS);

        pipelineViewController.getRunPipeline().setOnAction(actionEvent -> runPipelineCommand.execute());
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

         /*
        ==================================
        =========  SHORTCUTS SETUP  ======
        ==================================
         */

        KeyCombination undoKeyCombination = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        KeyCombination redoKeyCombination = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
        KeyCombination zoomInKeyCombination = new KeyCodeCombination(KeyCode.ADD, KeyCombination.CONTROL_DOWN);
        KeyCombination zoomOutKeyCombination = new KeyCodeCombination(KeyCode.SUBTRACT, KeyCombination.CONTROL_DOWN);

        /*
        ==================================
        =========  MEDIATOR SETUP  =======
        ==================================
         */
        ToolbarMediator<DataModel> toolbarMediator = ToolbarMediator.create(model, undoMenuItem, redoMenuItem);
        SelectableFiltersMediator<DataModel> selectableFiltersMediator = SelectableFiltersMediator.create(model, selectableFilters);
        RunPipelineMediator<DataModel> runPipelineMediator = RunPipelineMediator.create(model, runPipelineMenuItem, runPipelineButton);
        ZoomMediator<DataModel> zoomMediator = ZoomMediator.create(model, zoomInButton, zoomOutButton);
        ZoomShortcutMediator<DataModel> zoomShortcutMediator = ZoomShortcutMediator.create(model, mainView, zoomInKeyCombination, zoomOutKeyCombination, zoomInCommand, zoomOutCommand, imageViewLoader.getController());

        /*
        ==================================
        =======  OBSERVER SETUP  =========
        ==================================
         */

        model.addPropertyChangeListener(imageViewLoader.getController());
        model.addPropertyChangeListener(mainViewLoader.getController());
        model.addPropertyChangeListener(pipelineViewLoader.getController());

        //mediator listener
        model.addPropertyChangeListener(toolbarMediator);
        model.addPropertyChangeListener(selectableFiltersMediator);
        model.addPropertyChangeListener(runPipelineMediator);
        model.addPropertyChangeListener(zoomMediator);
        model.addPropertyChangeListener(zoomShortcutMediator);

        /*
        ==================================
        =========  OTHER SETUP  ==========
        ==================================
         */


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


        // Set main window title + show page
        stage.setTitle("Editor2D");
        stage.setScene(new Scene(mainView));
        stage.show();


        // Override close request
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            exitCommand.execute();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
