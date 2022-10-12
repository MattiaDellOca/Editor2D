package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.controller.IImageController;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.repository.ImagePBMRepository;
import ch.supsi.editor2d.backend.repository.ImagePGMRepository;


//TODO apply chain responsibility
public class ImageService implements IImageController {
    private IImageService repositoryLayer;

    @Override
    public ImageWrapper getImage(String path) {

        int indexDot = path.indexOf('.');
        if(indexDot == -1 ) {//dot not found
            throw new RuntimeException(); //File without an extension
        }
        String extension = path.substring(indexDot + 1);
        System.out.println(extension);



        return repositoryLayer.handleLoad(path);
    }

}
