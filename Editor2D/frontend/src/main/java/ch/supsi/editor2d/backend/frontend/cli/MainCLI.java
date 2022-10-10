package ch.supsi.editor2d.backend.frontend.cli;


import ch.supsi.editor2d.backend.controller.MainController;

public class MainCLI {
    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.SayHello();
    }
}
