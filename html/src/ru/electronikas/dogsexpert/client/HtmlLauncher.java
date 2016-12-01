package ru.electronikas.dogsexpert.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import ru.electronikas.dogsexpert.DogsExpertGdxGame;
import ru.electronikas.dogsexpert.listeners.ImageChooseListener;
import ru.electronikas.dogsexpert.listeners.PlatformListener;

public class HtmlLauncher extends GwtApplication implements PlatformListener {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new DogsExpertGdxGame(this);
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

        @Override
        public void choosePictureFromDisk(ImageChooseListener imageChooseListener) {

        }
}