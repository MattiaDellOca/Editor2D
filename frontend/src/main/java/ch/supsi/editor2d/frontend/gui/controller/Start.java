package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.*;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.command.*;
import ch.supsi.editor2d.frontend.gui.handler.*;
import ch.supsi.editor2d.frontend.gui.receiver.mediator.*;
import ch.supsi.editor2d.frontend.gui.model.*;
import ch.supsi.editor2d.frontend.gui.receiver.ExitDialogReceiver;
import ch.supsi.editor2d.frontend.gui.receiver.UndoRedoReceiver;
import ch.supsi.editor2d.frontend.gui.receiver.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collection;


public class Start extends Application {

    public static final Collection<String> SUPPORTED_FORMATS = Arrays.asList("ppm", "pgm", "pbm");

    private static final Collection<Filter> FILTERS = Arrays.asList(new FlipFilter(), new GrayscaleFilter(),
            new SepiaFilter(), new SharpenFilter());

    @Override
    public void start(Stage stage) throws Exception {

        /*
        ==================================
        =========  MODEL SETUP  ==========
        ==================================
         */
        DataModel model = DataModel.getInstance();

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


        // Export stage page
        FXMLLoader exportLoader = new FXMLLoader(getClass().getResource("/view/exportView.fxml"));
        Stage exportStage = new Stage();
        exportStage.setTitle("Export Image");
        exportStage.setScene(new Scene(exportLoader.load()));
        ExportViewController exportViewController = exportLoader.getController();

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
        Button zoomInButton = mainViewController.getZoomInButton();
        Button zoomOutButton = mainViewController.getZoomOutButton();
        MenuItem openMenuItem = mainViewController.getOpenMenuItem();
        MenuItem exportMenuItem = mainViewController.getExportMenuItem();
        Button exportButton = exportViewController.getExportButton();
        Button removeFilterButton = pipelineViewController.getRemoveFilter();


        /*
        ==================================
        =========  RECEIVER SETUP  =======
        ==================================
         */
        RunPipelineReceiver<DataModel> runPipelineReceiver = RunPipelineReceiver.create(model);
        AboutReceiver<DataModel> aboutReceiver = AboutReceiver.create(model);
        ZoomInReceiver<DataModel> zoomInReceiver = ZoomInReceiver.create(model);
        ZoomOutReceiver<DataModel> zoomOutReceiver = ZoomOutReceiver.create(model);
        ExitDialogReceiver<DataModel> exitDialogReceiver = ExitDialogReceiver.create(model, exitStage, stage);
        UndoRedoReceiver<DataModel> undoRedoReceiver = UndoRedoReceiver.create(model);
        AddFilterReceiver<DataModel> addFilterReceiver = AddFilterReceiver.create(model);
        RemoveFilterReceiver<DataModel> removeFilterReceiver = RemoveFilterReceiver.create(model);
        OpenFileReceiver<DataModel> openFileReceiver = OpenFileReceiver.create(model);
        ExportReceiver<DataModel> exportReceiver = ExportReceiver.create(model);

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

        RunPipelineCommand<RunPipelineHandler> runPipelineCommand = RunPipelineCommand.create(runPipelineReceiver);
        AboutCommand<AboutHandler> aboutCommand = AboutCommand.create(aboutReceiver);
        ZoomInCommand<ZoomInHandler> zoomInCommand = ZoomInCommand.create(zoomInReceiver);
        ZoomOutCommand<ZoomOutHandler> zoomOutCommand = ZoomOutCommand.create(zoomOutReceiver);

        OpenFileCommand<OpenFileHandler> openFileCommand = OpenFileCommand.create(openFileReceiver);
        ExportPageCommand<ExportHandler> exportPageCommand = ExportPageCommand.create(exportReceiver);
        ExportImageCommand<ExportHandler> exportImageCommand = ExportImageCommand.create(exportReceiver);

        // Set commands
        exitDialogReceiver.setCancelCommand(cancelCommand);
        exitDialogReceiver.setOkCommand(okCommand);
        undoMenuItem.setOnAction(actionEvent -> undoCommand.execute());
        redoMenuItem.setOnAction(actionEvent -> redoCommand.execute());
        removeFilterButton.setOnAction(actionEvent -> {
            Task<ImageWrapper, FilterTaskResult> selectedTask = pipelineViewController.getSelectedTask();
            removeFilterCommand.execute(selectedTask);
        });
        openMenuItem.setOnAction(actionEvent -> openFileCommand.execute());
        exportMenuItem.setOnAction(actionEvent -> exportPageCommand.execute(exportStage));
        exportButton.setOnAction(actionEvent -> exportImageCommand.execute(exportViewController.exportImage()));

        // Selectable filters
        ObservableList<Filter> filters = FXCollections.observableArrayList(FILTERS);
        selectableFilters.setItems(filters);
        filtersSelectionViewController.getFilterSelectionList().setCellFactory(param -> new FilterCell());
        selectableFilters.setOnMouseClicked(mouseEvent -> {
            Filter filter = selectableFilters.getSelectionModel().getSelectedItem();
            addFilterCommand.execute(filter);
            selectableFilters.getSelectionModel().clearSelection();
        });


        mainViewController.getRunPipelineMenuItem().setOnAction(actionEvent -> runPipelineCommand.execute());
        mainViewController.getExitMenuItem().setOnAction(actionEvent -> exitCommand.execute());
        mainViewController.getAboutMenuItem().setOnAction(actionEvent -> aboutCommand.execute(aboutStage));
        mainViewController.getZoomInButton().setOnAction(actionEvent ->
                zoomInCommand.execute(imageViewLoader.<ImageViewController>getController().getImageView()));
        mainViewController.getZoomOutButton().setOnAction(actionEvent ->
                zoomOutCommand.execute(imageViewLoader.<ImageViewController>getController().getImageView()));

        pipelineViewController.getRunPipeline().setOnAction(actionEvent -> runPipelineCommand.execute());

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
        ToolbarMediator<DataModel> toolbarMediator = ToolbarMediator.create(model, undoMenuItem, redoMenuItem, exportMenuItem);
        MementoShortcutMediator<DataModel> mementoShortcutMediator = MementoShortcutMediator.create(model, mainView, undoKeyCombination, redoKeyCombination, undoCommand, redoCommand);
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
        model.addPropertyChangeListener(pipelineViewLoader.getController());

        //mediator listener
        model.addPropertyChangeListener(toolbarMediator);
        model.addPropertyChangeListener(mementoShortcutMediator);
        model.addPropertyChangeListener(selectableFiltersMediator);
        model.addPropertyChangeListener(runPipelineMediator);
        model.addPropertyChangeListener(zoomMediator);
        model.addPropertyChangeListener(zoomShortcutMediator);

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
