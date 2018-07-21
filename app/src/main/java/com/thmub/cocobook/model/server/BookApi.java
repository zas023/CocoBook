package com.thmub.cocobook.model.server;

import com.thmub.cocobook.model.bean.BookDetailBean;
import com.thmub.cocobook.model.bean.packages.*;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zhouas666 on 18-1-23.
 */

public interface BookApi {

    /**====================书籍====================**/
    /**
     * 由性别推荐书籍
     * @param gender
     * @return
     */
    @GET("/book/recommend")
    Single<RecommendBookPackage> getRecommendBookPackage(@Query("gender") String gender);

    /**
     * 由某一本书推荐书籍
     * @param bookId
     * @return
     */
    @GET("/book/{bookId}/recommend")
    Single<RecommendBookPackage2> getRecommendBookPackageByBookId(@Path("bookId") String bookId);


    /**
     * 获取书籍的章节总列表
     * @param bookId
     * @param view 默认参数为:chapters
     * @return
     */
    @GET("/mix-atoc/{bookId}")
    Single<BookChapterPackage> getBookChapterPackage(@Path("bookId") String bookId, @Query("view") String view);

    /**
     * 章节的内容
     * 这里采用的是同步请求。
     * @param url
     * @return
     */
    @GET("http://chapter2.zhuishushenqi.com/chapter/{url}")
    Single<ChapterInfoPackage> getChapterInfoPackage(@Path("url") String url);


    /*******************************精选*******************************************************/

    @GET("/recommendPage/node/spread/575f74f27a4a60dc78a435a3?pl=ios")
    Single<SwipePicturePackage> getSwipePicturePackage();

    @GET("/recommendPage/nodes/5910018c8094b1e228e5868f")
    Single<FeaturePackage> getFeaturePackage();

    @GET("/recommendPage/books/{nodeId}")
    Single<FeatureBookPackage> getFeatureBookPackage(@Path("nodeId")String nodeId);

    @GET("/recommendPage/node/books/all/{nodeId}")
    Single<FeatureDetailPackage> getFeatureDetailPackage(@Path("nodeId")String nodeId);


    /**********************************排行榜****************************************************/

    /**
     * 获取所有排行榜
     *
     * @return
     */
    @GET("/ranking/gender")
    Single<BillboardPackage> getBillboardPackage();

    /**
     * 获取单一排行榜
     * 周榜：rankingId-> _id
     * 月榜：rankingId-> monthRank
     * 总榜：rankingId-> totalRank
     *
     * @return
     */
    @GET("/ranking/{rankingId}")
    Single<BillBookPackage> getBillBookPackage(@Path("rankingId") String rankingId);


    /*******************************分类***************************************/
    /**
     * 获取分类
     *
     * @return
     */
    @GET("/cats/lv2/statistics")
    Single<BookSortPackage> getBookSortPackage();

    /**
     * 获取二级分类
     *
     * @return
     */
    @GET("/cats/lv2")
    Single<BookSubSortPackage> getBookSubSortPackage();

    /**
     * 按分类获取书籍列表
     *
     * @param gender male、female
     * @param type   hot(热门)、new(新书)、reputation(好评)、over(完结)
     * @param major  玄幻
     * @param minor  东方玄幻、异界大陆、异界争霸、远古神话
     * @param limit  50
     * @return
     */
    @GET("/book/by-categories")
    Single<SortBookPackage> getSortBookPackage(@Query("gender") String gender, @Query("type") String type, @Query("major") String major, @Query("minor") String minor, @Query("start") int start, @Query("limit") int limit);

    /********************************主题书单**************************************8*/

    /**
     * 获取主题书单列表
     * 本周最热：duration=last-seven-days&sort=collectorCount
     * 最新发布：duration=all&sort=created
     * 最多收藏：duration=all&sort=collectorCount
     *
     * 如:http://api.zhuishushenqi.com/book-list?duration=last-seven-days&sort=collectorCount&start=0&limit=20&tag=%E9%83%BD%E5%B8%82&gender=male
     * @param tag    都市、古代、架空、重生、玄幻、网游
     * @param gender male、female
     * @param limit  20
     * @return
     */
    @GET("/book-list")
    Single<BookListPackage> getBookListPackage(@Query("duration") String duration, @Query("sort") String sort,
                                               @Query("start") String start, @Query("limit") String limit,
                                               @Query("tag") String tag, @Query("gender") String gender);

    /**
     * 获取主题书单标签列表
     *
     * @return
     */
    @GET("/book-list/tagType")
    Single<BookTagPackage> getBookTagPackage();

    /**
     * 获取书单详情
     *
     * @return
     */
    @GET("/book-list/{bookListId}")
    Single<BookListDetailPackage> getBookListDetailPackage(@Path("bookListId") String bookListId);


    /*************************书籍详情**********************************/
    /**
     * 书籍推荐书单
     * @param bookId
     * @param limit
     * @return
     */
    @GET("/book-list/{bookId}/recommend")
    Single<RecommendBookListPackage> getRecommendBookListPackage(@Path("bookId") String bookId, @Query("limit") String limit);

    /**
     * 书籍详情
     * @param bookId
     * @return
     */
    @GET("/book/{bookId}")
    Single<BookDetailBean> getBookDetail(@Path("bookId") String bookId);

    /**
     * 根据书籍的 Tag 进行检索
     * @param tags
     * @param start
     * @param limit
     * @return
     */
    @GET("/book/by-tags")
    Single<TagSearchPackage> getTagSearchPackage(@Query("tags") String tags, @Query("start") String start, @Query("limit") String limit);


    /************************************搜索书籍******************************************************/
    @GET("/book/hot-word")
    Single<HotWordPackage> getHotWordPackage();

    /**
     * 关键字自动补全
     *
     * @param query
     * @return
     */
    @GET("/book/auto-complete")
    Single<KeyWordPackage> getKeyWordPacakge(@Query("query") String query);

    /**
     * 书籍查询
     *
     * @param query:作者名或者书名
     * @return
     */
    @GET("/book/fuzzy-search")
    Single<SearchBookPackage> getSearchBookPackage(@Query("query") String query);
}
