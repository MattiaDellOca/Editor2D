package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ImageRepositoryPBMHandlerTest {

    private ImageRepositoryPBMHandler imageRepositoryPBMHandler;
    private final String pathImageTestPBMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PBM/testPBMOk.pbm")).getPath();
    private final String pathImageTestPBMWrongMagicNumber = Objects.requireNonNull(getClass().getClassLoader().getResource("PBM/testPBMWrongMagicNumber.pbm")).getPath();
    private final String pathImageTestPBMMalformedBody = Objects.requireNonNull(getClass().getClassLoader().getResource("PBM/testPBMMalformedBody.pbm")).getPath();

    @BeforeEach
    void init(){
        imageRepositoryPBMHandler = new ImageRepositoryPBMHandler();
    }


    @Test
    void handleLoadResultCorrect() {

        //result desired creation
        int widthDesiredResult = 10;
        int heightDesiredResult = 6;
        Color[][] data = new Color[heightDesiredResult][widthDesiredResult];
        for (int h = 0; h < heightDesiredResult; h++) {
            for (int w = 0; w < widthDesiredResult; w++) {
                if (h % 2 == 0) {
                    data[h][w] = Color.BLACK;
                } else {
                    data[h][w] = Color.WHITE;
                }
            }
        }

        try {
            ImageWrapper obtainedResult = imageRepositoryPBMHandler.handleLoad("PBM", pathImageTestPBMOk);
            assertEquals(obtainedResult.getWidth(), widthDesiredResult);
            assertEquals(obtainedResult.getHeight(), heightDesiredResult);
            for (int h = 0; h < heightDesiredResult; h++) {
                for (int w = 0; w < widthDesiredResult; w++) {
                    assertEquals(obtainedResult.getData()[h][w].toString(), data[h][w].toString());
                }
            }
        } catch (FileReadingException e) {
            fail();
        }
    }

    @Test
    void handleLoadWrongMagicNumber(){
        Exception ex = assertThrows(FileReadingException.class, () -> imageRepositoryPBMHandler.handleLoad("PBM", pathImageTestPBMWrongMagicNumber));
        assertEquals("Magic number is incorrect",ex.getMessage());
    }

    @Test
    void handleLoadMalformedBody(){
        Exception ex = assertThrows(FileReadingException.class, () -> imageRepositoryPBMHandler.handleLoad("PBM", pathImageTestPBMMalformedBody));
        assertEquals("Error during image loading",ex.getMessage());

    }
}