package ch.supsi.editor2d.frontend.gui.model;


import ch.supsi.editor2d.backend.controller.ImageController;
import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.helper.FilterPipeline;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.frontend.gui.event.*;
import ch.supsi.editor2d.frontend.gui.event.util.FileExport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Data model that holds the application data and login throughout the application.
 */
public class DataModel extends Observable implements RunPipelineHandler, AboutHandler,
        ZoomInHandler, ZoomOutHandler, UndoRedoHandler, AddFilterHandler {

    // Value used for zoom in/out functions
    private static final double ZOOM_FACTOR = 1.1;

    /**
     * Interface imageController
     * the right controller (ImagePBMController, ImagePPMController, ...) will be assigned on run time
     */
    private final ImageController imageController;

    /**
     * Image showed
     */
    private final ImageView imageComponent;

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
    private final FilterPipeline filterPipeline;

    /**
     * Observable list of the filter pipeline's tasks, used to update the pipeline view
     */
    private final ObservableList<Task<ImageWrapper, FilterTaskResult>> actualFiltersPipeline;


    /**
     * Undo/redo fields
     */
    private int savedStatesCount;
    private int undoRedoPointer;

    /**
     * Constructor that initializes the data model.
     */
    public DataModel() {
        this.imageComponent = new ImageView();
        this.imageController = new ImageController();
        this.filterPipeline = new FilterPipeline();
        this.actualFiltersPipeline = FXCollections.observableArrayList();
    }

    public int getSavedStatesCount() {
        return savedStatesCount;
    }

    public int getUndoRedoPointer() {
        return undoRedoPointer;
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

            // Draw image (passing copy)
            drawImage(new ImageWrapper(img));
            getPropertyChangeSupport().firePropertyChange(new ImageLoadedEvent(this));
        } catch (FileReadingException e) {
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }

    /**
     * Export the current image to a file with a specific format and path
     * @param exportReq export request object, containing all the needed information
     */
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
     * Draw the image on the image component
     * @param imageWrapper image to draw
     */
    private void drawImage(ImageWrapper imageWrapper) {
        // Save current image
        imageData = imageWrapper;

        // Create a new WritableImage
        WritableImage writableImage = new WritableImage(imageWrapper.getWidth(), imageWrapper.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        // Draw the image
        for (int h = 0; h < imageWrapper.getHeight(); h++) {
            for (int w = 0; w < imageWrapper.getWidth(); w++) {
                ColorWrapper tempColor = imageWrapper.getData()[h][w];
                pixelWriter.setColor(w, h, Color.color(tempColor.getRed(),tempColor.getGreen(),tempColor.getBlue()));
            }
        }

        // Display image on ImageView component
        imageComponent.setImage(writableImage);
    }

    @Override
    public void addFilter(Filter filter) {
        if(imageInitialData == null)

        savedStatesCount ++;
        undoRedoPointer ++;
        getPropertyChangeSupport().firePropertyChange(new AddedFilterEvent(this));

        filterPipeline.add(new FilterTask(filter));
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getTasks());
    }

    /**
     * Run the whole pipeline.
     * This method is called when the user press the button for running the pipeline
     */
    @Override
    public void runPipeline()  {
        try {
            imageData = filterPipeline.run(imageInitialData).getResult();
            drawImage(imageData);

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
        // TODO: 26/11/2022 Use memento pattern here

        undoRedoPointer --;
        getPropertyChangeSupport().firePropertyChange(new UndoneEvent(this));
    }

    @Override
    public void redo() {
        // TODO: 26/11/2022 Use memento pattern here

        undoRedoPointer ++;
        getPropertyChangeSupport().firePropertyChange(new RedoneEvent(this));
    }

    /**
     * Re-run the pipeline and update the current image on ImageView
     * @throws PipelineException if the pipeline is empty
     */
    public void refreshPipeline() throws PipelineException {
        drawImage(filterPipeline.run(imageInitialData).getResult());
    }

    /**
     * Clear the pipeline, called when uploading a new image
     */
    public void clearPipeline() {
        filterPipeline.getTasks().forEach(actualFiltersPipeline::remove);
        filterPipeline.clear();
    }

/*
    /**
     * Send filter to backend and update actualFilterPipeline ListView on frontend
     * @param filter filter to be added

    public void addFilterPipeline(Filter filter) throws ImageNotLoadedException {
        if(imageInitialData == null)
            throw new ImageNotLoadedException();

        savedStatesCount ++;
        undoRedoPointer ++;
        getPropertyChangeSupport().firePropertyChange(new AddedFilterEvent(this));

        filterPipeline.add(new FilterTask(filter));
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getTasks());
    }
*/
    /**
     * Remove a specific FilterTask from the pipeline and update actualFilterPipeline ListView on frontend
     * @param task FilterTask to be removed
     * @throws PipelineException if the task is not in the pipeline
     */
    public void removeTaskFromPipeline(Task<ImageWrapper, FilterTaskResult> task) throws PipelineException {
        filterPipeline.remove(task);
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getTasks());

        // Re-run pipeline + update current image on ImageView
        refreshPipeline();
    }

    /**
     * Method used for swipe up a filter in the pipeline list
     * @param task to swipe up
     * @throws PipelineException in case of execution errors or index errors
     */
    public void swipeUpFilterPipeline(Task<ImageWrapper, FilterTaskResult> task) throws PipelineException {
        filterPipeline.invertBeforePositionTask(task);
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getTasks());

        //refresh pipeline
        refreshPipeline();
    }

    /**
     * Method used for swipe down a filter in the pipeline list
     * @param task to swipe down
     * @throws PipelineException in case of execution errors or index errors
     */
    public void swipeDownFilterPipeline(Task<ImageWrapper, FilterTaskResult> task) throws PipelineException {
        filterPipeline.invertAfterPositionTask(task);
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getTasks());

        //refresh pipeline
        refreshPipeline();
    }

    /**
     * Get the image component used to show the image
     * @return image component
     */
    public ImageView getImageComponent() {
        return imageComponent;
    }

    /**
     * Get the image data
     * @return ImageWrapper object
     */
    public ImageWrapper getImageData() {
        return imageData;
    }

    /**
     * Get actual filter pipeline
     * @return actual filter pipeline
     */
    public ObservableList<Task<ImageWrapper, FilterTaskResult>> getActualFiltersPipeline(){
        return actualFiltersPipeline;
    }
}
