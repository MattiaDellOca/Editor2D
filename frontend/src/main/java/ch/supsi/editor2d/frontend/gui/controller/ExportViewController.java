package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.alert.ErrorAlert;
import ch.supsi.editor2d.frontend.gui.event.util.FileExport;
import ch.supsi.editor2d.frontend.gui.event.FileExportEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.Collection;

public class ExportViewController {

    /**
     * The filename field (FXML injected)
     */
    @FXML
    public TextField filename;

    /**
     * The destination directory object
     */
    public File destinationDir;

    /**
     * The file format combo box (FXML injected)
     */
    @FXML
    public ComboBox<String> extension;

    /**
     * The export button (FXML injected)
     */
    @FXML
    public Button browseDir;

    /**
     * The export directory path  (FXML injected)
     */
    @FXML
    public TextField exportDirectory;

    /**
     * List of the supported export file formats (empty by default)
     */
    private final ObservableList<String> supportedFormats = FXCollections.observableArrayList();

    /**
     * The event handler to be called when the user clicks on the export button.
     */
    private EventHandler<FileExportEvent> onExport = event -> {};

    /**
     * Directory chooser used to select the destination directory
     */
    private DirectoryChooser chooser;

    /**
     * Initialize the controller
     * @param supportedFormat the supported export file formats
     */
    public void init (Collection<String> supportedFormat) {
        // Setup supported formats
        setSupportedFormat(supportedFormat);

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
            // Update the filename field
            exportDirectory.setText(destinationDir.getAbsolutePath());
        });
    }

    /**
     * Export image action
     */
    public void exportImage() {
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
                        // Fire the event + target is the current instance
                        onExport.handle(new FileExportEvent(export, browseDir.getScene().getWindow()));

                        // Close the window
                        close();
                    }
                }
            }
        }
    }

    /**
     * Set the supported file formats
     * @param supportedFormat the supported file formats
     */
    public void setSupportedFormat(Collection<String> supportedFormat) {
        // Set the supported file formats
        this.supportedFormats.addAll(supportedFormat);
        // Set the supported file formats
        extension.setItems(supportedFormats);
        this.extension.setValue(supportedFormat.iterator().next());
    }

    /**
     * Set the event handler to be called when the user clicks on the export button.
     * @param handler the event handler
     */
    public void setOnExport(EventHandler<FileExportEvent> handler) {
        this.onExport = handler;
    }

    /**
     * Close export window
     */
    public void close() {
        // Close window
        filename.getScene().getWindow().hide();

        // Clear fields
        filename.clear();
        exportDirectory.clear();
        extension.setValue(supportedFormats.iterator().next());
        destinationDir = null;
    }
}
