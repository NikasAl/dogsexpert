package ru.electronikas.dogsexpert.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import ru.electronikas.dogsexpert.DogsExpertGdxGame;
import ru.electronikas.dogsexpert.Textures;
import ru.electronikas.dogsexpert.Utils;


/**
 * User: nikas
 * Date: 19.10.13
 */
public class ButtonsPanel {
    private Stage stage;
    Label label;
    Skin uiSkin;
    private float butW = 0;
    private float h = 0;
    private float w = 0;
    private float hidePos = 0;

    Table butsMenu;

    public ButtonsPanel(Stage stage) {
        this.stage = stage;
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight() / 10;
        hidePos = Gdx.graphics.getHeight() + h;
        butW = w / 8f;
        uiSkin = Textures.getUiSkin();

        butsMenu = new Table(uiSkin);
        butsMenu.align(Align.topLeft);
        butsMenu.setPosition(butW / 2, hidePos);
        butsMenu.setWidth(w - butW);
        butsMenu.setHeight(h);
        butsMenu.background("bluepane");

        butsMenu.row().height(h).width(w - butW);
        butsMenu.add(createInfoBut()).width(h).height(h);
        butsMenu.add(createDetailsLabel(w - butW - h*2.5f)).align(Align.center).width(w - butW - h*2.5f).height(h);
        butsMenu.add(createCloseBut()).width(h).height(h);

//        butsMenu.setDebug(true);

        stage.addActor(butsMenu);
    }

    private Actor createCloseBut() {
        ImageButton closeBut = new ImageButton(uiSkin.get("close-but", ImageButton.ImageButtonStyle.class));
        closeBut.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                DogsExpertGdxGame.game.reset();
            }
        });


        return closeBut;
    }

    private Actor createDetailsLabel(float width) {
        Label details =  new Label(" ", uiSkin);
        details.setAlignment(Align.center);
        Utils.textSizeTuning(details, width, 80);
        return details;
    }


    private Actor createInfoBut() {
        ImageButton infoBut = new ImageButton(uiSkin.get("help-but", ImageButton.ImageButtonStyle.class));
        return infoBut;
    }

    public void animateHide() {
        MoveToAction action = Actions.moveTo(butW / 2, hidePos);
        action.setDuration(0.5f);
        butsMenu.addAction(action);
    }

    public void animateOpen() {
        MoveToAction action = Actions.moveTo(butW / 2, Gdx.graphics.getHeight() - h);
        action.setDuration(0.5f);
        butsMenu.addAction(action);

    }

}
