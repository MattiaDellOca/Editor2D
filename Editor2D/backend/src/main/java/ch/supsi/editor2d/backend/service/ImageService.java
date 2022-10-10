package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.controller.IImageController;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.repository.ImagePBMRepository;
import ch.supsi.editor2d.backend.repository.ImagePGMRepository;

public class ImageService implements IImageController {
    private IImageService repositoryLayer;

   @Override
    public ImageWrapper getImage(String path) {
        /*if(path.equals("PBM")){
            this.repositoryLayer = new ImagePBMRepository();
        } else if(path.equals("PGM")){
            this.repositoryLayer = new ImagePGMRepository();
        }*/
       this.repositoryLayer = new ImagePBMRepository();
        return repositoryLayer.loadImage(path);
    }

}
