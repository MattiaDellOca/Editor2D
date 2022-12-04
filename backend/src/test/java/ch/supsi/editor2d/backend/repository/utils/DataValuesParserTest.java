package ch.supsi.editor2d.backend.repository.utils;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DataValuesParserTest {
    private final String path = Objects.requireNonNull(getClass().getClassLoader().getResource("utils/buffered_reader_values.txt")).getPath();

    @Test
    void getNext() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            DataValuesParser parser = new DataValuesParser(reader);
            int[] values = new int[6];
            for(int i = 0; i < values.length; i++)
                values[i] = parser.getNext();

            assertArrayEquals(values, new int[] {1, 2, 3, 4, 10, 20});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}