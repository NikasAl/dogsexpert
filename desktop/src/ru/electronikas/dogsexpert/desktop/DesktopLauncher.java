package ru.electronikas.dogsexpert.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import ru.electronikas.dogsexpert.DogsExpertGdxGame;
import ru.electronikas.dogsexpert.listeners.ImageChooseListener;
import ru.electronikas.dogsexpert.listeners.PlatformListener;

import java.util.Locale;

public class DesktopLauncher implements PlatformListener {
	public static void main (String[] arg) {
		DesktopLauncher desktopLauncher = new DesktopLauncher();
		Locale.setDefault(new Locale("ru", "RU"));
//		Locale.setDefault(new Locale("en", "US"));

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 500;
		config.height = 800;
		new LwjglApplication(new DogsExpertGdxGame(desktopLauncher), config);
	}

	@Override
	public void share() {

	}

	@Override
	public void rate() {

	}

	@Override
	public void getCameraSnapshot(ImageChooseListener imageChooseListener) {
	}

	@Override
	public void choosePictureFromDisk(ImageChooseListener imageChooseListener) {
		Texture txt = new Texture(new FileHandle("data/undefined1.jpg"));
		if(!txt.getTextureData().isPrepared()) {
			txt.getTextureData().prepare();
		}
		imageChooseListener.onChooseImage(txt.getTextureData().consumePixmap());
	}

}
