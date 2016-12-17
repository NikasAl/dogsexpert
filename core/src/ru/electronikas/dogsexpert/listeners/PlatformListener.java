package ru.electronikas.dogsexpert.listeners;

/**
 * Created by nikas on 7/1/16.
 */
public interface PlatformListener {
    void share();
    void rate();

    void getCameraSnapshot(ImageChooseListener imageChooseListener);
    void choosePictureFromDisk(ImageChooseListener imageChooseListener);
}
