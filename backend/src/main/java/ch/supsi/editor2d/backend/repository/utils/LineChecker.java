package ch.supsi.editor2d.backend.repository.utils;

import java.io.BufferedReader;
import java.io.IOException;


/**
 * Utility for checking if a comment is present in a given line.
 * The information related to the header are read directly from the BufferedReader, while the data's information
 * are passed by a DataValuesParser
 */
public class LineChecker {

    public static String checkHeaderLine(char commentType, BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        if (line == null) {
            throw new IOException();
        }

        line = line.replaceAll("\s+", " ");

        // Remove the first char if it's a space
        if(line.startsWith(" "))
            line = line.substring(1);

        int indexComment = line.indexOf(commentType);
        if (indexComment == -1) {
            //no comment present
            return line;
        }

        return line.substring(0, indexComment);
    }

    public static String checkDataLine(String line, char commentType) {
        // Replace multiple spaces with only one
        line = line.replaceAll("\s+", " ");

        // Remove the first char if it's a space
        if(line.startsWith(" "))
            line = line.substring(1);


        int indexComment = line.indexOf(commentType);
        if (indexComment == -1) {
            //no comment present
            return line;
        }

        //create a substring
        return line.substring(0, indexComment);
    }
}
