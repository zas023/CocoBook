package com.thmub.cocobook.ui.activity;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.BookSearchBean;
import com.thmub.cocobook.model.bean.packages.SearchBookPackage;
import com.thmub.cocobook.model.local.BookRepository;
import com.thmub.cocobook.presenter.SearchPresenter;
import com.thmub.cocobook.presenter.contract.SearchContract;
import com.thmub.cocobook.ui.adapter.HotKeyWordAdapter;
import com.thmub.cocobook.ui.adapter.KeyWordAdapter;
import com.thmub.cocobook.ui.adapter.SearchBookAdapter;
import com.thmub.cocobook.base.BaseMVPActivity;
import com.thmub.cocobook.ui.adapter.SearchRecordAdapter;
import com.thmub.cocobook.widget.RefreshLayout;
import com.thmub.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 * 搜索activity
 */

public class SearchActivity extends BaseMVPActivity<SearchContract.Presenter>
        implements SearchContract.View {
    /*****************************Constant********************************/
    private static final int TAG_LIMIT = 8;

    /******************************View*************************************/
    @BindView(R.id.search_iv_back)
    ImageView mIvBack;
    @BindView(R.id.search_et_input)
    EditText mEtInput;
    @BindView(R.id.search_iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.search_iv_search)
    ImageView mIvSearch;
    @BindView(R.id.search_book_tv_refresh_hot)
    TextView mTvRefreshHot;
    @BindView(R.id.search_rv_hot)
    RecyclerView mRvHot;
    @BindView(R.id.search_rv_record)
    RecyclerView mRvRecord;
    @BindView(R.id.refresh_layout)
    RefreshLayout mRlRefresh;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvSearch;
    @BindView(R.id.search_book_tv_refresh_record)
    TextView searchBookTvRefreshRecord;

    private KeyWordAdapter mKeyWordAdapter;
    private HotKeyWordAdapter mHotKeyWordAdapter;
    private SearchRecordAdapter mSearchRecordAdapter;
    private SearchBookAdapter mSearchAdapter;

    /*****************************Variable********************************/
    private boolean isTag;
    private List<String> mHotTagList;
    private List<String> tags;
    private List<String> mRecordTagList;
    private int mTagStart = 0;

    /*****************************Initialization********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        initAdapter();
        mRlRefresh.setBackground(getDrawable(R.color.white));
    }

    private void initAdapter() {
        mKeyWordAdapter = new KeyWordAdapter();
        mHotKeyWordAdapter = new HotKeyWordAdapter();
        mSearchRecordAdapter = new SearchRecordAdapter();
        mSearchAdapter = new SearchBookAdapter();

        mRvHot.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRvHot.setAdapter(mHotKeyWordAdapter);

        mRvRecord.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSearch.addItemDecoration(new DividerItemDecoration(mContext));
        mRvRecord.setAdapter(mSearchRecordAdapter);

        mRvSearch.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSearch.addItemDecoration(new DividerItemDecoration(mContext));
    }

    @Override
    protected void initClick() {
        super.initClick();

        //退出
        mIvBack.setOnClickListener(
                (v) -> onBackPressed()
        );

        //输入框
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")) {
                    //隐藏delete按钮和关键字显示内容
                    if (mIvDelete.getVisibility() == View.VISIBLE) {
                        mIvDelete.setVisibility(View.INVISIBLE);
                        mRlRefresh.setVisibility(View.INVISIBLE);
                        //删除全部视图
                        mKeyWordAdapter.clear();
                        mSearchAdapter.clear();
                        mRvSearch.removeAllViews();
                    }
                    return;
                }
                //显示delete按钮
                if (mIvDelete.getVisibility() == View.INVISIBLE) {
                    mIvDelete.setVisibility(View.VISIBLE);
                    mRlRefresh.setVisibility(View.VISIBLE);
                    //默认是显示完成状态
                    mRlRefresh.showFinish();
                }
                //搜索
                String query = s.toString().trim();
                if (isTag) {
                    mRlRefresh.showLoading();
                    mPresenter.searchBook(query);
                    addRecord(query);
                    isTag = false;
                } else {
                    //传递
                    mPresenter.searchKeyWord(query);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //键盘的搜索
        mEtInput.setOnKeyListener((v, keyCode, event) -> {
            //修改回车键功能
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                searchBook();
                return true;
            }
            return false;
        });

        //进行搜索
        mIvSearch.setOnClickListener(
                (v) -> searchBook()
        );

        //删除字
        mIvDelete.setOnClickListener(
                (v) -> {
                    mEtInput.setText("");
                    toggleKeyboard();
                }
        );

        //点击查书
        mKeyWordAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //显示正在加载
                    mRlRefresh.showLoading();
                    String book = mKeyWordAdapter.getItem(pos);
                    mPresenter.searchBook(book);
                    addRecord(mEtInput.getText().toString().trim());
                    toggleKeyboard();
                }
        );

        //热搜 Tag的点击事件
        mHotKeyWordAdapter.setOnItemClickListener((view, pos) -> {
            isTag = true;
            mEtInput.setText(tags.get(pos));
        });

        //历史记录Tag的点击事件
        mSearchRecordAdapter.setOnItemClickListener((view, pos) -> {
            isTag = true;
            mEtInput.setText(mRecordTagList.get(pos));
        });
        //Tag的刷新(换一批)事件
        mTvRefreshHot.setOnClickListener((v) -> {
                    mHotKeyWordAdapter.clear();
                    refreshTag();
                }
        );

        //书本的点击事件
        mSearchAdapter.setOnItemClickListener(
                (view, pos) -> {
                    String bookId = mSearchAdapter.getItem(pos).get_id();
                    BookDetailActivity.startActivity(this, bookId, mSearchAdapter.getItem(pos).getTitle());
                }
        );
        //清空历史
        searchBookTvRefreshRecord.setOnClickListener(
                view -> {
                    BookRepository.getInstance().deleteSearchRecords();
                    mRecordTagList.clear();
                    mSearchRecordAdapter.clear();
                    mSearchRecordAdapter.addItems(mRecordTagList);
                }
        );
    }

    private void searchBook() {
        String query = mEtInput.getText().toString().trim();
        if (!query.equals("")) {
            addRecord(query);
            mRlRefresh.setVisibility(View.VISIBLE);
            mRlRefresh.showLoading();
            mPresenter.searchBook(query);
            //显示正在加载
            mRlRefresh.showLoading();
            toggleKeyboard();
        }
    }

    private void addRecord(String query) {
        mPresenter.addSearchRecord(new BookSearchBean(query));
    }

    private void toggleKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /*****************************Transaction********************************/

    @Override
    protected SearchContract.Presenter bindPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        //默认隐藏
        mRlRefresh.setVisibility(View.GONE);
        //获取热词
        mPresenter.searchHotWord();
        //获取搜索记录
        mPresenter.searchRecord();
    }

    @Override
    public void showError() {
    }

    @Override
    public void complete() {

    }

    @Override
    public void finishHotWords(List<String> hotWords) {
        mHotTagList = hotWords;
        refreshTag();
    }

    private void refreshTag() {
        int last = mTagStart + TAG_LIMIT;
        if (mHotTagList.size() <= last) {
            mTagStart = 0;
            last = TAG_LIMIT;
        }
        tags = mHotTagList.subList(mTagStart, last);
        mHotKeyWordAdapter.addItems(tags);
        mTagStart += TAG_LIMIT;
    }

    @Override
    public void finishKeyWords(List<String> keyWords) {
        if (keyWords.size() == 0) mRlRefresh.setVisibility(View.INVISIBLE);
        mKeyWordAdapter.refreshItems(keyWords);
        if (!(mRvSearch.getAdapter() instanceof KeyWordAdapter)) {
            mRvSearch.setAdapter(mKeyWordAdapter);
        }
    }

    @Override
    public void finishBooks(List<SearchBookPackage.BooksBean> books) {
        mSearchAdapter.refreshItems(books);
        if (books.size() == 0) {
            mRlRefresh.showEmpty();
        } else {
            //显示完成
            mRlRefresh.showFinish();
        }
        //加载
        if (!(mRvSearch.getAdapter() instanceof SearchBookAdapter)) {
            mRvSearch.setAdapter(mSearchAdapter);
        }
    }

    @Override
    public void finishRecord(List<BookSearchBean> records) {
        if (records == null)
            return;
        mRecordTagList = new ArrayList<>();
        for (BookSearchBean bean : records)
            mRecordTagList.add(bean.getKeyword());
        Collections.reverse(mRecordTagList);
        mSearchRecordAdapter.clear();
        mSearchRecordAdapter.addItems(mRecordTagList);
    }

    @Override
    public void finishAddRecord(BookSearchBean bean) {
        mPresenter.searchRecord();
    }

    @Override
    public void errorBooks() {
        mRlRefresh.showEmpty();
    }

    /*****************************Event********************************/
    @Override
    public void onBackPressed() {
        if (mRlRefresh.getVisibility() == View.VISIBLE) {
            mEtInput.setText("");
        } else {
            super.onBackPressed();
        }
    }
}
