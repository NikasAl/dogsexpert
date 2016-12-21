package ru.electronikas.dogsexpert.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import ru.electronikas.dogsexpert.Assets;
import ru.electronikas.dogsexpert.Textures;
import ru.electronikas.dogsexpert.Utils;
import ru.electronikas.dogsexpert.neural.Breed;

/**
 * Created by navdonin on 03/01/15.
 */
public class AboutPanel {
    private final ButtonsPanel buttonsPanel;
    Table aboutPanel;
    Table scrollAboutPanel;
    Skin uiSkin;
    float butW = 0;
    float h = 0;
    float w = 0;
    float imgSize = 0;
    private Stage stage;
    ScrollPane scroll;

    public AboutPanel(Stage stage) {
        this.stage = stage;
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        imgSize = h / 3;

        butW = w / 25f;

        uiSkin = Textures.getUiSkin();
        aboutPanel = new Table(uiSkin);
        aboutPanel.align(Align.topLeft);
        aboutPanel.setPosition(butW / 2, -h);
        aboutPanel.setWidth(w - butW);
        aboutPanel.setHeight(h);

        scroll = new ScrollPane(initialScrollTable(), uiSkin);
        scroll.setScrollingDisabled(true, false);
        aboutPanel.add(scroll);

//        aboutPanel.row().height(h).width(w - butW);
//        aboutPanel.add();

        aboutPanel.setDebug(true);

        stage.addActor(aboutPanel);

        buttonsPanel = new ButtonsPanel(stage);
    }

    float headFontScale;
    float textFontScale;
    private Actor initialScrollTable() {
        scrollAboutPanel = new Table(uiSkin);
        scrollAboutPanel.defaults().width(w - butW *3f);
        scrollAboutPanel.row().height(h / 20);
        Label header = new Label(Assets.bdl().get("aboutWhatIs"), uiSkin);
        header.setAlignment(Align.center);
        header.setColor(Color.GREEN);
        headFontScale = Utils.textSizeTuning(header, w - butW, 80);
        textFontScale = headFontScale - 0.05f;
        scrollAboutPanel.add(header).center();

        scrollAboutPanel.row().height(h / 4);
        Label description = new Label(Assets.bdl().get("aboutDescription"), uiSkin);
        description.setFontScale(textFontScale);
        description.setWrap(true);
        description.setAlignment(Align.top);
        scrollAboutPanel.add(description).top();

        scrollAboutPanel.row().height(h / 20);
        Label how = new Label(Assets.bdl().get("aboutHowDoesItWorks"), uiSkin);
        how.setAlignment(Align.center);
        how.setColor(Color.GREEN);
        how.setFontScale(headFontScale);
        scrollAboutPanel.add(how);

        scrollAboutPanel.row().height(h / 2.5f);
        Label works = new Label(Assets.bdl().format("aboutWorks", 79, 15), uiSkin);
        works.setFontScale(textFontScale);
        works.setWrap(true);
        works.setAlignment(Align.top);
        scrollAboutPanel.add(works);


        scrollAboutPanel.setDebug(true);

        return scrollAboutPanel;
    }

    public void animateHide() {
        buttonsPanel.animateHide();

        MoveToAction action = Actions.moveTo(butW / 2, -h);
        action.setDuration(0.5f);
        aboutPanel.addAction(action);
    }

    public void animateOpen() {
        MoveToAction action = Actions.moveTo(butW / 2, 0);
        action.setDuration(0.5f);
        aboutPanel.addAction(action);
    }

    private void addBreedToScrollPanel(Breed breed) {

        scrollAboutPanel.row().height(imgSize).width(w - butW).pad(h/40);
        Image img = Assets.getBreedImage(breed.getImagePath());
        scrollAboutPanel.add(img).width(imgSize);

        Label percent = new Label("" + breed.getPercent() + "%", uiSkin);
        if(breed.getPercent()>10) percent.setColor(Color.YELLOW);
        else percent.setColor(Color.MAROON);
        if(breed.getPercent()==100) percent.setColor(Color.WHITE);
//        percent.setFontScale(0.5f);
        scrollAboutPanel.add(percent);
    }

    public void reset() {
//        scrollAboutPanel.reset();
        scroll.clearChildren();
        scroll.setWidget(initialScrollTable());
        scroll.setScrollingDisabled(true, false);
    }
}
