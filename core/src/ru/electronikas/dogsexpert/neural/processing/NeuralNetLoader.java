package ru.electronikas.dogsexpert.neural.processing;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

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
        this.network = (BasicNetwork) EncogDirectoryPersistence.loadObject(file.read());
        return network;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, AssetLoaderParameters<BasicNetwork> parameter) {
        return null;
    }
}
