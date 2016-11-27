package ru.electronikas.dogsexpert.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.sarxos.webcam.Webcam;
import ru.electronikas.dogsexpert.DogsExpertGdxGame;
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
	public Image getCameraSnapshot() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
//		ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
//		return webcam.getImage();
		return null;
	}

	@Override
	public Image getPictureFromDisk() {
		Texture txt = new Texture(new FileHandle("hello-word.png"));
		Image img = new Image(txt);
		return img;
	}
}
