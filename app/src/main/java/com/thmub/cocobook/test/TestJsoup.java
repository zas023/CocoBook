package com.thmub.cocobook.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.seimicrawler.xpath.JXDocument;

import java.io.IOException;
import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-06
 * Github: https://github.com/zas023
 */
public class TestJsoup {
    public static void main(String[] args) {
        String url = "https://www.biquge5.com/7_7397/2765210.html";
//        String html = null;
//        try {
//            html = Jsoup.connect(url).get().html();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(html);

        String xpath="//div[@id='content']/text()";
        JXDocument jxDocument = JXDocument.create(url);
        List<Object> rs = jxDocument.sel(xpath);
//        for (Object o:rs){
//            if (o instanceof Element){
//                int index = ((Element) o).siblingIndex();
//                System.out.println(index);
//            }
//            System.out.println(o.toString());
//        }
        System.out.println(jxDocument.toString());
        System.out.println(jxDocument.sel(xpath));
    }
}
