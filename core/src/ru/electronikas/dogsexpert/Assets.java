package ru.electronikas.dogsexpert;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.I18NBundle;
import org.encog.neural.networks.BasicNetwork;
import ru.electronikas.dogsexpert.neural.processing.NeuralNetLoader;
import ru.electronikas.dogsexpert.neural.processing.NeuralProcessor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * User: navdonin
 * Date: 07/01/14
 */
public class Assets {

    private static I18NBundle myBundle;

    public static I18NBundle bdl() {
        if(myBundle==null) {
            FileHandle baseFileHandle = Gdx.files.internal("i18n/gameBundle");
            Locale locale = Locale.getDefault();
            myBundle = I18NBundle.createBundle(baseFileHandle, locale);
        }
        return myBundle;
    }

    public static String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    private static AssetManager assetManager;
    public static AssetManager getAssetMgr() {
        if(assetManager==null) {
            assetManager = new AssetManager();
        }
        return assetManager;
    }

    public static void initLoading() {
        startLoadNet();
    }


    private static void startLoadNet() {
        NeuralNetLoader nnl = new NeuralNetLoader(new InternalFileHandleResolver());
        getAssetMgr().setLoader(BasicNetwork.class, nnl);
        getAssetMgr().load(NeuralProcessor.NETWORK, BasicNetwork.class);
    }


    private static Map<String,Image> breedImages = new HashMap<String, Image>();
    public static Image getBreedImage(String imagePath) {
        Image img = breedImages.get(imagePath);
        if(img==null) {
            Image nimg = new Image(new Texture(imagePath));
            breedImages.put(imagePath, nimg);
            img = nimg;
        }
        return img;
    }
}
