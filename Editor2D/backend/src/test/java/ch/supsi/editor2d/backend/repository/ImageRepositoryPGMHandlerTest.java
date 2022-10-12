package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ImageRepositoryPGMHandlerTest {

    private ImageRepositoryPGMHandler imageRepositoryPGMHandler;
    private final String pathImageTestPGMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMOk.pgm")).getPath();
    private final String pathImageTestPGMWrongMagicNumber = Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMWrongMagicNumber.pgm")).getPath();
    private final String pathImageTestPGMMalformedBody = Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMMalformedBody.pgm")).getPath();

    @BeforeEach
    void init(){
        imageRepositoryPGMHandler = new ImageRepositoryPGMHandler();
    }


    @Test
    void handleLoadResultCorrect() {

        //result desired creation
        int widthDesiredResult = 10;
        int heightDesiredResult = 6;
        int scaleOfGray = 15;

        int grayColorValue1 = 255 / scaleOfGray;
        int grayColorValue5 = (255 / scaleOfGray) * 5;
        int grayColorValue10 = (255 / scaleOfGray) * 10;

        Color[][] data = new Color[heightDesiredResult][widthDesiredResult];
        for (int h = 0; h < heightDesiredResult; h++) {
            for (int w = 0; w < widthDesiredResult; w++) {
                if (h == 0) {
                    data[h][w] = Color.rgb(grayColorValue1,grayColorValue1,grayColorValue1);
                } else if(h == 2){
                    data[h][w] = Color.rgb(grayColorValue5,grayColorValue5,grayColorValue5);
                }else if(h == 4){
                    data[h][w] = Color.rgb(grayColorValue10,grayColorValue10,grayColorValue10);
                }
                else {
                    data[h][w] = Color.rgb(0,0,0);
                }
            }
        }

        try {
            ImageWrapper obtainedResult = imageRepositoryPGMHandler.handleLoad("PGM", pathImageTestPGMOk);
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
        Exception ex = assertThrows(FileReadingException.class, () -> imageRepositoryPGMHandler.handleLoad("PGM",pathImageTestPGMWrongMagicNumber));
        assertEquals("Magic number is incorrect",ex.getMessage());
    }

    @Test
    void handleLoadMalformedBody(){
        Exception ex = assertThrows(FileReadingException.class, () -> imageRepositoryPGMHandler.handleLoad("PGM",pathImageTestPGMMalformedBody));
        assertEquals("Error during image loading",ex.getMessage());

    }

}