package ru.electronikas.dogsexpert;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by nikas on 6/22/16.
 */
public class Utils {

    public static void cellTextSizeTuning(Label nameLabel, float width) {
        String saveStr = nameLabel.getText().toString();
        nameLabel.setText("1024");

        float maxScale = 3f;
        nameLabel.setFontScale(maxScale);
        while (nameLabel.getPrefWidth() > width / 1.4f) {
            maxScale -= 0.1f;
            nameLabel.setFontScale(maxScale);
            nameLabel.layout();
        }

        nameLabel.setText(saveStr);

        Gdx.app.log("FONT", "calibrated maxScale: " + maxScale);
    }

    public static void textSizeTuning(Label nameLabel, float width) {
        float maxScale = 3f;
        nameLabel.setFontScale(maxScale);
        while (nameLabel.getPrefWidth() > width / 2f) {
            maxScale -= 0.1f;
            nameLabel.setFontScale(maxScale);
            nameLabel.layout();
        }

        Gdx.app.log("FONT", "calibrated maxScale: " + maxScale);
    }

    public static float textSizeTuning(Label nameLabel, float width, int widthPercent) {
        float maxScale = 3f;
        nameLabel.setFontScale(maxScale);
        while (nameLabel.getPrefWidth() > (width / 100) * widthPercent) {
            maxScale -= 0.1f;
            nameLabel.setFontScale(maxScale);
            nameLabel.layout();
        }

        return maxScale;
    }

    public static Color getRandomColor() {
        int i = MathUtils.random(0,10);
        switch (i){
            case 0: return Color.BLUE;
            case 1: return Color.MAROON;
            case 2: return Color.CLEAR;
            case 3: return Color.CYAN;
            case 4: return Color.OLIVE;
            case 5: return Color.PINK;
            case 6: return Color.PURPLE;
            case 7: return Color.GREEN;
            case 8: return Color.NAVY;
            case 9: return Color.MAGENTA;
            case 10: return Color.ORANGE;
        }
        return Color.TEAL;
    }

}
