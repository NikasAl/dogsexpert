package ru.electronikas.dogexpert.parser;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DogsHtmlParser {
    public final String BEGIN_URL_PART = "http://www.zoopicture.ru";

    private final String BROWSER_INFO = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

//    DateFormat sdf = new SimpleDateFormat("d.M.y hh:mm");

    private HtmlCleaner cleaner;

    public DogsHtmlParser() {
        this.cleaner = new HtmlCleaner();
        System.setProperty("http.agent", BROWSER_INFO);
    }

    public List<BreedRef> getBreeds() throws IOException {
        return getBreedLinks(BEGIN_URL_PART + "/porody-sobak");
    }

    private List<BreedRef> getBreedLinks(String urlStr) throws IOException {
        List<BreedRef> artRefList = new ArrayList<BreedRef>();
        try {
            TagNode node = cleaner.clean(new URL(urlStr));
//            TagNode tnode = (TagNode) node.evaluateXPath("//article/div[1]/div/div[1]")[0];
            List<TagNode> tagNodes = (List<TagNode>)node.getElementListByAttValue("class", "span2", true, true);
//            for (TagNode tn : tnode.getChildTags()) {
            for (TagNode tn : tagNodes) {
                BreedRef breed = new BreedRef();
                if(tn.evaluateXPath("//a").length == 0) continue;
                String link = ((TagNode) tn.evaluateXPath("//a")[0]).getAttributeByName("href");
                if(!link.contains("zoopicture.ru")) continue;
                breed.link = link;
                breed.identity = link.split("/")[3];

                artRefList.add(breed);
                System.out.println("link added: " + breed.link);
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return artRefList;
    }

    public static void main(String[] args) throws IOException {

        DogsHtmlParser parser = new DogsHtmlParser();
        List<BreedRef> breedRefs = parser.getBreeds();
        parser.feelBreeds(breedRefs);
        saveBreeds(breedRefs);

        System.out.print(breedRefs);
    }

    private static void saveBreeds(List<BreedRef> breedRefs) throws IOException {
        for(BreedRef breed : breedRefs) {
            breed.save();
        }
    }

    public void feelBreeds(List<BreedRef> breedRefs) {
        for (BreedRef breed : breedRefs) {
            breed.feel(cleaner);
        }
    }

}
