package ru.electronikas.dogsexpert.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.electronikas.dogsexpert.Assets;
import ru.electronikas.dogsexpert.Textures;


/**
 * User: nikas
 * Date: 19.10.13
 */
public class LoadingPanel {

    private Stage stage;
    Label label;
//    Label progress;

    public LoadingPanel(Stage stage) {

        this.stage = stage;
        createLoadingText();
    }

    private void createLoadingText() {
        label = new Label(Assets.bdl().get("loading"), Textures.getUiSkin());//.get("h1-lbl", Label.LabelStyle.class));
        label.pack();
        label.setPosition((Gdx.graphics.getWidth() - label.getWidth()) / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(label);

/*
        progress = new Label("0%", Textures.getUiSkin());//.get("h1-lbl", Label.LabelStyle.class));
        progress.pack();
        progress.setPosition((Gdx.graphics.getWidth() - progress.getWidth()) / 2, Gdx.graphics.getHeight() / 3);
        progress.setFontScale(0.5f);
        stage.addActor(progress);
*/

    }

    public void stop() {
        stage.getRoot().removeActor(label);
//        stage.getRoot().removeActor(progress);
        stage.clear();
    }

/*    public void setProgress(float progress) {
        this.progress.setText(" Progress: " + progress + "%");
        Gdx.app.log("PROGRESS", "" + progress);
    }*/
}
