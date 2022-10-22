package ch.supsi.editor2d.backend.repository.utils;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineCheckerTest {

    @Test
    void checkAndGetLineRemoveContent() {
        String expected = "1234 ";
        Reader reader = new StringReader("1234 # Comment");
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            String result = LineChecker.checkHeaderLine('#', bufferedReader);
            assertEquals(expected, result);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void checkAndGetLineEmptyLine() {
        Reader reader = new StringReader("");
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            LineChecker.checkHeaderLine('#', bufferedReader);
            fail();
        } catch (IOException ignored) {
        }
    }

    @Test
    void checkAndGetLineMultiLine() {
        List<String> expected = Arrays.asList("Line1", "Line3");

        Reader reader = new StringReader("Line1\n#Cancel\nLine3");
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            String result1 = LineChecker.checkHeaderLine('#', bufferedReader);
            String result2 = LineChecker.checkHeaderLine('#', bufferedReader);
            assertEquals(expected.get(0), result1);
            assertEquals(expected.get(1), result2);
        } catch (IOException e) {
            fail();
        }
    }
}