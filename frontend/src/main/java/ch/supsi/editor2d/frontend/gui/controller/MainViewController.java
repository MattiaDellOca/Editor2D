package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.event.FileDropEvent;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Main view controller that handles the main view logic.
 */
public class MainViewController {

    /**
     * List of supported file extensions
     */
    private final String[] SUPPORTED_FORMATS = new String[]{"ppm", "pgm", "pbm", "tga"};

    /**
     * Scroll pane for pipeline FXML
     */
    @FXML
    private AnchorPane pipelinePane;

    /**
     * Data model reference
     */
    private DataModel model;

    /**
     * File drop event handler
     */
    private EventHandler<FileOpenEvent> fileOpened = event -> {
    };

    private EventHandler<ImageUpdatedEvent> imageUpdated = event -> {};

    /**
     * Image pane reference
     */
    @FXML
    private Pane imagePane;

    @FXML
    private ListView<String> filtersSelectionView;

    @FXML
    private ListView<String> filtersSelectedView;

    private final List<String> filters = new ArrayList<>(List.of(new String[]{"Flip", "Grayscale", "Sepia"}));

    private final Pipeline<ImageWrapper, FilterTaskResult> filterPipeline = new FilterPipeline();

    public void setOnFileDropped(EventHandler<FileDropEvent> event) {
        this.fileDropped = event;
    }
    /**
     * Loaded about stage
     */
    private Stage aboutStage;

    public void setOnImageUpdated(EventHandler<ImageUpdatedEvent> event) { this.imageUpdated = event; }

    public EventHandler<FileDropEvent> getOnFileDropped() {
        return this.fileDropped;
    }
    /**
     * File chooser reference
     */
    private FileChooser fileChooser;

    public EventHandler<ImageUpdatedEvent> getOnImageUpdated() { return this.imageUpdated; }

    public void initModel(DataModel model){
    /**
     * Initialize the model reference and set all the event handlers
     *
     * @param model Data model
     */
    public void init(DataModel model) {

        // ensure model is only set once
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;

        // set event handlers + load about view
        try {
            initEventHandlers();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load about view, please check the resources folder");
        }
    }

    private void initEventHandlers() throws IOException {
        // Load about page
        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("/view/aboutView.fxml"));
        aboutStage = new Stage();
        aboutStage.setScene(new Scene(aboutLoader.load()));

        // Load file chooser
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open picture");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Image Files",
                        Arrays.stream(SUPPORTED_FORMATS).map(s -> "*." + s).toArray(String[]::new)
                )
        );

        // Enable drag and drop to imagePane component
        imagePane.setOnDragOver(dragEvent -> {
            if (
                    dragEvent.getGestureSource() != imagePane
                            && dragEvent.getDragboard().hasFiles()
                            && isSupportedFormat(dragEvent.getDragboard().getFiles().get(0))
            ) {
                dragEvent.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
            }
            dragEvent.consume();
        });

        // Handle image drop
        imagePane.setOnDragDropped(dragEvent -> {
            boolean success = dragEvent.getDragboard().hasFiles()
                    && dragEvent.getDragboard().getFiles().size() == 1
                    && isSupportedFormat(dragEvent.getDragboard().getFiles().get(0));

            // handle response
            dragEvent.setDropCompleted(success);
            if (success) {
                // Fire file dropped event
                fileOpened.handle(new FileOpenEvent(dragEvent.getDragboard().getFiles().get(0), imagePane));
            }
            // Consume event
            dragEvent.consume();
        });
    }

    /**
     * Check if the file extension is supported
     *
     * @param file File to check
     * @return true if the file extension is supported, false otherwise
     */
    private boolean isSupportedFormat(File file) {
        // Check if file is supported
        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        for (String format : SUPPORTED_FORMATS) {
            if (extension.equals(format)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Set the file dropped event handler
     *
     * @param event File drop event
     */
    public void setOnFileOpen(EventHandler<FileOpenEvent> event) {
        this.fileOpened = event;
    }

    /**
     * Get the image pane reference
     *
     * @return Image pane
     */
    public Pane getImagePane() {
        return imagePane;
    }

        // Add filters to filtersSelectionView
        filtersSelectionView.getItems().addAll(filters);
        // Add ChangeListener to filtersSelectionView
        filtersSelectionView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, filter) -> {
            filtersSelectedView.getItems().add(filter);
            ImageWrapper image = model.getImageLoaded();

            switch (filter) {
                case "Flip" -> filterPipeline.add(new FilterTask(new FlipFilter()));
                case "Grayscale" -> filterPipeline.add(new FilterTask(new GrayscaleFilter()));
                case "Sepia" -> filterPipeline.add(new FilterTask(new SepiaFilter()));
            }

            ImageWrapper i = filterPipeline.run(image).getResult();

            getOnImageUpdated().handle(new ImageUpdatedEvent(i, imagePane));

        });


        // Add ChangeListener to filtersSelectedView
        filtersSelectedView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String filter = filtersSelectedView.getSelectionModel().getSelectedItem();
            filtersSelectionView.getItems().add(filter);
            //filtersSelectedView.getItems().remove(filter);
        });

        this.model = model;

    /**
     * Get the pipeline pane reference
     * @return Pipeline Pane
     */
    public AnchorPane getPipelinePane() {
        return pipelinePane;
    }

    /**
     * Handle the zoom out action
     *
     * @param actionEvent Action event
     */
    public void zoomOut(ActionEvent actionEvent) {
        System.out.println("ZOOMING OUT: " + actionEvent);
    }

    /**
     * Handle the zoom in action
     *
     * @param actionEvent Action event
     */
    public void zoomIn(ActionEvent actionEvent) {
        System.out.println("ZOOMING IN: " + actionEvent);
    }

    /**
     * Handle the about menu action
     */
    public void onAboutMenu() {
        // Open a new window with about view
        if (aboutStage != null)
            aboutStage.show();
    }

    public void onRunPipeline(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    public void onRedo(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    public void onUndo(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    public void onSave(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    public void onOpen() {
        // Open file browser
        File file = fileChooser.showOpenDialog(imagePane.getScene().getWindow());

        // additional file extension check
        if (file != null && isSupportedFormat(file)) {
            // Fire file dropped event
            fileOpened.handle(new FileOpenEvent(file, imagePane));
        }
    }

    public void onClose(ActionEvent actionEvent) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }
}
