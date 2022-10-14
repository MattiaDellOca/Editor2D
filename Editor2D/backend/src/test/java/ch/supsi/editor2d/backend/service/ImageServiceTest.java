package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.model.ImagePGM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {

    private ImageService imageService;
    private final String pathImageTestPBMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PBM/testPBMOk.pbm")).getPath();
    private final String pathImageTestPGMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMOk.pgm")).getPath();

    @BeforeEach
    void init(){
        imageService = new ImageService();
    }

    @Test
    void getImageNoExtension() {
        Exception ex = assertThrows(FileReadingException.class, () -> imageService.getImage("path/without/extension"));
        assertEquals("This file does not have an extension",ex.getMessage());
    }

    @Test
    void getImageExtensionUnhandled() {
        Exception ex = assertThrows(FileReadingException.class, () -> imageService.getImage("path/with/unhandled/extension.png"));
        assertEquals("File extension not supported",ex.getMessage());
    }

    @Test
    void chainOfResponsibility(){
        try {
            ImageWrapper PBM = imageService.getImage(pathImageTestPBMOk);
            ImageWrapper PGM = imageService.getImage(pathImageTestPGMOk);

            assertEquals(ImagePBM.class, PBM.getClass());
            assertEquals(ImagePGM.class, PGM.getClass());
        } catch (FileReadingException ignored){}
    }
}