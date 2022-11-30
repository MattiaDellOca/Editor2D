package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.command.CancelCommand;
import ch.supsi.editor2d.frontend.gui.command.OkCommand;
import ch.supsi.editor2d.frontend.gui.model.CancelHandler;
import ch.supsi.editor2d.frontend.gui.model.ExitHandler;
import ch.supsi.editor2d.frontend.gui.model.Observable;
import ch.supsi.editor2d.frontend.gui.model.OkHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ExitDialogReceiver<T extends Observable> extends AbstractReceiver<Observable> implements ExitHandler,
        CancelHandler, OkHandler {

    protected Stage exitStage;

    protected Stage mainStage;

    protected ExitDialogReceiver(T model, Stage exitStage, Stage mainStage) {
        super(model);
        this.exitStage = exitStage;
        this.mainStage = mainStage;
    }

    // factory method
    public static ExitDialogReceiver<Observable> create(Observable model, Stage exitStage, Stage mainStage)
            throws InstantiationException {
        if(model == null) {
            throw new InstantiationException("controller model cannot be null!");
        }

        if(exitStage == null) {
            throw new InstantiationException("exit stage cannot be null!");
        }

        if(mainStage == null) {
            throw new InstantiationException("main stage cannot be null!");
        }

        return new ExitDialogReceiver<>(model, exitStage, mainStage);
    }

    public void setCancelCommand(CancelCommand<CancelHandler> command) {
        Button cancelButton = (Button) exitStage.getScene().lookup("#cancelBtn");
        cancelButton.setOnAction(actionEvent -> command.execute());
    }

    public void setOkCommand(OkCommand<OkHandler> command) {
        Button okButton = (Button) exitStage.getScene().lookup("#okBtn");
        okButton.setOnAction(actionEvent -> command.execute());
    }

    @Override
    public void exit() {
        // check if data model status is set to changed
        if(! this.model.isChanged()) {
            // the model's status hasn't changed
            mainStage.close();
            System.exit(0);
        } else {
            exitStage.show();
        }
    }

    @Override
    public void cancel() {
        exitStage.close();
    }

    @Override
    public void ok() {
        cancel();
        mainStage.close();
        System.exit(0);
    }
}
