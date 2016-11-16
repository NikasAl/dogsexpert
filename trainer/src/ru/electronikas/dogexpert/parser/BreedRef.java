package ru.electronikas.dogexpert.parser;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BreedRef {

    public String link;
    public String name;
    public String description;
    public List imgs = new ArrayList<Image>();

    public BreedRef() {
    }

    public void feel(HtmlCleaner cleaner) {
        try {
            TagNode node = cleaner.clean(new URL(this.link));
            TagNode tnode = (TagNode) node.evaluateXPath("//div/article/header/h1")[0];
            TagNode[] tnod = ((TagNode) node.evaluateXPath("//article/div[2]")[0]).getElementsByName("p", false);
            this.name = tnode.getText().toString();
            String description = "";
            for(TagNode pnode : tnod) {
                TagNode[] as = pnode.getElementsByName("a", true);
                if(as.length==0) {
                    description += "..." + pnode.getText();
                } else {
                    String picUrl = as[0].findElementByName("img", true).getAttributeByName("src");
                    try {
                        Image dogImg = ImageIO.read(new URL(picUrl));
                        this.imgs.add(dogImg);
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



}
