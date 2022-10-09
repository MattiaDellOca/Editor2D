package ch.supsi.editor2d.backend.controller.PBM;

import ch.supsi.editor2d.backend.model.ImagePBM;

/**
 * Interface for PBM image
 * pattern: dependency inversion
 */
public interface IImagePBMController {
    ImagePBM getImage(String path);
}
