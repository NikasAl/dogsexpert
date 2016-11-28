package ru.electronikas.dogsexpert.neural.processing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;
import ru.electronikas.dogsexpert.neural.downsample.Downsample;
import ru.electronikas.dogsexpert.neural.downsample.RGBDownsample;
import ru.electronikas.dogsexpert.neural.image.PixmapMLData;

import java.io.File;

/**
 * Created by nikas on 11/27/16.
 */
public class NeuralProcessor {

    Downsample downsample;
    BasicNetwork network;
    int downsampleWidth = 30;
    int downsampleHeight = 30;

    public NeuralProcessor() {
        downsample = new RGBDownsample();
        this.network = (BasicNetwork) EncogDirectoryPersistence.loadObject(new File("./data/theBestNet2_07prctErr_15x15_120-100-70.eg"));
    }

    public void processWhatIs(Texture texture) {

        Pixmap pixmap = getPixmapFromTexture(texture);
        final PixmapMLData input = new PixmapMLData(pixmap);
        input.downsample(this.downsample, false, this.downsampleHeight,
                this.downsampleWidth, 1, -1);

        final int winner = this.network.winner(input);

//        System.out.println("What is: " + filename + ", it seems to be: "
//                + this.neuron2identity.get(winner));

        Gdx.app.log("WINNER:", "" + winner);
//		MLData outData = network.compute(input);
//		for (double speed : outData.getData()) {
//			System.out.print(nf.format(speed) + ", ");
//		}
//		System.out.println();

    }

    private Pixmap getPixmapFromTexture(Texture texture) {
        if(!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        return texture.getTextureData().consumePixmap();
    }

}
