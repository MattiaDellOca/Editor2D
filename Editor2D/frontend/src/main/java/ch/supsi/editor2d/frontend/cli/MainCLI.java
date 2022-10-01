package ch.supsi.editor2d.frontend.cli;


import ch.supsi.editor2d.controller.MainController;

public class MainCLI {
    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.SayHello();
    }
}
