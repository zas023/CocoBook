package com.thmub.cocobook.model.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.thmub.cocobook.model.bean.AuthorBean;
import com.thmub.cocobook.model.bean.BookChapterBean;
import com.thmub.cocobook.model.bean.BookRecordBean;
import com.thmub.cocobook.model.bean.BookSearchBean;
import com.thmub.cocobook.model.bean.CollBookBean;
import com.thmub.cocobook.model.bean.DownloadTaskBean;

import com.thmub.cocobook.model.dao.AuthorBeanDao;
import com.thmub.cocobook.model.dao.BookChapterBeanDao;
import com.thmub.cocobook.model.dao.BookRecordBeanDao;
import com.thmub.cocobook.model.dao.BookSearchBeanDao;
import com.thmub.cocobook.model.dao.CollBookBeanDao;
import com.thmub.cocobook.model.dao.DownloadTaskBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig authorBeanDaoConfig;
    private final DaoConfig bookChapterBeanDaoConfig;
    private final DaoConfig bookRecordBeanDaoConfig;
    private final DaoConfig bookSearchBeanDaoConfig;
    private final DaoConfig collBookBeanDaoConfig;
    private final DaoConfig downloadTaskBeanDaoConfig;

    private final AuthorBeanDao authorBeanDao;
    private final BookChapterBeanDao bookChapterBeanDao;
    private final BookRecordBeanDao bookRecordBeanDao;
    private final BookSearchBeanDao bookSearchBeanDao;
    private final CollBookBeanDao collBookBeanDao;
    private final DownloadTaskBeanDao downloadTaskBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        authorBeanDaoConfig = daoConfigMap.get(AuthorBeanDao.class).clone();
        authorBeanDaoConfig.initIdentityScope(type);

        bookChapterBeanDaoConfig = daoConfigMap.get(BookChapterBeanDao.class).clone();
        bookChapterBeanDaoConfig.initIdentityScope(type);

        bookRecordBeanDaoConfig = daoConfigMap.get(BookRecordBeanDao.class).clone();
        bookRecordBeanDaoConfig.initIdentityScope(type);

        bookSearchBeanDaoConfig = daoConfigMap.get(BookSearchBeanDao.class).clone();
        bookSearchBeanDaoConfig.initIdentityScope(type);

        collBookBeanDaoConfig = daoConfigMap.get(CollBookBeanDao.class).clone();
        collBookBeanDaoConfig.initIdentityScope(type);

        downloadTaskBeanDaoConfig = daoConfigMap.get(DownloadTaskBeanDao.class).clone();
        downloadTaskBeanDaoConfig.initIdentityScope(type);

        authorBeanDao = new AuthorBeanDao(authorBeanDaoConfig, this);
        bookChapterBeanDao = new BookChapterBeanDao(bookChapterBeanDaoConfig, this);
        bookRecordBeanDao = new BookRecordBeanDao(bookRecordBeanDaoConfig, this);
        bookSearchBeanDao = new BookSearchBeanDao(bookSearchBeanDaoConfig, this);
        collBookBeanDao = new CollBookBeanDao(collBookBeanDaoConfig, this);
        downloadTaskBeanDao = new DownloadTaskBeanDao(downloadTaskBeanDaoConfig, this);

        registerDao(AuthorBean.class, authorBeanDao);
        registerDao(BookChapterBean.class, bookChapterBeanDao);
        registerDao(BookRecordBean.class, bookRecordBeanDao);
        registerDao(BookSearchBean.class, bookSearchBeanDao);
        registerDao(CollBookBean.class, collBookBeanDao);
        registerDao(DownloadTaskBean.class, downloadTaskBeanDao);
    }
    
    public void clear() {
        authorBeanDaoConfig.clearIdentityScope();
        bookChapterBeanDaoConfig.clearIdentityScope();
        bookRecordBeanDaoConfig.clearIdentityScope();
        bookSearchBeanDaoConfig.clearIdentityScope();
        collBookBeanDaoConfig.clearIdentityScope();
        downloadTaskBeanDaoConfig.clearIdentityScope();
    }

    public AuthorBeanDao getAuthorBeanDao() {
        return authorBeanDao;
    }

    public BookChapterBeanDao getBookChapterBeanDao() {
        return bookChapterBeanDao;
    }

    public BookRecordBeanDao getBookRecordBeanDao() {
        return bookRecordBeanDao;
    }

    public BookSearchBeanDao getBookSearchBeanDao() {
        return bookSearchBeanDao;
    }

    public CollBookBeanDao getCollBookBeanDao() {
        return collBookBeanDao;
    }

    public DownloadTaskBeanDao getDownloadTaskBeanDao() {
        return downloadTaskBeanDao;
    }

}
