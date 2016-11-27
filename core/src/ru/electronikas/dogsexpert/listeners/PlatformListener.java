package ru.electronikas.dogsexpert.listeners;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by nikas on 7/1/16.
 */
public interface PlatformListener {
    void share();
    void rate();

    Image getCameraSnapshot();
    Image getPictureFromDisk();
}
