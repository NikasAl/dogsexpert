package ru.electronikas.dogsexpert.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import ru.electronikas.dogsexpert.Textures;


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
        butsMenu.background("bluepane-t");

        butsMenu.row().height(h).width(w - butW);
        butsMenu.add(createInfoBut()).width(h).height(h);
        butsMenu.add(createInfoBut()).width(h).height(h);
        butsMenu.add(createInfoBut()).width(h).height(h);
        butsMenu.add(createInfoBut()).width(h).height(h);

        stage.addActor(butsMenu);
    }


    private Actor createInfoBut() {
        ImageButton infoBut = new ImageButton(uiSkin.get("go-advice-but", ImageButton.ImageButtonStyle.class));
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
