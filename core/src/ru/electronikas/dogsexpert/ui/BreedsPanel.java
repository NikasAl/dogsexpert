package ru.electronikas.dogsexpert.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import ru.electronikas.dogsexpert.Assets;
import ru.electronikas.dogsexpert.Textures;
import ru.electronikas.dogsexpert.Utils;
import ru.electronikas.dogsexpert.neural.Breed;

import java.util.ArrayList;

/**
 * Created by navdonin on 03/01/15.
 */
public class BreedsPanel {
    private final ButtonsPanel buttonsPanel;
    private TextProgressBar progressBar;
    Table breedsPanel;
    Table scrollBreedsPanel;
    Skin uiSkin;
    float butW = 0;
    float h = 0;
    float w = 0;
    float imgSize = 0;
    private Stage stage;
    ScrollPane settigsScroll;

    public BreedsPanel(Stage stage) {
        this.stage = stage;
        w = Gdx.graphics.getWidth();
        h = 8 * Gdx.graphics.getHeight() / 15;
        imgSize = h / 3;

        butW = w / 8f;

        uiSkin = Textures.getUiSkin();
        breedsPanel = new Table(uiSkin);
        breedsPanel.align(Align.topLeft);
        breedsPanel.setPosition(butW / 2, -h);
        breedsPanel.setWidth(w - butW);
        breedsPanel.setHeight(h);
//        aboutPanel.background("bluepane-t");

        settigsScroll = new ScrollPane(initialScrollTable(), uiSkin);
//        scroll.setScale(0.95f);
        settigsScroll.setScrollingDisabled(true, false);
        breedsPanel.add(settigsScroll);

//        aboutPanel.row().height(h).width(w - butW);
//        aboutPanel.add();

//        aboutPanel.setDebug(true);

        stage.addActor(breedsPanel);

        buttonsPanel = new ButtonsPanel(stage);
    }

    private Actor initialScrollTable() {
        scrollBreedsPanel = new Table(uiSkin);
        scrollBreedsPanel.align(Align.topLeft);
        scrollBreedsPanel.setPosition(butW / 2, -h);
        scrollBreedsPanel.setWidth(w - butW);
        scrollBreedsPanel.setHeight(h);

        scrollBreedsPanel.row().height(h / 10).width(w - butW).pad(h/40);
        Label header = new Label(Assets.bdl().get("itReminds"), uiSkin);
        Utils.textSizeTuning(header, w - butW, 90);
        scrollBreedsPanel.add(header).left().colspan(2);

        return scrollBreedsPanel;
    }

    private TextProgressBar createProgressBar() {
        TextProgressBar progressBar = new TextProgressBar(Assets.bdl().get("breedRecog"),
                0, Breed.size(), 1f, false, Textures.getUiSkin().get(ProgressBar.ProgressBarStyle.class), Textures.getUiSkin().get(Label.LabelStyle.class));
        progressBar.setAnimateDuration(0);
        progressBar.setValue(0);
        return progressBar;
    }

//    private Image createImage() {
//        Texture texture = new Texture("data/0.jpg");
//        image = new Image(texture);
//        return image;
//    }


    public void animateHide() {
        buttonsPanel.animateHide();

        MoveToAction action = Actions.moveTo(butW / 2, -h);
        action.setDuration(0.5f);
        breedsPanel.addAction(action);
    }

    public void animateOpen() {
        MoveToAction action = Actions.moveTo(butW / 2, 0);
        action.setDuration(0.5f);
        breedsPanel.addAction(action);

    }


    public int i = 0;
    public void runAnimOfAnalysis(final ArrayList<Breed> breeds) {
        progressBar = createProgressBar();
        progressBar.setPosition(butW / 2, h*0.9f);
        progressBar.setWidth(w - butW);
        stage.addActor(progressBar);

        for(int i=0; i < Breed.size(); i++) {
            addBreedToScrollPanel(breeds.get(i));
        }

        iterateProgressBar(breeds);
    }

    private void iterateProgressBar(final ArrayList<Breed> breeds) {
        Timer.schedule(new Timer.Task(){
                           @Override
                           public void run() {

                               if(i == breeds.size()) {
                                   progressBar.remove();
                                   this.cancel();
                                   animateOpen();
                                   buttonsPanel.animateOpen();
                                   i=0;
                                   return;
                               }
                               progressBar.setValue(i++);
                           }
                       }
                , 0f      //    (delay)
                , 0.03f     //    (seconds)
                , Breed.size()
        );
    }


    private void addBreedToScrollPanel(Breed breed) {

        scrollBreedsPanel.row().height(imgSize).width(w - butW).pad(h/40);
        Image img = Assets.getBreedImage(breed.getImagePath());
        scrollBreedsPanel.add(img).width(imgSize);

        Label percent = new Label("" + breed.getPercent() + "%", uiSkin);
        if(breed.getPercent()>10) percent.setColor(Color.YELLOW);
        else percent.setColor(Color.MAROON);
        if(breed.getPercent()==100) percent.setColor(Color.WHITE);
//        percent.setFontScale(0.5f);
        scrollBreedsPanel.add(percent);
    }

    public void reset() {
//        scrollAboutPanel.reset();
        settigsScroll.clearChildren();
        settigsScroll.setWidget(initialScrollTable());
        settigsScroll.setScrollingDisabled(true, false);
    }
}
