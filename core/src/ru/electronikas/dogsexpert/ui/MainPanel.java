package ru.electronikas.dogsexpert.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.electronikas.dogsexpert.Assets;
import ru.electronikas.dogsexpert.DogsExpertGdxGame;
import ru.electronikas.dogsexpert.Textures;
import ru.electronikas.dogsexpert.listeners.ImageChooseListener;
import ru.electronikas.dogsexpert.neural.processing.NeuralProcessor;

import static ru.electronikas.dogsexpert.Utils.maxScale;
import static ru.electronikas.dogsexpert.Utils.textSizeTuning;

/**
 * Created by navdonin on 03/01/15.
 */
public class MainPanel {
    Table butsMenu;
    Skin uiSkin;
    float butW = 0;
    float h = 0;
    private Stage stage;
    private NeuralProcessor processor;

    private BreedsPanel breedsPanel;

    public MainPanel(Stage stage, NeuralProcessor processor) {
        this.stage = stage;
        this.processor = processor;
        float w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        butW = w / 8f;
        float width = w - butW;

        uiSkin = Textures.getUiSkin();
        butsMenu = new Table(uiSkin);
        butsMenu.align(Align.topLeft);
        butsMenu.setPosition(butW / 2, h);
        butsMenu.setWidth(width);
        butsMenu.setHeight(h);
//        breedsPanel.background("bluepane-t");

        butsMenu.row().height(h / 10).width(width);
        butsMenu.add(createHeader(w - butW));

        butsMenu.row().height(h / 3).width(width);
        butsMenu.add(createImage());

        butsMenu.row().height(h / 10).padTop(h/40);
        butsMenu.add(pictureButton(width)).width(width);

        butsMenu.row().height(h / 10).padTop(h/40);
        butsMenu.add(camShotButton()).width(width);

        butsMenu.row().height(h / 10).padTop(h/40);
        butsMenu.add(aboutButton()).width(width);

//        butsMenu.setDebug(true);

        stage.addActor(butsMenu);
        breedsPanel = new BreedsPanel(stage);
    }

    Image image;

    private Image createImage() {
        Texture texture = new Texture("data/0.jpg");
        image = new Image(texture);
//        float scl = image.getWidth() / image.getHeight();
//        image.setWidth(Gdx.graphics.getWidth());
//        image.setHeight(Gdx.graphics.getWidth() / scl);
        return image;
    }


    private TextButton aboutBut;
    private Actor aboutButton() {
        aboutBut = new TextButton(Assets.bdl().get("aboutBut"),
                uiSkin.get("red-but", TextButton.TextButtonStyle.class));
        aboutBut.getLabel().setFontScale(maxScale);
        aboutBut.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
//                animateHide();
            }
        });
        return aboutBut;
    }

    TextButton camShotBut;
    private Actor camShotButton() {
        camShotBut = new TextButton(Assets.bdl().get("camShotBut"),
                uiSkin);
        camShotBut.getLabel().setFontScale(maxScale);
        camShotBut.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                DogsExpertGdxGame.game.platformListener.getCameraSnapshot(imgChooseListener);
            }
        });
        return camShotBut;
    }

    TextButton choosePhotoBut;
    private Actor pictureButton(float width) {
        choosePhotoBut = new TextButton(Assets.bdl().get("ChoosePhotoBut"),
                uiSkin);
        textSizeTuning(choosePhotoBut.getLabel(), width);
        choosePhotoBut.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                DogsExpertGdxGame.game.platformListener.choosePictureFromDisk(imgChooseListener);
            }
        });
        return choosePhotoBut;
    }

    private void buttonsEnable(boolean isEnabled) {
        if(isEnabled) {
            choosePhotoBut.setDisabled(false);
            choosePhotoBut.setVisible(true);
            camShotBut.setDisabled(false);
            camShotBut.setVisible(true);
            aboutBut.setDisabled(false);
            aboutBut.setVisible(true);
        } else {
            choosePhotoBut.setDisabled(true);
            choosePhotoBut.setVisible(false);
            camShotBut.setDisabled(true);
            camShotBut.setVisible(false);
            aboutBut.setDisabled(true);
            aboutBut.setVisible(false);
        }
    }

    ImageChooseListener imgChooseListener = new ImageChooseListener() {
        @Override
        public void onChooseImage(final Pixmap pixmap) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    TextureRegionDrawable rrr = new TextureRegionDrawable();
                    rrr.setRegion(new TextureRegion(new Texture(pixmap)));
                    image.setDrawable(new TextureRegionDrawable(rrr));
                    buttonsEnable(false);
                    breedsPanel.runAnimOfAnalysis(processor.processWhatIs(pixmap));
                }
            });
        }
    };

    private Actor createHeader(float width) {
        Label headLabel =  new Label(Assets.bdl().get("mainLabel"), uiSkin);
        headLabel.setAlignment(Align.center);
        textSizeTuning(headLabel, width);
        return headLabel;
    }

    public void animateHide() {
        MoveToAction action = Actions.moveTo(butW / 2, h);
        action.setDuration(0.5f);
        butsMenu.addAction(action);
    }

    public void animateOpen() {
        MoveToAction action = Actions.moveTo(butW / 2, 0);
        action.setDuration(0.5f);
        butsMenu.addAction(action);

    }

    public void reset() {
        breedsPanel.animateHide();
        breedsPanel.reset();

        buttonsEnable(true);
    }
}
