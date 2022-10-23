package ch.supsi.editor2d.frontend.gui.controller;

import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Start extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        DataModel model = new DataModel();

        // Main View page
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
        Parent mainView = mainViewLoader.load();
        MainViewController mainViewController = mainViewLoader.getController();
        mainViewController.initModel(model);

        // Image View page
        FXMLLoader imageViewLoader = new FXMLLoader(getClass().getResource("/view/imageView.fxml"));
        Parent imageView = imageViewLoader.load();
        ImageViewController imageViewController = imageViewLoader.getController();
        imageViewController.initModel(model);

        // Load about page
        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("/view/aboutView.fxml"));
        Parent aboutView = aboutLoader.load();

        // Set imageView.fxml inside mainView.fxml
        Pane imagePane = mainViewController.getImagePane();
        imagePane.getChildren().add(imageView);

        imagePane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));

        // File visualization handling
        mainViewController.setOnFileDropped(e -> {
            // Pass file path to model
            model.loadImage(e.getFile().getAbsolutePath());
            // Now, refresh image view to show the new image
            imageViewController.refresh();
        });

        // Menu event handling
        mainViewController.setOnAboutMenuClicked(e -> {
            // Create new window and show about page
            Stage aboutStage = new Stage();
            aboutStage.setScene(new Scene(aboutView));
            aboutStage.show();
        });

        stage.setTitle("Editor2D");
        stage.setScene(new Scene(mainView));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
