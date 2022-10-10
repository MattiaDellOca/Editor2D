package ch.supsi.editor2d.backend.repository.PBM;

import ch.supsi.editor2d.backend.model.ImagePBM;
import ch.supsi.editor2d.backend.repository.ImagePBMRepository;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.paint.Color;

import java.util.Objects;

class ImagePBMRepositoryTest {

    @Test
    void loadImage() {
        ImagePBMRepository imagePBMRepository = new ImagePBMRepository();

        ImagePBM obtainedResult = imagePBMRepository.loadImage(Objects.requireNonNull(getClass().getClassLoader().getResource("testOK.pbm")).getPath());

        //result desired creation
        int widthDesiredResult = 10;
        int heightDesiredResult = 6;
        Color[][] data = new Color[heightDesiredResult][widthDesiredResult];
        for (int h = 0; h < heightDesiredResult; h++) {
            for (int w = 0; w < widthDesiredResult; w++) {
                if(h % 2 == 0){
                    data[h][w] = Color.BLACK;
                } else{
                    data[h][w] = Color.WHITE;
                }
            }
        }

        //test
        assertEquals(obtainedResult.getWidth(),widthDesiredResult);
        assertEquals(obtainedResult.getHeight(),heightDesiredResult);
        for (int h = 0; h < heightDesiredResult; h++) {
            for (int w = 0; w < widthDesiredResult; w++) {
                assertEquals(obtainedResult.getData()[h][w].toString(), data[h][w].toString());
            }
        }

    }
}
