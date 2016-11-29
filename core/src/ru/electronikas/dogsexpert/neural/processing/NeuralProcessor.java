package ru.electronikas.dogsexpert.neural.processing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;
import ru.electronikas.dogsexpert.neural.downsample.Downsample;
import ru.electronikas.dogsexpert.neural.downsample.RGBDownsample;
import ru.electronikas.dogsexpert.neural.image.PixmapMLData;

/**
 * Created by nikas on 11/27/16.
 */
public class NeuralProcessor {

    Downsample downsample;
    BasicNetwork network;
    int downsampleWidth = 15;
    int downsampleHeight = 15;

    public NeuralProcessor() {
        downsample = new RGBDownsample();
        FileHandle fh = Gdx.files.internal("data/theBestNet2_07prctErr_15x15_120-100-70.eg");
        this.network = (BasicNetwork) EncogDirectoryPersistence.loadObject(fh.read());
    }

    public void processWhatIs(Pixmap pixmap) {

        final PixmapMLData input = new PixmapMLData(pixmap);
        input.downsample(downsample, false, downsampleHeight,
                downsampleWidth, 1, -1);

        final int winner = network.winner(input);

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
