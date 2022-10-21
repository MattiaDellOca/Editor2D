package ch.supsi.editor2d.backend.repository.utils;

import java.io.BufferedReader;
import java.io.IOException;


/**
 * Utility for checking if a comment is present in the bufferedReader next line
 */
public class LineChecker {

    public static String checkAndGetLine(char commentType, BufferedReader bufferedReader) throws IOException {
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

        //create a substring
        String possibleResult = line.substring(0, indexComment);
        if (possibleResult.length() > 0) {
            //there is some information
            return possibleResult;
        }

        //no information in this line, return the next one
        return checkAndGetLine(commentType, bufferedReader);
    }

    public static String checkLine(String line, char commentType) {
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
