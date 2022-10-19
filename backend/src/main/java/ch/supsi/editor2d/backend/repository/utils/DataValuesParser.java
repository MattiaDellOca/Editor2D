package ch.supsi.editor2d.backend.repository.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static ch.supsi.editor2d.backend.repository.utils.LineChecker.checkLine;

public class DataValuesParser {
    private final BufferedReader bufferedReader;
    private final Queue<String> queue;

    public DataValuesParser(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
        queue = new LinkedList<>();
        init();
    }

    private void init() throws IOException {
        String line;
        while((line = bufferedReader.readLine()) != null) {
            line = checkLine(line, '#');
            if(line.equals(""))
                continue;
            String[] elements = line.split("[\s\n]");
            queue.addAll(Arrays.asList(elements));
        }
    }

    public String getNext() throws IOException {
        return queue.poll();
    }
}
