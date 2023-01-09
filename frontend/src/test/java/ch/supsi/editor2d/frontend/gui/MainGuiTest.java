package ch.supsi.editor2d.frontend.gui;

import ch.supsi.editor2d.backend.model.filter.*;
import ch.supsi.editor2d.backend.model.task.FilterTask;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.util.UIToolkit;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to test the MainGUI class.
 */
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainGuiTest {

    private DataModel model;

    private static final List<Filter> FILTERS = Arrays.asList(new FlipFilter(), new GrayscaleFilter(), new SepiaFilter(), new SharpenFilter());

    private final static long SLEEP_TIME = 100;

    private final String PPM_PATH = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("images/PPM/testPPMOk.ppm")).getFile()).getAbsolutePath();

    /**
     * This method is used to start the application.
     * @param stage the stage to be used.
     * @throws Exception if something goes wrong.
     */
    @Start
    public void start(Stage stage) throws Exception {
        // Build stage
        this.model = UIToolkit.buildAndStartStage(stage, FILTERS);
        // Show stage
        stage.show();
    }

    @BeforeEach
    public void tearDown() {
        // Reset data model after each test
        this.model.reset();
    }

    /**
     * Tests that the initial state of the application is correct.
     */
    @Test
    @Order(1)
    public void stateInitTest(FxRobot robot) {
        // Zoom in / Zoom out buttons
        FxAssert.verifyThat("#zoomOutButton", Node::isDisabled);
        FxAssert.verifyThat("#zoomInButton", Node::isDisabled);

        // Pipeline buttons
        FxAssert.verifyThat("#moveUpFilter", Node::isDisabled);
        FxAssert.verifyThat("#moveDownFilter", Node::isDisabled);
        FxAssert.verifyThat("#removeFilter", Node::isDisabled);
        FxAssert.verifyThat("#runPipeline", Node::isDisabled);

        // Assert that list view "filterPipelineList" is empty
        FxAssert.verifyThat("#filterPipelineList", (ListView<String> listView) -> listView.getItems().isEmpty());

        // Verify that the export dialog, about dialog and exit dialog don't exist yet
        Assertions.assertFalse(robot.lookup("#exportDialog").tryQuery().isPresent());
        Assertions.assertFalse(robot.lookup("#aboutPage").tryQuery().isPresent());
        Assertions.assertFalse(robot.lookup("#exitDialog").tryQuery().isPresent());
    }


    /**
     * Test the import of a supported image file.
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(2)
    public void loadImageTest(FxRobot robot) {
        // Load PPM image from resources
        robot.interact(() -> model.loadImage(PPM_PATH));

        // Wait until image is loaded
        FxAssert.verifyThat("#imageView", Node::isVisible);
    }

    /**
     * Tests that the zoom in / zoom out buttons work correctly
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(3)
    public void zoomInOutTest (FxRobot robot) {
        // Load PPM image from resources
        robot.interact(() -> model.loadImage(PPM_PATH));

        // Wait until image is loaded
        FxAssert.verifyThat("#imageView", Node::isVisible);

        // Now, verify that zoom in / zoom out buttons are enabled
        FxAssert.verifyThat("#zoomOutButton", (n) -> !n.isDisabled());
        FxAssert.verifyThat("#zoomInButton", (n) -> !n.isDisabled());

        // Now, zoom in and out the image and verify that the application does not crash
        robot.clickOn("#zoomInButton");
        robot.clickOn("#zoomOutButton");

        // Verify that the image is still visible
        FxAssert.verifyThat("#imageView", Node::isVisible);
    }

    /**
     * Tests the filter selection and the pipeline list buttons correct status (move up, move down, remove filter, run pipeline)
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(4)
    public void filterSelectionAndButtonTest (FxRobot robot) {
        // Load image
        robot.interact(() -> model.loadImage(PPM_PATH));

        // Then, select the first filter
        robot.clickOn("#filterSelectionList");
        robot.lookup("#filterSelectionList").tryQuery().ifPresentOrElse(listView -> {
            // Select the first filter
            ListView<Filter> temp = (ListView<Filter>) listView;
            temp.getSelectionModel().select(0);
            assert temp.getSelectionModel().getSelectedItem().getName().equals("Flip");

            // Add selected filter
            robot.clickOn();
        }, () -> Assertions.fail("Could not find filter selection list"));

        // Check the added FilterTask of the pipeline
        robot.clickOn("#filterPipelineList");
        robot.lookup("#filterPipelineList").tryQuery().ifPresentOrElse(listView -> {
            // Select the first filter
            ListView<FilterTask> temp = (ListView<FilterTask>) listView;
            temp.getSelectionModel().select(0);
            assert temp.getSelectionModel().getSelectedItem().getFilter().getName().equals("Flip");

            // Add selected filter
            robot.clickOn();

            // Assert that only the "Delete" button is enabled
            FxAssert.verifyThat("#moveUpFilter", Node::isDisabled);
            FxAssert.verifyThat("#moveDownFilter", Node::isDisabled);
            FxAssert.verifyThat("#removeFilter", (n) -> !n.isDisabled());
            FxAssert.verifyThat("#runPipeline", (n) -> !n.isDisabled());
        }, () -> Assertions.fail("Could not find filter selection list"));

        // Verify that the "play" button in now clickable
        FxAssert.verifyThat("#runPipeline", n -> !n.isDisabled());

        // Now, add new 2 other filters
        robot.clickOn("#filterSelectionList");
        robot.lookup("#filterSelectionList").tryQuery().ifPresentOrElse(listView -> {
            for (int i = 1; i < 3; i++) {
                // Select the first filter
                ListView<Filter> temp = (ListView<Filter>) listView;
                temp.getSelectionModel().select(i);
                assert temp.getSelectionModel().getSelectedItem().getName().equals(FILTERS.get(i).getName());

                // Add selected filter
                robot.clickOn();
            }
        }, () -> Assertions.fail("Could not find filter selection list"));

        // Now, check that the move up / move down buttons work correctly. There are 3 possible cases:
        // 1. First filter = move up disabled
        // 2. Last filter = move down disabled
        // 3. Middle filter = both enabled

        // First, select the first filter
        robot.clickOn("#filterPipelineList");
        robot.lookup("#filterPipelineList").tryQuery().ifPresentOrElse(listView -> {
            // Select the first filter
            ListView<FilterTask> temp = (ListView<FilterTask>) listView;
            temp.getSelectionModel().select(0);

            // Assert that only the "Delete" button is enabled
            FxAssert.verifyThat("#moveUpFilter", Node::isDisabled);
            FxAssert.verifyThat("#moveDownFilter", (n) -> !n.isDisabled());
            FxAssert.verifyThat("#removeFilter", (n) -> !n.isDisabled());
            FxAssert.verifyThat("#runPipeline", (n) -> !n.isDisabled());

            // Now, select the last filter
            temp.getSelectionModel().selectLast();

            // Assert that only the "Delete" button is enabled
            FxAssert.verifyThat("#moveUpFilter", (n) -> !n.isDisabled());
            FxAssert.verifyThat("#moveDownFilter", Node::isDisabled);
            FxAssert.verifyThat("#removeFilter", (n) -> !n.isDisabled());
            FxAssert.verifyThat("#runPipeline", (n) -> !n.isDisabled());

            // Now, select the middle filter
            temp.getSelectionModel().select(1);

            // Assert that all the buttons are enabled
            FxAssert.verifyThat("#moveUpFilter", (n) -> !n.isDisabled());
            FxAssert.verifyThat("#moveDownFilter", (n) -> !n.isDisabled());
            FxAssert.verifyThat("#removeFilter", (n) -> !n.isDisabled());
            FxAssert.verifyThat("#runPipeline", (n) -> !n.isDisabled());
        }, () -> Assertions.fail("Could not find filter selection list"));
    }

    /**
     * Tests the pipeline list arrangement buttons (move up, move down)
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(5)
    public void filterArrangeTest (FxRobot robot) {
        // Load image
        robot.interact(() -> model.loadImage(PPM_PATH));

        // Then, add some filters
        robot.clickOn("#filterSelectionList");
        robot.lookup("#filterSelectionList").tryQuery().ifPresentOrElse(listView -> {
            for (int i = 0; i < 4; i++) {
                // Select the first filter
                ((ListView<?>) listView).getSelectionModel().select(i);
                robot.clickOn();
                robot.sleep(SLEEP_TIME);
            }
        }, () -> Assertions.fail("Could not find filter selection list"));
        robot.sleep(SLEEP_TIME);

        // Now, check that the move up / move down buttons work correctly. There are 3 possible cases:
        robot.clickOn("#filterPipelineList");
        robot.lookup("#filterPipelineList").tryQuery().ifPresentOrElse(listView -> {
            // Select the first filter
            ListView<FilterTask> temp = (ListView<FilterTask>) listView;

            // Save the first and second filter
            temp.getSelectionModel().select(0);
            FilterTask firstFilter = temp.getSelectionModel().getSelectedItem();
            temp.getSelectionModel().select(1);
            FilterTask secondFilter = temp.getSelectionModel().getSelectedItem();

            // Select the first filter again
            temp.getSelectionModel().select(0);

            // Assert that only the "Delete" button is enabled
            FxAssert.verifyThat("#moveUpFilter", Node::isDisabled);
            FxAssert.verifyThat("#moveDownFilter", (n) -> !n.isDisabled());
            FxAssert.verifyThat("#removeFilter", (n) -> !n.isDisabled());
            FxAssert.verifyThat("#runPipeline", (n) -> !n.isDisabled());

            // Now, move the filter down
            robot.clickOn("#moveDownFilter");

            // Now, check that the first filter is the second filter
            temp.getSelectionModel().select(0);
            Assertions.assertEquals(secondFilter, temp.getSelectionModel().getSelectedItem());

            // Now, check that the second filter is the first filter
            temp.getSelectionModel().select(1);
            Assertions.assertEquals(firstFilter, temp.getSelectionModel().getSelectedItem());

            // Now, move up the second filter
            robot.clickOn("#moveUpFilter");

            // Now, check that the first filter is the first filter
            temp.getSelectionModel().select(0);
            Assertions.assertEquals(firstFilter, temp.getSelectionModel().getSelectedItem());

            // Now, check that the second filter is the second filter
            temp.getSelectionModel().select(1);
            Assertions.assertEquals(secondFilter, temp.getSelectionModel().getSelectedItem());
        }, () -> Assertions.fail("Could not find filter selection list"));
    }

    /**
     * Tests the pipeline remove button
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(6)
    public void filterRemoveTest (FxRobot robot) {
        // Load image
        robot.interact(() -> model.loadImage(PPM_PATH));

        // Then, add some filters
        robot.clickOn("#filterSelectionList");
        robot.lookup("#filterSelectionList").tryQuery().ifPresentOrElse(listView -> {
            for (int i = 0; i < 2; i++) {
                // Select the first filter
                ((ListView<?>) listView).getSelectionModel().select(i);
                robot.clickOn();
                robot.sleep(SLEEP_TIME);
            }
        }, () -> Assertions.fail("Could not find filter selection list"));
        robot.sleep(SLEEP_TIME);

        // Now, check that the move up / move down buttons work correctly. There are 3 possible cases:
        robot.clickOn("#filterPipelineList");
        robot.lookup("#filterPipelineList").tryQuery().ifPresentOrElse(listView -> {
            // Select the first filter
            ListView<FilterTask> temp = (ListView<FilterTask>) listView;

            // Save filter reference to the second filter
            temp.getSelectionModel().select(1);
            FilterTask secondFilter = temp.getSelectionModel().getSelectedItem();

            // Now, remove the first filter
            temp.getSelectionModel().select(0);
            FxAssert.verifyThat("#removeFilter", (n) -> !n.isDisabled());
            robot.clickOn("#removeFilter");

            // Select the first filter again and check that it is the second filter
            temp.getSelectionModel().select(0);
            Assertions.assertEquals(secondFilter, temp.getSelectionModel().getSelectedItem());

            // Now, remove the second filter
            FxAssert.verifyThat("#removeFilter", (n) -> !n.isDisabled());
            robot.clickOn("#removeFilter");

            // Now, check that the list is empty
            Assertions.assertEquals(0, temp.getItems().size());
        }, () -> Assertions.fail("Could not find filter selection list"));
    }

    /**
     * Tests the pipeline run button with a few filters.
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(7)
    public void pipelineRunTest (FxRobot robot) {
        // Load image
        robot.interact(() -> model.loadImage(PPM_PATH));

        // Now, check that the image has been changed
        ImageView imageFrame = robot.lookup("#imageView").query();
        Image preImage = imageFrame.getImage();
        Assertions.assertNotNull(preImage);

        // Then, add some filters
        robot.clickOn("#filterSelectionList");
        robot.lookup("#filterSelectionList").tryQuery().ifPresentOrElse(listView -> {
            for (int i = 0; i < 2; i++) {
                // Select the first filter
                ((ListView<?>) listView).getSelectionModel().select(i);
                robot.clickOn();
                robot.sleep(SLEEP_TIME);
            }
        }, () -> Assertions.fail("Could not find filter selection list"));
        robot.sleep(SLEEP_TIME);

        // Now, run the pipeline
        robot.clickOn("#runPipeline");

        // Now, check that the image has been changed
        Image postImage = imageFrame.getImage();
        Assertions.assertNotEquals(postImage, preImage);

        // add more filters
        robot.clickOn("#filterSelectionList");
        robot.lookup("#filterSelectionList").tryQuery().ifPresentOrElse(listView -> {
            for (int i = 2; i < 4; i++) {
                // Select the first filter
                ((ListView<?>) listView).getSelectionModel().select(i);
                robot.clickOn();
                robot.sleep(SLEEP_TIME);
            }
        }, () -> Assertions.fail("Could not find filter selection list"));

        // Now, run the pipeline
        robot.clickOn("#runPipeline");

        // Now, check that the image has been changed
        Image postImage2 = imageFrame.getImage();
        Assertions.assertNotEquals(postImage2, postImage);
    }

    /**
     * Tests the "Abort" button in the "Export image" dialog.
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(8)
    public void exportDialogFormTest(FxRobot robot) {
        // Load PPM image from resources
        robot.interact(() -> model.loadImage(PPM_PATH));

        // Ensure that the node "#exportButton" does not exist
        Assertions.assertFalse(robot.lookup("#exportButton").tryQuery().isPresent());
        robot.clickOn("#fileMenu").clickOn("#exportMenuItem");
        FxAssert.verifyThat("#exportDialog", Node::isVisible);
        robot.clickOn("#closeExportButton");
        Assertions.assertFalse(robot.lookup("#exportButton").tryQuery().isPresent());
    }

    /**
     * Tests that the export dialog form is cleared after an abort.
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(9)
    public void exportDialogAbortFormTest(FxRobot robot) {
        robot.interact(() -> model.loadImage(PPM_PATH));

        // Reopen export dialog
        robot.clickOn("#fileMenu").clickOn("#exportMenuItem");
        FxAssert.verifyThat("#exportDialog", Node::isVisible);

        // Now fill in the dialog with generated information
        robot.clickOn("#filename").write("test");
        robot.clickOn("#extension").type(KeyCode.DOWN).type(KeyCode.ENTER);

        // Now, click the abort button
        robot.clickOn("#closeExportButton");
        Assertions.assertFalse(robot.lookup("#exportButton").tryQuery().isPresent());

        // Reopen export dialog
        robot.clickOn("#fileMenu").clickOn("#exportMenuItem");
        FxAssert.verifyThat("#exportDialog", Node::isVisible);

        // Verify that the form is empty
        FxAssert.verifyThat("#filename", (n) -> ((TextField) n).getText().isEmpty());
        FxAssert.verifyThat("#extension", (n) -> ((ComboBox<?>) n).getSelectionModel().getSelectedItem().equals("ppm"));
    }

    /**
     * This test is used to verify that the application does not crash when the user tries to open the "about" dialog.
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(10)
    public void aboutPageTest (FxRobot robot) {
        // Click on the Help menu
        robot.clickOn("#helpMenu").clickOn("#aboutMenuItem");

        // Wait until the about page is shown
        FxAssert.verifyThat("#aboutPage", Node::isVisible);

        // Verify that the main window is still visible
        FxAssert.verifyThat("#mainWindow", Node::isVisible);
    }

    /**
     * This test is used to verify that the "Exit" menu item works as expected.
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Disabled
    @Order(11)
    // FIXME: This test cannot be completed since the "Close" menu item kills the application before the test can complete
    public void closeActionTest (FxRobot robot) {
        // Ensure that the main window is visible
        FxAssert.verifyThat("#mainWindow", Node::isVisible);

        // Now, click on the "Exit" menu item
        robot.clickOn("File").clickOn("#exitMenuItem");
    }

    /**
     * This test is used to verify that the "undo" and "redo" operation work as expected.
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(12)
    public void undoRedoActionsTest (FxRobot robot) {
        // Load image
        robot.interact(() -> model.loadImage(PPM_PATH));

        // Then, add some filters
        robot.clickOn("#filterSelectionList");
        robot.lookup("#filterSelectionList").tryQuery().ifPresentOrElse(listView -> {
            for (int i = 0; i < 2; i++) {
                // Select the first filter
                ((ListView<?>) listView).getSelectionModel().select(i);
                robot.clickOn();
                robot.sleep(SLEEP_TIME);
            }
        }, () -> Assertions.fail("Could not find filter selection list"));
        robot.sleep(SLEEP_TIME);

        robot.lookup("#filterPipelineList").tryQuery().ifPresentOrElse(listView -> {
            // Select the first filter
            ListView<FilterTask> temp = (ListView<FilterTask>) listView;

            // Save filter reference to the second filter
            temp.getSelectionModel().select(1);
            FilterTask secondFilter = temp.getSelectionModel().getSelectedItem();

            // Now, remove the last inserted filter
            robot.clickOn("#editMenu").clickOn("#undoMenuItem");
            // Now, check that the list has only 1 item
            Assertions.assertEquals(1, temp.getItems().size());

            // Select the first filter again and check that it is the second filter
            temp.getSelectionModel().select(0);
            FilterTask firstFilter = temp.getSelectionModel().getSelectedItem();

            // Now, remove the first inserted filter
            robot.clickOn("#editMenu").clickOn("#undoMenuItem");
            // Now, check that the list is empty
            Assertions.assertEquals(0, temp.getItems().size());

            // Let's now test the 'redo' operation
            robot.clickOn("#editMenu").clickOn("#redoMenuItem");
            Assertions.assertEquals(1, temp.getItems().size());
            temp.getSelectionModel().select(0);
            Assertions.assertEquals(firstFilter, temp.getSelectionModel().getSelectedItem());
            robot.clickOn("#editMenu").clickOn("#redoMenuItem");
            Assertions.assertEquals(2, temp.getItems().size());
            temp.getSelectionModel().select(1);
            Assertions.assertEquals(secondFilter, temp.getSelectionModel().getSelectedItem());
        }, () -> Assertions.fail("Could not find filter selection list"));
    }

    /**
     * This test is used to verify that a confirmation is asked when trying to close the app with unsaved changes.
     * @param robot the robot used to interact with the GUI. Automatically injected by the TestFX framework.
     */
    @Test
    @Order(13)
    public void closeConfirmationTest (FxRobot robot) {
        // Load image and apply some filters
        robot.interact(() -> model.loadImage(PPM_PATH));
        robot.clickOn("#filterSelectionList");
        robot.lookup("#filterSelectionList").tryQuery().ifPresentOrElse(listView -> {
            for (int i = 0; i < 2; i++) {
                // Select the first filter
                ((ListView<?>) listView).getSelectionModel().select(i);
                robot.clickOn();
                robot.sleep(SLEEP_TIME);
            }
        }, () -> Assertions.fail("Could not find filter selection list"));
        robot.sleep(SLEEP_TIME);

        // Now, run the pipeline
        robot.clickOn("#runPipeline");

        // Then verify that a confirmation dialog is launched when trying to close the application
        robot.clickOn("File").clickOn("#exitMenuItem");
        FxAssert.verifyThat("#exitDialog", Node::isVisible);

        // Now, click the on "cancel" button
        robot.clickOn("#cancelBtn");

        // Verify that the exit dialog has been closed
        Assertions.assertTrue(robot.lookup("#exitDialog").tryQuery().isEmpty());

        // Verify that the main window is still visible
        FxAssert.verifyThat("#mainWindow", Node::isVisible);
    }
}