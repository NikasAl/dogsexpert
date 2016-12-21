package ru.electronikas.dogsexpert.neural.processing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import java.util.Date;

/**
 * Created by nikas on 12/4/16.
 */
public class NeuralNetLoader extends AsynchronousAssetLoader<BasicNetwork, AssetLoaderParameters<BasicNetwork>> {

    BasicNetwork network;

    public NeuralNetLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, AssetLoaderParameters<BasicNetwork> parameter) {

    }

    @Override
    public BasicNetwork loadSync(AssetManager manager, String fileName, FileHandle file, AssetLoaderParameters<BasicNetwork> parameter) {
        long start = new Date().getTime();
        Gdx.app.log("Loading Net", "start");
        this.network = (BasicNetwork) EncogDirectoryPersistence.loadObject(file.read());
/*
        ZipInputStream zis = new ZipInputStream(file.read());

        ZipEntry entry;
        // while there are entries I process them
        try {
            while ((entry = zis.getNextEntry()) != null)
            {
                System.out.println("entry: " + entry.getName() + ", " + entry.getSize());
                // consume all the data from this entry
                if(zis.available() > 0)
//                    zis.read();
                // I could close the entry, but getNextEntry does it automatically
                // zis.closeEntry()
                this.network = (BasicNetwork) EncogDirectoryPersistence.loadObject(zis);
                 zis.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        Gdx.app.log("Loading Net", "done after - " + (new Date().getTime() - start) + "ms");
        return network;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, AssetLoaderParameters<BasicNetwork> parameter) {
        return null;
    }
}
