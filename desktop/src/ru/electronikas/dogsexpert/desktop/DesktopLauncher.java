package ru.electronikas.dogsexpert.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.sarxos.webcam.Webcam;
import ru.electronikas.dogsexpert.DogsExpertGdxGame;
import ru.electronikas.dogsexpert.listeners.PlatformListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
	public BufferedImage getCameraSnapshot() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
//		ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
		return webcam.getImage();
	}

	@Override
	public BufferedImage getPictureFromDisk() {
		try {
			return ImageIO.read(new File("hello-world.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
