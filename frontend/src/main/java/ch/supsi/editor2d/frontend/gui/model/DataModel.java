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
import ch.supsi.editor2d.frontend.gui.event.util.FileExport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


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

    private final List<Filter> filterList = new ArrayList<>();

    /**
     * Filter pipeline
     */
    private final FilterPipeline filterPipeline;

    private final ObservableList<Filter> actualFiltersList;

    private final ObservableList<Task<ImageWrapper, FilterTaskResult>> actualFiltersPipeline;

    public DataModel() {
        this.imageComponent = new ImageView();
        this.imageController = new ImageController();
        this.filterPipeline = new FilterPipeline();
        this.actualFiltersPipeline = FXCollections.observableArrayList();
        this.actualFiltersList = FXCollections.observableArrayList();
    }

    public ImageView getImageComponent() {
        return imageComponent;
    }

    public ImageWrapper getImageData() {
        return imageData;
    }

    // Load an image from a given path
    public void loadImage(String path) {
        try {
            ImageWrapper img = imageController.getImage(path);
            drawImage(img);
        } catch (FileReadingException e) {
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }

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

    // Set the image which has to be shown. Used after applying a filter
    public void setImageComponent(ImageWrapper imageWrapper) {
        drawImage(imageWrapper);
    }

    // Draw an ImageWrapper on ImageView
    private void drawImage(ImageWrapper imageWrapper) {
        imageData = imageWrapper;

        WritableImage writableImage = new WritableImage(imageWrapper.getWidth(), imageWrapper.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int h = 0; h < imageWrapper.getHeight(); h++) {
            for (int w = 0; w < imageWrapper.getWidth(); w++) {
                ColorWrapper tempColor = imageWrapper.getData()[h][w];
                pixelWriter.setColor(w, h, Color.color(tempColor.getRed(),tempColor.getGreen(),tempColor.getBlue()));
            }
        }

        imageComponent.setImage(writableImage);
    }

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
        actualFiltersPipeline.addAll(filterPipeline.getQueue());
    }

    public ObservableList<Filter> getActualFiltersList() {
        return actualFiltersList;
    }

    public ObservableList<Task<ImageWrapper, FilterTaskResult>> getActualFiltersPipeline(){
        return actualFiltersPipeline;
    }

    public void removeTaskFromPipeline(Task<ImageWrapper, FilterTaskResult> task) {
        filterPipeline.remove(task);
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getQueue());
        // TODO: 25/10/22 Re-execute the pipeline and update image
    }

    /**
     * Method used for swipe up a filter in the pipeline list
     * @param task to swipe up
     */
    public void swipeUpFilterPipeline(Task<ImageWrapper, FilterTaskResult> task){
        filterPipeline.invertBeforePositionTask(task);
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getQueue());
    }

    /**
     * Method used for swipe down a filter in the pipeline list
     * @param task to swipe down
     */
    public void swipeDownFilterPipeline(Task<ImageWrapper, FilterTaskResult> task){
        filterPipeline.invertAfterPositionTask(task);
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getQueue());
    }

    public FilterTaskResult runPipeline(ImageWrapper imageWrapper) throws PipelineException {
        return filterPipeline.run(imageWrapper);
    }
}
