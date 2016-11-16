package ru.electronikas.dogexpert.parser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyHtmlSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import ru.electronikas.nstarikov.model.ArticleRef;
import ru.electronikas.nstarikov.ssl.CustomHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DogsHtmlParser {
    public static final int TIMEOUT = 30000;
    public final String BEGIN_URL_PART = "http://nstarikov.ru/blog";

    private final String BROWSER_INFO = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

//    DateFormat sdf = new SimpleDateFormat("d.M.y hh:mm");

    private HtmlCleaner cleaner;

    public DogsHtmlParser() {
        this.cleaner = new HtmlCleaner();
        System.setProperty("http.agent", BROWSER_INFO);
    }

    public List<ArticleRef> getNewsByPage(String page) throws IOException {
        return getNews(BEGIN_URL_PART + "/page/" + page);
    }

    public List<ArticleRef> getLastNews() throws IOException {
        return getNews(BEGIN_URL_PART);
    }

    public String getArticleHtml(String allPageHtml) throws IOException {
        String article = null;

        try {
            TagNode node = cleaner.clean(allPageHtml);
            //List<TagNode> tn = node.getElementListByAttValue("class", "post_wrap",true,true); //evaluateXPath("//*[@id=\"content\"]/div[*]")[0]);
            TagNode tn = (TagNode) node.evaluateXPath("//*[@id=\"content\"]")[0];
            TagNode tnDiscus = (TagNode) node.evaluateXPath("//*[@id=\"disqus_thread\"]")[0];

//            filterUnneeded(node);


            PrettyHtmlSerializer prettyHtmlSerializer = new PrettyHtmlSerializer(cleaner.getProperties());
            article = prettyHtmlSerializer.getAsString(tn) + prettyHtmlSerializer.getAsString(tnDiscus);

        } catch (XPatherException e) {
            e.printStackTrace();
        }

        return article;
    }



    private List<ArticleRef> getNews(String urlStr) throws IOException {
        String htmlNewsList = getContentFromUrl(urlStr);

        return getArticleRefsByHtmlString(htmlNewsList);
    }

    private List<ArticleRef> getArticleRefsByHtmlString(String htmlStr) throws IOException {
        List<ArticleRef> artRefList = new ArrayList<ArticleRef>();
        try {
            TagNode node = cleaner.clean(htmlStr);
            TagNode tnode = (TagNode) node.evaluateXPath("//*[@id=\"content\"]/div[1]")[0];
//            TagNode tnode = node.findElementByName("article", true);

            //List<TagNode> tagNodes = node.getElementListByAttValue("class", "post wrap",true,true);
            for (TagNode tn : tnode.getChildTags()) {
                if (!tn.getName().equals("article")) continue;
                ArticleRef ar = new ArticleRef();
                ar.txtDate = ((TagNode) tn.evaluateXPath("//header")[0]).getText().toString();
                //((TagNode)tn.evaluateXPath("//div[1]/h3/span[1]")[0]).getText().toString();
                ar.length = "";
                ar.link = ((TagNode) tn.evaluateXPath("//a")[0]).getAttributeByName("href");
                ar.name = ((TagNode) tn.evaluateXPath("//a")[0]).getText().toString().replaceAll("&nbsp;", "").replaceAll("&mdash;", "").replaceAll("&laquo;","\"").replaceAll("&raquo;","\"");
                ar.author = "";//((TagNode)tn.evaluateXPath("//div[1]/h4[1]/a")[0]).getText().toString();

                artRefList.add(ar);
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return artRefList;
    }

    private void filterUnneeded(TagNode node) throws XPatherException {
        clearJScripts(node);
        clearVK(node);

    }

    private void clearVK(TagNode node) throws XPatherException {
        Object[] tnarr = node.evaluateXPath("//*[@class=\"vkbutton\"]");
        cleanArr(tnarr);
        tnarr = node.evaluateXPath("//*[@id=\"vk_like\"]");
        cleanArr(tnarr);
    }

    private void clearJScripts(TagNode node) {
        List<TagNode> ltn = (List<TagNode>) node.getElementListByName("script", true);
        for (TagNode tn : ltn) {
            tn.removeFromTree();
        }
        ltn = (List<TagNode>) node.getElementListByName("form", true);
        for (TagNode tn : ltn) {
            tn.removeFromTree();
        }
    }

    private void cleanArr(Object[] tnarr) {
        for (Object tn : tnarr) {
            ((TagNode) tn).removeFromTree();
        }
    }

/*

    public ArrayList<ArticleRef> fetchNewsList() throws IOException, FeedException {
        ArrayList<ArticleRef> messages = new ArrayList<ArticleRef>();
        URL feedUrl = new URL("http://feeds.feedburner.com/nstarikov?format=xml");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new InputStreamReader(feedUrl.openStream()));
//        feed.setEncoding("UTF-8");
        for (Object entry : feed.getEntries()) {
            ArticleRef articleRef = new ArticleRef();

            articleRef.author = ((SyndEntry) entry).getAuthor();
            articleRef.description = ((SyndEntry) entry).getDescription().getValue();
            articleRef.link = ((SyndEntry) entry).getLink();
            articleRef.name = ((SyndEntry) entry).getTitle();
            articleRef.txtDate = ((SyndEntry) entry).getPublishedDate().toString();
//            articleRef.content = getArticleContentFromUrl(articleRef.link);
//                SyndContentImpl rrr = ((SyndContentImpl)((SyndEntry)entry).getContents().get(0));//((SyndEntry)entry).getContents().get(0);
//                articleRef.content = rrr.getValue();
//            System.out.println(((SyndEntry) entry).getContents().size());

            messages.add(articleRef);
        }
        return messages;
    }
*/

    public String getArticleContentFromUrl(String url) {
        String resString;
        try {
            resString = getArticleHtml(getContentFromUrl(url));
        } catch (IOException e) {
            throw new RuntimeException("parser Error");
        }
        return resString;
    }

    public String getContentFromUrl(String url) {
        if(cook==null) authorize();
        String resString = null;
        resString = getHttpSourceFromUrl(url, cook);
        return resString;
    }

    String cook = null;


    HttpClient httpclient = new CustomHttpClient(); // Create HTTP Client

    private String getHttpSourceFromUrl(String url, String cookie) {
        HttpGet httpget = new HttpGet(url); // Set the action you want to do
        if(cookie!=null)
            httpget.addHeader("Cookie", cookie);
//        httpget.addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.2.1; ru-ru; A9500 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        httpget.addHeader("User-Agent", BROWSER_INFO);
        HttpResponse response = null; // Executeit
        String resString = null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent(); // Create an InputStream with the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) // Read line by line
                sb.append(line + "\n");

            resString = sb.toString(); // Result is here

            is.close(); // Close the stream
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resString;
    }


    public void authorize() {
        String html = getHttpSourceFromUrl("https://nstarikov.ru", null);
        if(html==null) return;
        int begin = html.indexOf("_ddn_intercept_2");
        if(begin==-1) {
            cook = "mobile1=123;";
            return;
        }
        int end = html.indexOf(";", begin);
        cook = html.substring(begin, end+1);
    }

    public static void main(String[] args) throws IOException {

        NStarikovHtmlParser parser = new NStarikovHtmlParser();
//        String art = parser.getArticleContentFromUrl("https://nstarikov.ru/blog/71082");
        List<ArticleRef> articleRefs = parser.getLastNews();
        System.out.print(articleRefs);
    }

}
