package com.thmub.cocobook.model.bean.packages;

import com.thmub.cocobook.model.bean.BookChapterBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-24
 * Github: https://github.com/zas023
 */
public class BookAtocChapterPackage {

    /**
     * _id : 5b2a0cb3c22a4001003e4e56
     * link : http://book.my716.com/getBooks.aspx?method=chapterList&bookId=2435467
     * source : my176
     * name : 176小说
     * book : 5b2a0cb337b772524b7a45fc
     * chapters : [{"title":"序","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374151_1608_1.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第一案　后窗血影","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374151_6778_2.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第二案　夜半枪声","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374151_0188_3.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第三案　幽绿巨人","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374152_1288_4.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第四案　夺面老屋","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374152_0482_5.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第五案　深山屠戮","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374152_6425_6.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第六案　月下花魂","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374152_0877_7.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第七案　古墓戾影","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374152_6769_8.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第八案　地狱旅馆","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374152_8068_9.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第九案　死不瞑目","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374151_8253_10.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第十案　车尾游魂","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374151_7328_11.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"第十一案　命丧风尘","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374151_7990_12.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false},{"title":"尾声　黎明之战","link":"http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374151_5964_13.txt","time":0,"chapterCover":"","totalpage":0,"partsize":0,"order":0,"currency":0,"unreadble":false,"isVip":false}]
     * updated : 2018-06-20T08:13:39.496Z
     * host : book.my716.com
     */

    private String _id;
    private String link;
    private String source;
    private String name;
    private String book;
    private String updated;
    private String host;
    private List<BookChapterBean> chapters;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<BookChapterBean> getChapters() {
        return chapters;
    }

    public void setChapters(List<BookChapterBean> chapters) {
        this.chapters = chapters;
    }

//    public static class ChaptersBean {
//        /**
//         * title : 序
//         * link : http://book.my716.com/getBooks.aspx?method=content&bookId=2435467&chapterFile=U_2435467_201806201610374151_1608_1.txt
//         * time : 0
//         * chapterCover :
//         * totalpage : 0
//         * partsize : 0
//         * order : 0
//         * currency : 0
//         * unreadble : false
//         * isVip : false
//         */
//
//        private String title;
//        private String link;
//        private int time;
//        private String chapterCover;
//        private int totalpage;
//        private int partsize;
//        private int order;
//        private int currency;
//        private boolean unreadble;
//        private boolean isVip;
//    }
}
