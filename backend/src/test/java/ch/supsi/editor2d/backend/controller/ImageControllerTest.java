package ch.supsi.editor2d.backend.controller;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.model.ImagePGM;
import ch.supsi.editor2d.backend.model.ImagePPM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ImageControllerTest {

    private ImageController imageController;
    private final String pathImageTestPBMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PBM/testPBMOk.pbm")).getPath();
    private final String pathImageTestPGMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMOk.pgm")).getPath();
    private final String pathImageTestPPMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PPM/testPPMOk.ppm")).getPath();

    @BeforeEach
    void init(){
        imageController = new ImageController();
    }

    @Test
    void getImageNoExtension() {
        Exception ex = assertThrows(FileReadingException.class, () -> imageController.getImage("path/without/extension"));
        assertEquals("This file does not have an extension",ex.getMessage());
    }

    @Test
    void getImageExtensionUnhandled() {
        Exception ex = assertThrows(FileReadingException.class, () -> imageController.getImage("path/with/unhandled/extension.png"));
        assertEquals("File extension not supported",ex.getMessage());
    }

    @Test
    void chainOfResponsibility(){
        try {
            ImageWrapper PBM = imageController.getImage(pathImageTestPBMOk);
            ImageWrapper PGM = imageController.getImage(pathImageTestPGMOk);
            ImageWrapper PPM = imageController.getImage(pathImageTestPPMOk);

            assertEquals(ImagePBM.class, PBM.getClass());
            assertEquals(ImagePGM.class, PGM.getClass());
            assertEquals(ImagePPM.class, PPM.getClass());
        } catch (FileReadingException ignored){}
    }

}