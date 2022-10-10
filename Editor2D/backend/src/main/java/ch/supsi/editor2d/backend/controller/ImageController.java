package ch.supsi.editor2d.backend.controller;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.service.ImageService;

public class ImageController {

    private final IImageController serviceLayer;

    public ImageController() {
        this.serviceLayer = new ImageService();
    }

    public ImageWrapper getImage(String path){
        return serviceLayer.getImage(path);
    }
}
