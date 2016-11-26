package ru.electronikas.dogsexpert;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.electronikas.dogsexpert.listeners.PlatformListener;
import ru.electronikas.dogsexpert.ui.MainButtonsMenu;

public class DogsExpertGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Stage stage;
	public PlatformListener platformListener;
	public static DogsExpertGdxGame game;


	public DogsExpertGdxGame(PlatformListener platformListener) {
		this.platformListener = platformListener;
	}

	@Override
	public void create () {
		game = this;
		batch = new SpriteBatch();
		Viewport viewport = new ScreenViewport();
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true); //false

		stage = new Stage(viewport, batch);

		Gdx.input.setInputProcessor(stage);

//		stage.setDebugAll(true);
		new MainButtonsMenu(stage).animateOpen();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.3f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
		stage.act();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		stage.dispose();
	}
}
