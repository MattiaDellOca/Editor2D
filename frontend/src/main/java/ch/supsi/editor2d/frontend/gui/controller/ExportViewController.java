package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.frontend.gui.model.FileExport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class ExportViewController {

    /**
     * The filename field (FXML injected)
     */
    @FXML
    private TextField filename;

    /**
     * The destination directory object
     */
    private File destinationDir;

    /**
     * The file format combo box (FXML injected)
     */
    @FXML
    private ComboBox<String> extension;

    /**
     * The export button (FXML injected)
     */
    @FXML
    private Button browseDir;

    /**
     * The export directory path  (FXML injected)
     */
    @FXML
    private TextField exportDirectory;

    @FXML
    private Button exportButton;

    /**
     * List of the supported export file formats (empty by default)
     */
    private final ObservableList<String> supportedFormats = FXCollections.observableArrayList();

    /**
     * Directory chooser used to select the destination directory
     */
    private DirectoryChooser chooser;

    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        // Setup supported formats
        setSupportedFormat();

        // Setup Directory picker
        chooser = new DirectoryChooser();
        chooser.setTitle("Select export destination");
        chooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );

        // Setup browse button
        browseDir.setOnAction(event -> {
            // Open a directory chooser
            destinationDir = chooser.showDialog(browseDir.getScene().getWindow());
            if (destinationDir != null) {
                // Update the filename field
                exportDirectory.setText(destinationDir.getAbsolutePath());
            }
        });
    }

    public Button getExportButton() {
        return exportButton;
    }

    /**
     * Export image action
     */
    public FileExport exportImage() {
        if (chooser == null) {
            throw new IllegalArgumentException("CRITICAL ERROR! Controller not initialized!");
        } else {
            // Check if the filename is empty
            if (filename.getText().isEmpty()) {
                ErrorAlert.showError("Filename cannot be empty!");
            } else {
                // Check if the destination directory is empty
                if (destinationDir == null) {
                    ErrorAlert.showError("Destination directory cannot be empty!");
                } else {
                    // Check if the extension is empty
                    if (extension.getValue() == null) {
                        ErrorAlert.showError("Extension cannot be empty!");
                    } else {
                        // Create a new FileExport object
                        FileExport export = new FileExport(
                                filename.getText(),
                                destinationDir,
                                extension.getValue()
                        );
                        // Close the window
                        close();
                        // Return the export object
                        return export;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Set the supported file formats
     */
    private void setSupportedFormat() {
        // Set the supported file formats
        this.supportedFormats.addAll(Start.SUPPORTED_FORMATS);
        // Set the supported file formats
        extension.setItems(supportedFormats);
        this.extension.setValue(Start.SUPPORTED_FORMATS.iterator().next());
    }

    /**
     * Close export window
     */
    @FXML
    private void close() {
        // Close window
        filename.getScene().getWindow().hide();

        // Clear fields
        filename.clear();
        exportDirectory.clear();
        extension.setValue(supportedFormats.iterator().next());
        destinationDir = null;
    }
}
