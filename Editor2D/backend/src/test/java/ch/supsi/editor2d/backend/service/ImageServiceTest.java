package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {

    private ImageService imageService;

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
}