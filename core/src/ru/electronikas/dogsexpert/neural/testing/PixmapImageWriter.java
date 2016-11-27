package ru.electronikas.dogsexpert.neural.testing;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

/**
 * Created by nikas on 11/27/16.
 */
public class PixmapImageWriter {

    private static FileHandle testingDir;

    private static FileHandle getTestingDir() {
        if(testingDir==null)
            testingDir = new FileHandle("./TESTING");
        if(!testingDir.exists())
            testingDir.mkdirs();
        return testingDir;
    }

    public static void saveTestPixmapToPNG(int[] result, int width, int height) {

        Pixmap pxm = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        int count = 0;
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                double r = result[count] >> 24 & 0xff; r /= 255;// r += 1; r *= 127;
                double g = result[count] >> 16 & 0xff; g /= 255;// g += 1; g *= 127;
                double b = result[count] >> 8  & 0xff; b /= 255;// b += 1; b *= 127;
                count++;
                pxm.drawPixel(x, y, Color.rgba8888((float)r,(float)g,(float)b,1f));
            }
        }

        writePixmapIntoTestDir(pxm);
    }

    public static void saveTestPixmapToPNG(double[] result, int width, int height) {

        Pixmap pxm = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        int count = 0;
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                double r = result[count++]; r /= 255;// r += 1; r *= 127;
                double g = result[count++]; g /= 255;// g += 1; g *= 127;
                double b = result[count++]; b /= 255;// b += 1; b *= 127;
                pxm.drawPixel(x, y, Color.rgba8888((float)r,(float)g,(float)b,1f));
            }
        }

        writePixmapIntoTestDir(pxm);
    }

    private static void writePixmapIntoTestDir(Pixmap pxm) {
        FileHandle testingDir = getTestingDir();
        long l = testingDir.list().length;
        FileHandle newPNGFile = testingDir.child("fromPixmap" + l + ".png");
        PixmapIO.writePNG(newPNGFile, pxm);
    }

}
