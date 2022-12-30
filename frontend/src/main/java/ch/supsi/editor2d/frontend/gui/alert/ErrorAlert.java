package ch.supsi.editor2d.frontend.gui.alert;

import javafx.scene.control.Alert;

public class ErrorAlert {
    public static void showError(String message){
        // Create the dialog
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(message);
        alert.setContentText(":(");
        // Add IDs to be able to test the dialog
        alert.getDialogPane().setId("errorDialog");
        alert.getDialogPane().lookupButton(alert.getButtonTypes().get(0)).setId("errorDialogOkButton");
        // Show the dialog
        alert.showAndWait();
    }
}
