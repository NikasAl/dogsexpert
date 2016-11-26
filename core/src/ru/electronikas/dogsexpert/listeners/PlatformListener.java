package ru.electronikas.dogsexpert.listeners;

import java.awt.image.BufferedImage;

/**
 * Created by nikas on 7/1/16.
 */
public interface PlatformListener {
    void share();
    void rate();

    BufferedImage getCameraSnapshot();
    BufferedImage getPictureFromDisk();
}
