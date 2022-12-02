package ch.supsi.editor2d.frontend.gui.model;

import java.io.File;

public class FileExport {
    private final String filename;
    private final File destination;
    private final String extension;

    public FileExport(final String filename, final File destination, final String extension) {
        this.filename = filename;
        this.destination = destination;
        this.extension = extension;
    }

    public String getFilename() {
        return filename;
    }

    public File getDestination() {
        return destination;
    }

    public String getExtension() {
        return extension;
    }
}
