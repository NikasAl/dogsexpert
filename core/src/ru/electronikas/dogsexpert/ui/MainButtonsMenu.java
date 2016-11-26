package ru.electronikas.dogsexpert.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import ru.electronikas.dogsexpert.Assets;
import ru.electronikas.dogsexpert.DogsExpertGdxGame;
import ru.electronikas.dogsexpert.Textures;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.electronikas.dogsexpert.Utils.textSizeTuning;

/**
 * Created by navdonin on 03/01/15.
 */
public class MainButtonsMenu {
    Table butsMenu;
    Skin uiSkin;
    float butW = 0;
    float h = 0;
    private Stage stage;

    public MainButtonsMenu(Stage stage) {
        this.stage = stage;
        float w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        butW = w / 8f;

        uiSkin = Textures.getUiSkin();
        butsMenu = new Table(uiSkin);
        butsMenu.align(Align.topLeft);
        butsMenu.setPosition(butW / 2, h);
        butsMenu.setWidth(w - butW);
        butsMenu.setHeight(h / 3);
//        butsMenu.background("bluepane-t");

        butsMenu.row().height(h / 10).width(w - butW);
        butsMenu.add(createHeader(w - butW));

        butsMenu.row().height(h / 10);
        butsMenu.add(camShotButton(butW * 4f)).pad(10).width(butW * 4f);

        butsMenu.row().height(h / 10);
        butsMenu.add(pictureButton(butW * 4f)).pad(10).width(butW * 4f);

//        butsMenu.setDebug(true);

        stage.addActor(butsMenu);

        createImage();
    }

    private void createImage() {
        Texture texture = new Texture("data/0.jpg");
        Image image = new Image(texture);
        float scl = image.getWidth() / image.getHeight();
        image.setWidth(Gdx.graphics.getWidth());
        image.setHeight(Gdx.graphics.getWidth() / scl);
        stage.addActor(image);
        image.addAction(Actions.moveTo(0,Gdx.graphics.getHeight() / scl,0.5f));

    }

    private Actor camShotButton(float width) {
        TextButton camShotBut = new TextButton(Assets.bdl().get("camShotBut"),
                uiSkin.get("green-but", TextButton.TextButtonStyle.class));
        textSizeTuning(camShotBut.getLabel(), width);
        camShotBut.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
//                animateHide();
                BufferedImage ri = DogsExpertGdxGame.game.platformListener.getCameraSnapshot();
                try {
                    ImageIO.write(ri, "PNG", new File("hello-world.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        return camShotBut;
    }

    private Actor pictureButton(float width) {
        TextButton ChoosePhotoBut = new TextButton(Assets.bdl().get("ChoosePhotoBut"),
                uiSkin.get("green-but", TextButton.TextButtonStyle.class));
        textSizeTuning(ChoosePhotoBut.getLabel(), width);
        ChoosePhotoBut.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                BufferedImage ri = DogsExpertGdxGame.game.platformListener.getPictureFromDisk();

            }
        });
        return ChoosePhotoBut;
    }

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
        MoveToAction action = Actions.moveTo(butW / 2, h/3);
        action.setDuration(0.5f);
        butsMenu.addAction(action);

    }
}
