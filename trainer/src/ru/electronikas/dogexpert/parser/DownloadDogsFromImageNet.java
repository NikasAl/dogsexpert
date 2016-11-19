package ru.electronikas.dogexpert.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by nikas on 11/18/16.
 */
public class DownloadDogsFromImageNet {

    static String currentCatalog = "";
    static BreedRef breedRef = new BreedRef();

    public static void main(String[] args) throws IOException {
        downloadURLsFromFiles(args[0]);
    }

    private static void downloadURLsFromFiles(String imgFileName) throws IOException {

        Stream<String> stream = Files.lines(Paths.get(imgFileName));
        stream.forEach(new Consumer<String>() {
            @Override
            public void accept(String line) {
                if(line.contains("catalog:")) setCurrentCatalog(line.split(":")[1]);
                else if(line.contains("http:")) downloadImgToBreedRef(line);
            }
        });

        breedRef.save();
    }

    private static void downloadImgToBreedRef(String picUrl) {
        try {
            breedRef.addImg(picUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void setCurrentCatalog(String catalog) {
        System.out.println("set catalog to: " + catalog);
        currentCatalog = catalog;
        breedRef.identity = catalog;
    }

}
