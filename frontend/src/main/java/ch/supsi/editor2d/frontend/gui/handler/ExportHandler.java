package ch.supsi.editor2d.frontend.gui.handler;

import ch.supsi.editor2d.frontend.gui.model.FileExport;
import javafx.stage.Stage;

public interface ExportHandler extends Handler {
    //Show export stage
    void exportPage(Stage stage);

    //Export the image
    void exportImage(FileExport exportReq);
}
