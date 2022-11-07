package ch.supsi.editor2d.frontend.gui.model;


import ch.supsi.editor2d.backend.controller.ImageController;
import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.helper.FilterPipeline;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.exception.ImageNotLoadedException;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.frontend.gui.event.util.FileExport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


/**
 * Data model that holds the application data and login throughout the application.
 */
public class DataModel {

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
     * List of filters
     */
    private final List<Filter> filterList = new ArrayList<>();

    /**
     * Filter pipeline containing the currently selected filter list
     */
    private final FilterPipeline filterPipeline;

    /**
     * Observable list of all the possible filters to apply
     */
    private final ObservableList<Filter> actualFiltersList;

    /**
     * Observable list of the filter pipeline's tasks, used to update the pipeline view
     */
    private final ObservableList<Task<ImageWrapper, FilterTaskResult>> actualFiltersPipeline;

    /**
     * Constructor that initializes the data model.
     */
    public DataModel() {
        this.imageComponent = new ImageView();
        this.imageController = new ImageController();
        this.filterPipeline = new FilterPipeline();
        this.actualFiltersPipeline = FXCollections.observableArrayList();
        this.actualFiltersList = FXCollections.observableArrayList();
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

    /**
     * Run the pipeline starting from the ImageWrapper obtained as the last result of the previous execution
     * This method is called every time a filter is added to the pipeline
     * @throws ImageNotLoadedException if no image has been loaded
     * @throws PipelineException if the pipeline is empty
     */
    public ImageWrapper runPipeline() throws ImageNotLoadedException, PipelineException {
        if(imageData == null)
            throw new ImageNotLoadedException();

        try {
            imageData = filterPipeline.run(imageData).getResult();
        } catch(FilterApplyException e) {
            System.err.println("Unable to run pipeline: " + e.getMessage());
            ErrorAlert.showError("Unable to run pipeline: " + e.getMessage());
        }
        return imageData;
    }

    /**
     * Re-run the pipeline and update the current image on ImageView
     * @throws PipelineException if the pipeline is empty
     */
    public void refreshPipeline() throws PipelineException {
        drawImage(filterPipeline.run(imageInitialData).getResult());
    }

    /**
     * Add a filter to the filter list
     * @param filter filter to add
     */
    public void addFilterSelection(Filter filter) {
        filterList.add(filter);
        actualFiltersList.clear();
        actualFiltersList.addAll(filterList);
    }

    /**
     * Send filter to backend and update actualFilterPipeline ListView on frontend
     * @param filter filter to be added
     */
    public void addFilterPipeline(Filter filter){
        filterPipeline.add(new FilterTask(filter));
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getTasks());
    }

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
     * Set the component used to display the image
     * @param imageWrapper the image to display
     */
    public void setImageComponent(ImageWrapper imageWrapper) {
        drawImage(imageWrapper);
    }


    /**
     * Get the FilterPipeline object
     * @return FilterPipeline object
     */
    public FilterPipeline getFilterPipeline() {
        return filterPipeline;
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
     * Get actual list of possible filters
     * @return removed filter
     */
    public ObservableList<Filter> getActualFiltersList() {
        return actualFiltersList;
    }

    /**
     * Get actual filter pipeline
     * @return actual filter pipeline
     */
    public ObservableList<Task<ImageWrapper, FilterTaskResult>> getActualFiltersPipeline(){
        return actualFiltersPipeline;
    }
}
