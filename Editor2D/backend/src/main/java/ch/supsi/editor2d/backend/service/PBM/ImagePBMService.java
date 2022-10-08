package ch.supsi.editor2d.backend.service.PBM;

import ch.supsi.editor2d.backend.controller.PBM.IImagePBMController;
import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.repository.PBM.ImagePBMRepository;
import ch.supsi.editor2d.backend.service.IImageService;

public class ImagePBMService implements IImageService<ImagePBM>, IImagePBMController {
    private final IImagePBMService repositoryLayer;

    public ImagePBMService() {
        repositoryLayer = new ImagePBMRepository();
    }

    @Override
    public ImagePBM getImage() {
        return null;
    }
}
