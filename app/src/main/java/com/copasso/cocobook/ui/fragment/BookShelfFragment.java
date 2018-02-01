package com.copasso.cocobook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.copasso.cocobook.R;
import com.copasso.cocobook.RxBus;
import com.copasso.cocobook.model.bean.CollBookBean;
import com.copasso.cocobook.model.event.DeleteResponseEvent;
import com.copasso.cocobook.model.event.DeleteTaskEvent;
import com.copasso.cocobook.model.event.DownloadMessage;
import com.copasso.cocobook.model.event.RecommendBookEvent;
import com.copasso.cocobook.model.local.BookRepository;
import com.copasso.cocobook.presenter.BookShelfPresenter;
import com.copasso.cocobook.presenter.contract.BookShelfContract;
import com.copasso.cocobook.ui.activity.ReadActivity;
import com.copasso.cocobook.ui.activity.SearchActivity;
import com.copasso.cocobook.ui.adapter.CollBookAdapter;
import com.copasso.cocobook.ui.base.BaseMVPFragment;
import com.copasso.cocobook.utils.ProgressUtils;
import com.copasso.cocobook.utils.RxUtils;
import com.copasso.cocobook.utils.ToastUtils;
import com.copasso.cocobook.utils.UiUtils;
import com.copasso.cocobook.widget.adapter.WholeAdapter;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;
import com.copasso.cocobook.widget.refresh.ScrollRefreshRecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;

import java.io.File;
import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 * 书架fragment
 */

public class BookShelfFragment extends BaseMVPFragment<BookShelfContract.Presenter>
        implements BookShelfContract.View {

    @BindView(R.id.book_shelf_rv_content)
    ScrollRefreshRecyclerView mRvContent;
    //全选
    @BindView(R.id.multi_select_rl_root)
    RelativeLayout multiSelectRlRoot;
    @BindView(R.id.multi_select_cb_all)
    CheckBox multiSelectCbAll;
    @BindView(R.id.multi_select_btn_add)
    Button multiSelectBtnAdd;
    @BindView(R.id.multi_select_btn_delete)
    Button multiSelectBtnDelete;

    /***************************视图********************************/
    private CollBookAdapter mCollBookAdapter;
    private FooterItemView mFooterItem;

    /***************************参数********************************/
    //是否是第一次进入
    private boolean isInit = true;
    //是否是多选模式
    private  boolean isMultiSelectMode = false;

    public boolean isMultiSelectMode(){
        return isMultiSelectMode;
    }

    /**
     * 退出多选
     */
    public void cancelMultiSelectMode(){
        isMultiSelectMode=false;
        mCollBookAdapter.setShowCheckBox(isMultiSelectMode);
        multiSelectRlRoot.setVisibility(View.GONE);
    }
    /***************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bookshelf;
    }

    @Override
    protected BookShelfContract.Presenter bindPresenter() {
        return new BookShelfPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        multiSelectRlRoot.setVisibility(View.GONE);
        multiSelectBtnAdd.setText("缓存");
        initAdapter();
        initEvent();
    }

    private void initAdapter() {
        //添加Footer
        mCollBookAdapter = new CollBookAdapter();
        mRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mRvContent.addItemDecoration(new DividerItemDecoration(mContext));
        mRvContent.setAdapter(mCollBookAdapter);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        //推荐书籍
        addDisposable(RxBus.getInstance()
                .toObservable(RecommendBookEvent.class)
                .subscribe(
                        event -> {
                            mRvContent.startRefresh();
                            mPresenter.loadRecommendBooks(event.sex);
                        }
                ));
        //下载书籍
        addDisposable(RxBus.getInstance()
                .toObservable(DownloadMessage.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            //使用Toast提示
                            ToastUtils.show(event.message);
                        }
                ));
        //删除书籍
        addDisposable(RxBus.getInstance()
                .toObservable(DeleteResponseEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            if (event.isDelete) {
                                ProgressUtils.show(mContext, "正在删除中");
                                addDisposable(BookRepository.getInstance().deleteCollBookInRx(event.collBook)
                                        .compose(RxUtils::toSimpleSingle)
                                        .subscribe(
                                                (integer) -> {
                                                    mCollBookAdapter.removeItem(event.collBook);
                                                    ProgressUtils.dismiss();
                                                }, throwable -> {
                                                    ProgressUtils.dismiss();
                                                }
                                        ));
                            } else {
                                //弹出一个Dialog
                                AlertDialog tipDialog = new AlertDialog.Builder(mContext)
                                        .setTitle("您的任务正在加载")
                                        .setMessage("先请暂停任务再进行删除")
                                        .setPositiveButton("确定", (dialogInterface, which) -> {
                                            dialogInterface.dismiss();
                                        }).create();
                                tipDialog.show();
                            }
                        }
                ));
    }

    @Override
    protected void initClick() {
        super.initClick();

        multiSelectBtnAdd.setOnClickListener(view -> {
            isMultiSelectMode=false;
            mCollBookAdapter.setShowCheckBox(isMultiSelectMode);
            multiSelectRlRoot.setVisibility(View.GONE);
            ToastUtils.show(""+mCollBookAdapter.getCheckedCount());
        });

        multiSelectBtnDelete.setOnClickListener(view -> {
            isMultiSelectMode=false;
            mCollBookAdapter.setShowCheckBox(isMultiSelectMode);
            multiSelectRlRoot.setVisibility(View.GONE);
        });

        mRvContent.setOnRefreshListener(
                () -> mPresenter.updateCollBooks(mCollBookAdapter.getItems())
        );

        mCollBookAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //如果是本地文件，首先判断这个文件是否存在
                    CollBookBean collBook = mCollBookAdapter.getItem(pos);
                    if (collBook.isLocal()) {
                        //id表示本地文件的路径
                        String path = collBook.getCover();
                        File file = new File(path);
                        //判断这个本地文件是否存在
                        if (file.exists()) {
                            ReadActivity.startActivity(mContext,
                                    mCollBookAdapter.getItem(pos), true);
                        } else {
                            //提示(从目录中移除这个文件)
                            new AlertDialog.Builder(mContext)
                                    .setTitle(UiUtils.getString(R.string.nb_common_tip))
                                    .setMessage("文件不存在,是否删除")
                                    .setPositiveButton(getResources().getString(R.string.nb_common_sure),
                                            ((dialogInterface, i) -> {
                                                deleteBook(collBook);
                                            }))
                                    .setNegativeButton(UiUtils.getString(R.string.nb_common_cancel), null)
                                    .show();
                        }
                    } else {
                        ReadActivity.startActivity(mContext,
                                mCollBookAdapter.getItem(pos), true);
                    }
                }
        );

        mCollBookAdapter.setOnItemLongClickListener(
                (v, pos) -> {
                    //开启Dialog,最方便的Dialog,就是AlterDialog
                    openItemDialog(mCollBookAdapter.getItem(pos));
                    return true;
                }
        );
    }

    /***************************业务逻辑********************************/
    @Override
    protected void processLogic() {
        super.processLogic();
        mRvContent.startRefresh();
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        if (mCollBookAdapter.getItemCount() > 0 && mFooterItem == null) {
            mFooterItem = new FooterItemView();
            mCollBookAdapter.addFooterView(mFooterItem);
        }

        if (mRvContent.isRefreshing()) {
            mRvContent.finishRefresh();
        }
    }

    @Override
    public void finishRefresh(List<CollBookBean> collBookBeans) {
        mCollBookAdapter.refreshItems(collBookBeans);
        //如果是初次进入，则更新书籍信息
        if (isInit) {
            isInit = false;
            mRvContent.post(
                    () -> mPresenter.updateCollBooks(mCollBookAdapter.getItems())
            );
        }
    }

    @Override
    public void finishUpdate() {
        //重新从数据库中获取数据
        mCollBookAdapter.refreshItems(BookRepository
                .getInstance().getCollBooks());
    }

    @Override
    public void showErrorTip(String error) {
        mRvContent.setTip(error);
        mRvContent.showTip();
    }


    /***************************事件处理********************************/
    private void openItemDialog(CollBookBean collBook) {
        String[] menus = collBook.isLocal() ? UiUtils.getStringArray(R.array.nb_menu_local_book)
                : UiUtils.getStringArray(R.array.nb_menu_net_book);
        AlertDialog collBookDialog = new AlertDialog.Builder(mContext)
                .setTitle(collBook.getTitle())
                .setAdapter(new ArrayAdapter<String>(mContext,
                                android.R.layout.simple_list_item_1, menus),
                        (dialog, which) -> onItemMenuClick(menus[which], collBook))
                .create();

        collBookDialog.show();
    }

    private void onItemMenuClick(String which, CollBookBean collBook) {
        switch (which) {
            case "置顶":
                break;
            case "缓存":
                downloadBook(collBook);
                break;
            case "删除":
                deleteBook(collBook);
                break;
            case "批量管理":
                isMultiSelectMode=true;
                mCollBookAdapter.setShowCheckBox(isMultiSelectMode);
                multiSelectRlRoot.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    private void downloadBook(CollBookBean collBook) {
        //创建任务
        mPresenter.createDownloadTask(collBook);
    }

    /**
     * 默认删除本地文件
     *
     * @param collBook
     */
    private void deleteBook(CollBookBean collBook) {

        if (collBook.isLocal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_delete, null);
            CheckBox cb = (CheckBox) view.findViewById(R.id.delete_cb_select);
            new AlertDialog.Builder(mContext)
                    .setTitle("删除文件")
                    .setView(view)
                    .setPositiveButton(UiUtils.getString(R.string.nb_common_sure), ((dialogInterface, i) -> {
                        boolean isSelected = cb.isSelected();
                        if (isSelected) {
                            ProgressUtils.show(mContext, "删除中...");
                            //删除
                            File file = new File(collBook.getCover());
                            if (file.exists()) file.delete();
                            BookRepository.getInstance().deleteCollBook(collBook);
                            BookRepository.getInstance().deleteBookRecord(collBook.get_id());

                            //从Adapter中删除
                            mCollBookAdapter.removeItem(collBook);
                            ProgressUtils.dismiss();
                        } else {
                            BookRepository.getInstance().deleteCollBook(collBook);
                            BookRepository.getInstance().deleteBookRecord(collBook.get_id());
                            //从Adapter中删除
                            mCollBookAdapter.removeItem(collBook);
                        }
                    }))
                    .setNegativeButton(getResources().getString(R.string.nb_common_cancel), null)
                    .show();
        } else {
            RxBus.getInstance().post(new DeleteTaskEvent(collBook));
        }
    }

    /*****************************************************************/
    class FooterItemView implements WholeAdapter.ItemView {
        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.footer_book_shelf, parent, false);
            view.setOnClickListener((v) -> {
                        startActivity(new Intent(mContext, SearchActivity.class));
                    }
            );
            return view;
        }

        @Override
        public void onBindView(View view) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refreshCollBooks();
    }
}
