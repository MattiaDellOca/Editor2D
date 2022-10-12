package ch.supsi.editor2d.backend;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.repository.ImageRepositoryPBMHandler;
import ch.supsi.editor2d.backend.repository.ImageRepositoryPGMHandler;
import ch.supsi.editor2d.backend.service.ImageService;

public class MainController {

    public static void main(String[] args) {

       /* ImageRepositoryPBMHandler h1 = new ImageRepositoryPBMHandler();
        ImageRepositoryPGMHandler h2 = new ImageRepositoryPGMHandler();
        h1.setSuccessor(h2);

        ImageWrapper result = null;
            result = h1.handleLoad("/home/manuelenolli/Desktop/Image/img3.pbm");

        System.out.println(result.getWidth() + " " + result.getHeight());
        for (int h = 0; h < result.getHeight(); h++) {
            for (int w = 0; w < result.getWidth(); w++) {
                System.out.print(result.getData()[h][w] + " ");
            }
            System.out.println();
        }
*/

        ImageService imageService = new ImageService();
        try {
            imageService.getImage("/home/manuelenolli/Desktop/Image/img3.pbm");
        } catch (FileReadingException e) {
            throw new RuntimeException(e);
        }
    }
}
