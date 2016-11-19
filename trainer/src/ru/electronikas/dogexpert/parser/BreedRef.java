package ru.electronikas.dogexpert.parser;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BreedRef {

    public String link;
    public String name;
    public String description;
    public List<Image> imgs = new ArrayList<Image>();
    public String identity;

    public BreedRef() {
    }

    public void feel(HtmlCleaner cleaner) {
        try {
            TagNode node = cleaner.clean(new URL(this.link));
            System.out.println("feeling link: " + link);
            TagNode tnode = (TagNode) node.evaluateXPath("//div/article/header/h1")[0];
            TagNode[] tnod = ((TagNode) node.evaluateXPath("//article/div[2]")[0]).getElementsByName("p", false);
            this.name = tnode.getText().toString();
            String description = "";
            for(TagNode pnode : tnod) {
                TagNode[] as = pnode.getElementsByName("a", true);
                if(as.length==0) {
                    description += "..." + pnode.getText();
                } else {
                    TagNode imgTag = as[0].findElementByName("img", true);
                    if(imgTag==null) continue;
                    String picUrl = imgTag.getAttributeByName("src");
                    try {
                        addImg(picUrl);
                    } catch (IOException e) {
                    }
                }
            }
            this.description = description;
        } catch (XPatherException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void save() throws IOException {
        System.out.println("Saving breed: " + name);
        createImageDirIfNeed();
        saveImages(imgs);
    }

    private void saveImages(List<Image> imgs) throws IOException {
        int count = new File(identity).listFiles().length;
        for(Image dogImg : imgs) {
            String imgPath = identity + "/" + count++ + ".jpg";
            ImageIO.write((RenderedImage) dogImg, "jpg",new File(imgPath));
            addClassifiedImgToTrainerProgram(imgPath, identity);
        }
    }

    private void addClassifiedImgToTrainerProgram(String imgPath, String identity) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dogTrainerFragment.cmd", true)));
        out.println("Input: image:./" + imgPath + ", identity:" + identity);
        out.close();
    }

    private void createImageDirIfNeed() {
        File theDir = new File(identity);
        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            }
            catch(SecurityException se){
            }
        }
    }

    public void addImg(String picUrl) throws IOException {
        try {
            Image dogImg = ImageIO.read(new URL(picUrl));
            if(dogImg == null) {
                System.out.println("dog image was null - skip it");
                return;
            }
            this.imgs.add(dogImg);
            System.out.println("dog image downloaded from: " + picUrl);
        } catch (IIOException ex) {
            System.out.println("can't download img from: " + picUrl);
        }
    }
}
