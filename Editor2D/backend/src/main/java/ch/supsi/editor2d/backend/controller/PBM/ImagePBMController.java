package ch.supsi.editor2d.backend.controller.PBM;

import ch.supsi.editor2d.backend.controller.IImageController;
import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.service.PBM.ImagePBMService;

public class ImagePBMController implements IImageController {

    private final IImagePBMController serviceLayer;

    public ImagePBMController() {
        serviceLayer = new ImagePBMService();
    }


    @Override
    public ImagePBM getImage(String path) {
        return serviceLayer.getImage(path);
    }
}
