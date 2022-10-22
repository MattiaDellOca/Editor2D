package ch.supsi.editor2d.backend.repository.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static ch.supsi.editor2d.backend.repository.utils.LineChecker.checkDataLine;

/**
 * Utility used for simplifying the parsing of data read by a PNM file
 * It implements a queue from which elements are retrieved so that the client doesn't have to worry about how the values
 * are stored in the original file.
 */
public class DataValuesParser {
    private final BufferedReader bufferedReader;
    private final Queue<Integer> queue;

    public DataValuesParser(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
        queue = new LinkedList<>();
        init();
    }

    private void init() throws IOException {
        String line;
        while((line = bufferedReader.readLine()) != null) {
            line = checkDataLine(line, '#');
            if(line.equals(""))
                continue;
            String[] elements = line.split("[\s\n\t]");
            for(String e : elements)
                queue.add(Integer.parseInt(e));
        }
    }

    public int getNext() throws IOException {
        return queue.poll();
    }
}
