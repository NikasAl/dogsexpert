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
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nikas on 11/27/16.
 */
public class NeuralProcessor {
//    public static final int IM_SIZE = 10;
//    public static final int IM_SIZE = 15;
    public static final int IM_SIZE = 50;
    public static final String NETWORK = "data/theBestNet0_73pErr_50x50_250-150-150-150-100_2d.eg";
//    public static final String NETWORK = "data/theBestNet0_66pErr_15x15_700-600-500-400-300-200-100_2d.eg";
//    public static final String NETWORK = "data/theBestNet2_4pErr_10x10_250-150-100-80_5h.eg.zip";

    private Downsample downsample;
    private BasicNetwork network;

    public NeuralProcessor() {
        downsample = new RGBDownsample();
//        FileHandle fh = Gdx.files.internal("data/theBestNet0_73pErr_50x50_250-150-150-150-100_2d.eg");
//        this.network = (BasicNetwork) EncogDirectoryPersistence.loadObject(fh.read());
        network = Assets.getAssetMgr().get(NETWORK, BasicNetwork.class);
    }

//    NumberFormat nf = new DecimalFormat("0.000000#");
    public ArrayList<Breed> processWhatIs(Pixmap pixmap) {
        ArrayList<Breed> breedArrayList = new ArrayList<Breed>();
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
            breedArrayList.add(breed);
        }

        Collections.sort(breedArrayList, new Comparator<Breed>() {
            @Override
            public int compare(Breed breed1, Breed breed2) {
                return breed2.getPercent() - breed1.getPercent();
            }
        });
        return breedArrayList;
    }
}
