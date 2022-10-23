package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Start extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        DataModel model = new DataModel();

        // Main View page
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
        Parent mainView = mainViewLoader.load();
        MainViewController mainViewController = mainViewLoader.getController();
        mainViewController.init(model);

        // Image View page
        FXMLLoader imageViewLoader = new FXMLLoader(getClass().getResource("/view/imageView.fxml"));
        Parent imageView = imageViewLoader.load();
        ImageViewController imageViewController = imageViewLoader.getController();
        imageViewController.initModel(model);


        // Set imageView.fxml inside mainView.fxml
        Pane imagePane = mainViewController.getImagePane();
        imagePane.getChildren().add(imageView);

        // File visualization handling
        mainViewController.setOnFileOpen(e -> {
            model.loadImage(e.getFile().getAbsolutePath());
            // Now, refresh image view to show the new image
            imageViewController.refresh();
        });

        stage.setTitle("Editor2D");
        stage.setScene(new Scene(mainView));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
