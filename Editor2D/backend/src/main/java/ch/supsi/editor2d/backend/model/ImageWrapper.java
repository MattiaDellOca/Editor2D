package ch.supsi.editor2d.backend.model;

public class ImageWrapper {

    public String exampleData = "A";

    public ImageWrapper () {
    }

    public ImageWrapper (ImageWrapper wrapper) {
        this.exampleData = wrapper.exampleData;
    }
}
