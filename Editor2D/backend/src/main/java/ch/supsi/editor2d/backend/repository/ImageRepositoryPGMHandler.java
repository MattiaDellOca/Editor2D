package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.model.ImagePPM;
import ch.supsi.editor2d.backend.model.ImageWrapper;

public class ImageRepositoryPGMHandler extends ImageRepositoryHandler{

    @Override
    public ImageWrapper handleLoad(String path) {
        if(path.equals("PPM")){
            return new ImagePPM(1,1,null,1);
        } else if(successor != null){
            return successor.handleLoad(path);
        }
        throw new RuntimeException("No one can handle this request");
    }
}
