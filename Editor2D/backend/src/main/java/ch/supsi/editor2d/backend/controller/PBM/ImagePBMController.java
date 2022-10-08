package ch.supsi.editor2d.backend.controller.PBM;

import ch.supsi.editor2d.backend.controller.IImageController;
import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.service.PBM.ImagePBMService;

public class ImagePBMController implements IImageController<ImagePBM> {

    private final IImagePBMController serviceLayer;

    public ImagePBMController() {
        serviceLayer = new ImagePBMService();
    }

    @Override
    public ImagePBM getImage() {
        return null;
    }
}
