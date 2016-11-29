package ru.electronikas.dogsexpert;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import ru.electronikas.dogsexpert.listeners.ImageChooseListener;
import ru.electronikas.dogsexpert.listeners.PlatformListener;

import java.io.IOException;

public class AndroidLauncher extends AndroidApplication implements PlatformListener {
	private ImageChooseListener imageChooseListener;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new DogsExpertGdxGame(this), config);
	}

	@Override
	public void share() {

	}

	@Override
	public void rate() {

	}

	@Override
	public Image getCameraSnapshot() {
		return null;
	}

	private int PICK_IMAGE_REQUEST = 1;
	@Override
	public void choosePictureFromDisk(ImageChooseListener imageChooseListener) {
		this.imageChooseListener = imageChooseListener;

		Intent intent = new Intent();
// Show only images, no videos or anything else
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

//		Texture tex = new Texture(bitmap.getWidth(), bitmap.getHeight(), Pixmap.Format.RGBA8888);
//		return tex;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

			Uri uri = data.getData();

			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
				// Log.d(TAG, String.valueOf(bitmap));
				int[] pixels = new int[bitmap.getWidth()*bitmap.getHeight()];
				bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
				// Convert from ARGB to RGBA
				for (int i = 0; i< pixels.length; i++) {
					int pixel = pixels[i];
					pixels[i] = (pixel << 8) | ((pixel >> 24) & 0xFF);
				}
				Pixmap pixmap = new Pixmap(bitmap.getWidth(), bitmap.getHeight(), Pixmap.Format.RGBA8888);
				pixmap.getPixels().asIntBuffer().put(pixels);

				imageChooseListener.onChooseImage(pixmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
