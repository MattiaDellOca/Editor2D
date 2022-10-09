package ch.supsi.editor2d.backend.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public interface IImageController {
    public ImageWrapper getImage(String path);
}
