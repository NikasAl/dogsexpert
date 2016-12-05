package ru.electronikas.dogsexpert.neural.processing;

import com.badlogic.gdx.graphics.Pixmap;
import org.encog.ml.data.MLData;
import org.encog.neural.networks.BasicNetwork;
import ru.electronikas.dogsexpert.Assets;
import ru.electronikas.dogsexpert.neural.Breed;
import ru.electronikas.dogsexpert.neural.downsample.Downsample;
import ru.electronikas.dogsexpert.neural.downsample.RGBDownsample;
import ru.electronikas.dogsexpert.neural.image.PixmapMLData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikas on 11/27/16.
 */
public class NeuralProcessor {
    public static final int IM_SIZE = 50;
    public static final String NETWORK = "data/theBestNet0_73pErr_50x50_250-150-150-150-100_2d.eg";

    private Downsample downsample;
    private BasicNetwork network;

    public NeuralProcessor() {
        downsample = new RGBDownsample();
//        FileHandle fh = Gdx.files.internal("data/theBestNet0_73pErr_50x50_250-150-150-150-100_2d.eg");
//        this.network = (BasicNetwork) EncogDirectoryPersistence.loadObject(fh.read());
        network = Assets.getAssetMgr().get(NETWORK, BasicNetwork.class);
    }

//    NumberFormat nf = new DecimalFormat("0.000000#");
    public List<Breed> processWhatIs(Pixmap pixmap) {
        final List<Breed> breedResultList = new ArrayList<Breed>();
        final PixmapMLData input = new PixmapMLData(pixmap);
        int downsampleHeight = IM_SIZE;
        int downsampleWidth = IM_SIZE;

        input.downsample(downsample, false, downsampleHeight,
                downsampleWidth, 1, -1);

//        final int winner = network.winner(input);
//        Gdx.app.log("WINNER:", "" + winner);
//        Gdx.app.log("WINNER:", "" + Breed.values()[winner]);

		MLData outData = network.compute(input);
//		for (double speed : outData.getData()) {
//			System.out.print(nf.format(speed) + ", ");
//		}
//		System.out.println();
        int count = 0;
        while (count < outData.getData().length) {
            Breed breed = Breed.values()[count];
            breed.setResult(outData.getData(count));
            count++;
            breedResultList.add(breed);
        }
        return breedResultList;
    }
}
