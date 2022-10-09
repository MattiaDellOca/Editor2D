package ch.supsi.editor2d.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FlipFilterApplicatorTest {

    @Test
    void generateFlipMatrix() {
        FlipFilterApplicator f = new FlipFilterApplicator();
        assertTrue(Arrays.deepEquals(new double[][] { {0, 0, 1}, {0, 1, 0}, {1, 0, 0}}, f.generateFlipMatrix(3)));
        assertTrue(Arrays.deepEquals(new double[][] { {1} }, f.generateFlipMatrix(1)));
    }
}