package ch.supsi.editor2d.frontend.gui.model;


import ch.supsi.editor2d.backend.controller.ImageController;
import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.helper.FilterPipeline;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.MatrixFilter;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class DataModel {

    /**
     * Interface imageController
     * the right controller (ImagePBMController, ImagePPMController, ...) will be assigned on run time
     */
    private final ImageController imageController;

    /**
     * Image showed
     */
    private final ImageView image;

    private ImageWrapper imageLoaded;

    /**
     * Filter pipeline
     */
    private final FilterPipeline filterPipeline;


    private final ObservableList<Task<ImageWrapper, FilterTaskResult>> actualFiltersPipeline;

    public DataModel() {
        this.image = new ImageView();
        this.imageController = new ImageController();
        this.filterPipeline = new FilterPipeline();
        this.actualFiltersPipeline = FXCollections.observableArrayList();
    }

    public ImageView getImage() {
        return image;
    }

    public ImageWrapper getImageLoaded() {
        return imageLoaded;
    }

    // Load an image from a given path
    public void loadImage(String path) {

        try {
            ImageWrapper img = imageController.getImage(path);
            drawImage(img);
        } catch (FileReadingException e) {
            //Show Alert
            System.err.println(e.getMessage());
            ErrorAlert.showError(e.getMessage());
        }
    }

    // Set the image which has to be shown. Used after applying a filter
    public void setImage(ImageWrapper imageWrapper) {
        drawImage(imageWrapper);
    }

    // Draw an ImageWrapper on ImageView
    private void drawImage(ImageWrapper imageWrapper) {
        imageLoaded = imageWrapper;

        WritableImage writableImage = new WritableImage(imageWrapper.getWidth(), imageWrapper.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int h = 0; h < imageWrapper.getHeight(); h++) {
            for (int w = 0; w < imageWrapper.getWidth(); w++) {
                ColorWrapper tempColor = imageWrapper.getData()[h][w];
                pixelWriter.setColor(w, h, Color.color(tempColor.getRed(),tempColor.getGreen(),tempColor.getBlue()));
            }
        }

        image.setImage(writableImage);
    }

    /**
     * Send filter to backend and update actualFilterPipeline ListView on frontend
     * @param filter filter to be added
     */
    public void addFilterPipeline(MatrixFilter filter){
        filterPipeline.add(new FilterTask(filter));
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getQueue());
    }

    public ObservableList<Task<ImageWrapper, FilterTaskResult>> getActualFiltersPipeline(){
        return actualFiltersPipeline;
    }

    public void removeTaskFromPipeline(Task<ImageWrapper, FilterTaskResult> task) {
        filterPipeline.remove(task);
        actualFiltersPipeline.clear();
        actualFiltersPipeline.addAll(filterPipeline.getQueue());
    }
}
