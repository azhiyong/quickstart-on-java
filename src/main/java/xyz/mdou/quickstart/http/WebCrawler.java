package xyz.mdou.quickstart.http;

import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiyong.li
 * Date: 14-11-12.
 * Time: 上午10:31
 */
public class WebCrawler {

    private static List<String> urlList = new ArrayList<String>();

    public static void main(String[] args) {
        urlCrawl("http://www.dajie.com");
    }

    private static void urlCrawl(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("a[abs:href]");
            for (Element element : elements) {
                String linkUrl = element.getElementsByTag("a").attr("abs:href");
                if (linkUrl.startsWith("http")) {
                    if (urlList.contains(linkUrl)) {
                        continue;
                    } else {
                        urlList.add(linkUrl);
                    }
                    System.out.println(linkUrl);
                    urlCrawl(linkUrl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void httpRequest(String url) {
        try {
            Request.Get(url).execute().returnContent();

            Request.Post(url).bodyForm(Form.form().add("username", "vip").add("password", "secret").build()).execute()
                    .handleResponse(httpResponse -> {
                        int statusCode = httpResponse.getStatusLine().getStatusCode();
                        HttpEntity entity = httpResponse.getEntity();
                        BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
                        StringBuilder content = new StringBuilder();
                        String strLine;
                        while ((strLine = in.readLine()) != null) {
                            content.append(strLine).append("\r\n");
                        }
                        return new ResponseModel(statusCode, content.toString());
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    static class ResponseModel {
        int statusCode;
        String content;

        ResponseModel(int statusCode, String content) {
            this.statusCode = statusCode;
            this.content = content;
        }
    }
}
