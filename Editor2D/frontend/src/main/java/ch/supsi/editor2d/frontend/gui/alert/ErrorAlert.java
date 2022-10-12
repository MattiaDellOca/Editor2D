package ch.supsi.editor2d.frontend.gui.alert;

import javafx.scene.control.Alert;

public class ErrorAlert {
    public static void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(message);
        alert.setContentText(":(");
        System.err.println(message);
        alert.showAndWait();
    }
}
