package ch.supsi.editor2d.backend.service.PBM;
import ch.supsi.editor2d.backend.model.ImagePBM;

/**
 * Interface for PBM image
 * pattern: dependency inversion
 */
public interface IImagePBMService {
    ImagePBM loadImage(String path);
}
