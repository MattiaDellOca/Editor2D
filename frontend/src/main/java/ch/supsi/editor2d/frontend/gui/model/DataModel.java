package ch.supsi.editor2d.frontend.gui.model;


import ch.supsi.editor2d.backend.controller.ImageController;
import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.helper.FilterPipeline;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.frontend.gui.event.*;
import ch.supsi.editor2d.frontend.gui.handler.*;
import ch.supsi.editor2d.frontend.gui.memento.Memento;
import ch.supsi.editor2d.frontend.gui.memento.MementoCaretaker;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static ch.supsi.editor2d.frontend.gui.controller.Start.SUPPORTED_FORMATS;

/**
 * Data model that holds the application data and login throughout the application.
 */
public class DataModel extends Observable implements RunPipelineHandler, AboutHandler,
        ZoomInHandler, ZoomOutHandler, UndoRedoHandler, AddFilterHandler, RemoveFilterHandler, OpenFileHandler, ExportHandler {

    // Value used for zoom in/out functions
    private static final double ZOOM_FACTOR = 1.1;

    /**
     * Interface imageController
     * the right controller (ImagePBMController, ImagePPMController, ...) will be assigned on run time
     */
    private final ImageController imageController;


    /**
     * Image wrapper containing the image data
     */
    private ImageWrapper imageData;

    /**
     * Image wrapper containing the initial image data (before any filter is applied)
     */
    private ImageWrapper imageInitialData;

    /**
     * Filter pipeline containing the currently selected filter list
     */
    private FilterPipeline filterPipeline;

    /**
     * Memento caretaker used for undo/redo operations
     */
    private final MementoCaretaker<FilterPipeline> filterPipelineCaretaker;

    //Singleton
    private static DataModel instance;

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    /**
     * Constructor that initializes the data model.
     */
    private DataModel() {
        this.imageController = new ImageController();
        this.filterPipeline = new FilterPipeline();
        this.filterPipelineCaretaker = new MementoCaretaker<>();
        // first memento state is the empty filter pipeline
        filterPipelineCaretaker.addMemento(new Memento<>(cloneFilterPipeline(filterPipeline)));
    }

    public void reset() {
        // Replace current instance with a new one
        instance = new DataModel();
    }

    /**
     * Load an image from a given path
     * @param path the path of the image
     */
    public void loadImage(String path) {
        try {
            // Load the image
            ImageWrapper img = imageController.getImage(path);

            // Save the initial image data
            imageInitialData = img;

            // Set the image data
            imageData = img;

            // Throw event
            getPropertyChangeSupport().firePropertyChange(new ImageLoadedEvent(this));
        } catch (FileReadingException e) {
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }

    public boolean canUndo() {
        return filterPipelineCaretaker.canUndo();
    }

    public boolean canRedo() {
        return filterPipelineCaretaker.canRedo();
    }

    private void savePipelineState(){
        // Save the state
        Memento<FilterPipeline> memento = new Memento<>(cloneFilterPipeline(filterPipeline));
        filterPipelineCaretaker.addMemento(memento);
    }

    /**
     * Clone the FilterPipeline
     * @param filterPipeline pipeline to clone
     */
    private FilterPipeline cloneFilterPipeline(FilterPipeline filterPipeline) {
        FilterPipeline clone = new FilterPipeline();
        for (Task<ImageWrapper, FilterTaskResult> task : filterPipeline.getTasks()) {
            clone.add(task);
        }
        return clone;
    }

    @Override
    public void addFilter(Filter filter) {
        // Create a new task
        filterPipeline.add(new FilterTask(filter));

        // Save the state
        savePipelineState();

        getPropertyChangeSupport().firePropertyChange(new AddedFilterEvent(this));
    }

    /**
     * Remove a filter from the filter pipeline
     * @param filter to be removed
     */
    @Override
    public void removeFilter(Task<ImageWrapper, FilterTaskResult> filter) {
        // Remove the filter
        filterPipeline.remove(filter);

        // Save the state
        savePipelineState();

        getPropertyChangeSupport().firePropertyChange(new RemovedFilterEvent(this));
    }

    /**
     * Run the whole pipeline.
     * This method is called when the user press the button for running the pipeline
     */
    @Override
    public void runPipeline()  {
        try {
            imageData = filterPipeline.run(imageInitialData).getResult();
            //drawImage(imageData);

            this.setChanged(true);

            // notify that the image has been updated
            getPropertyChangeSupport().firePropertyChange(new ImageUpdatedEvent(this));
        } catch( PipelineException e) {
            System.err.println("Unable to run pipeline: " + e.getMessage());
            ErrorAlert.showError("Unable to run pipeline: " + e.getMessage());
        }
    }


    /**
     * Show the "about stage"
     * @param aboutStage stage to be shown
     */
    @Override
    public void about(Stage aboutStage) {
        aboutStage.show();
    }

    /**
     * Show the "export stage"
     * @param exportStage stage to be shown
     */
    @Override
    public void exportPage(Stage exportStage) {
        exportStage.show();
    }

    /**
     * Export the current image to a file with a specific format and path
     * @param exportReq export request object, containing all the needed information
     */
    @Override
    public void exportImage (FileExport exportReq) {
        try {
            // Try to export image into selected directory
            imageController.exportImage(exportReq.getFilename(), exportReq.getExtension(), exportReq.getDestination(), imageData);

            // the image has been successfully saved, therefore there aren't any pending changes
            setChanged(false);
        } catch (FileWritingException e) {
            //Show Alert
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }

    /**
     * Zoom in on the given image view by a defined zoom factor
     * @param imageView image to zoom
     */
    @Override
    public void zoomIn(ImageView imageView) {
        imageView.setFitWidth(imageView.getFitWidth() * ZOOM_FACTOR);
        imageView.setFitHeight(imageView.getFitHeight() * ZOOM_FACTOR);

        //notify that the image has been updated
        getPropertyChangeSupport().firePropertyChange(new ImageUpdatedEvent(this));
    }

    /**
     * Zoom out on the given image view by a defined zoom factor
     * @param imageView image to zoom
     */
    @Override
    public void zoomOut(ImageView imageView) {
        imageView.setFitWidth(imageView.getFitWidth() / ZOOM_FACTOR);
        imageView.setFitHeight(imageView.getFitHeight() / ZOOM_FACTOR);

        //notify that the image has been updated
        getPropertyChangeSupport().firePropertyChange(new ImageUpdatedEvent(this));
    }

    @Override
    public void undo() {
        // Redundant check (already checked by mediator)
        if(filterPipelineCaretaker.canUndo()){
            // Get the previous state
            filterPipeline = filterPipelineCaretaker.undo().getState();
        }

        getPropertyChangeSupport().firePropertyChange(new UndoneEvent(this));
    }

    @Override
    public void redo() {
        // Redundant check (already checked by mediator)
        if(filterPipelineCaretaker.canRedo()){
            filterPipeline = filterPipelineCaretaker.redo().getState();
        }
        getPropertyChangeSupport().firePropertyChange(new RedoneEvent(this));
    }

    @Override
    public void openFile() {
        // Load file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open picture");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Image Files",
                        SUPPORTED_FORMATS.stream().map(s -> "*." + s).toArray(String[]::new)
                )
        );

        File file = fileChooser.showOpenDialog(null);

        // additional file extension check
        if (file != null) {
            // Check if the file is supported - redundant check
            boolean isSupportedFormat = false;
            String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            for (String format : SUPPORTED_FORMATS) {
                if (extension.equals(format)) {
                    isSupportedFormat = true;
                    break;
                }
            }

            if(isSupportedFormat){
                //Clear the pipeline
                filterPipeline.clear();
                //Load image
                loadImage(file.getAbsolutePath());
            }

        }
    }

    /**
     * Method used for swipe up a filter in the pipeline list
     * @param task to swipe up
     * @throws PipelineException in case of execution errors or index errors
     */
    public void moveUpFilterPipeline(Task<ImageWrapper, FilterTaskResult> task) throws PipelineException {
        filterPipeline.invertBeforePositionTask(task);

        // Save the state
        savePipelineState();

    }

    /**
     * Method used for swipe down a filter in the pipeline list
     * @param task to swipe down
     * @throws PipelineException in case of execution errors or index errors
     */
    public void moveDownFilterPipeline(Task<ImageWrapper, FilterTaskResult> task) throws PipelineException {
        filterPipeline.invertAfterPositionTask(task);

        // Save the state
        savePipelineState();

    }

    /**
     * Get the image data
     * @return ImageWrapper object
     */
    public ImageWrapper getImageData() {
        return imageData;
    }

    public FilterPipeline getFilterPipeline() {
        return filterPipeline;
    }
}
