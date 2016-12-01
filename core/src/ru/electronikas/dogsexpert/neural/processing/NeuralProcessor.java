package ru.electronikas.dogsexpert.neural.processing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.encog.ml.data.MLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;
import ru.electronikas.dogsexpert.neural.Breeds;
import ru.electronikas.dogsexpert.neural.downsample.Downsample;
import ru.electronikas.dogsexpert.neural.downsample.RGBDownsample;
import ru.electronikas.dogsexpert.neural.image.PixmapMLData;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by nikas on 11/27/16.
 */
public class NeuralProcessor {

    Downsample downsample;
    BasicNetwork network;
    int downsampleWidth = 50;
    int downsampleHeight = 50;

    public NeuralProcessor() {
        downsample = new RGBDownsample();
        FileHandle fh = Gdx.files.internal("data/theBestNet0_73pErr_50x50_250-150-150-150-100_2d.eg");
        this.network = (BasicNetwork) EncogDirectoryPersistence.loadObject(fh.read());
    }

    NumberFormat nf = new DecimalFormat("0.000000#");
    public void processWhatIs(Pixmap pixmap) {
        final PixmapMLData input = new PixmapMLData(pixmap);
        input.downsample(downsample, false, downsampleHeight,
                downsampleWidth, 1, -1);

        final int winner = network.winner(input);

        Gdx.app.log("WINNER:", "" + winner);
        Gdx.app.log("WINNER:", "" + Breeds.values()[winner]);

		MLData outData = network.compute(input);
		for (double speed : outData.getData()) {
			System.out.print(nf.format(speed) + ", ");
		}
		System.out.println();

    }

    private Pixmap getPixmapFromTexture(Texture texture) {
        if(!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        return texture.getTextureData().consumePixmap();
    }

}
