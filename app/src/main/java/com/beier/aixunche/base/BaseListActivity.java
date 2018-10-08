package com.beier.aixunche.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.beier.aixunche.R;
import com.beier.aixunche.adapter.ListBaseAdapter;
import com.beier.aixunche.callback.NetworkListener;
import com.beier.aixunche.callback.OnEmptyListener;
import com.beier.aixunche.flux.action.BaseActions;
import com.beier.aixunche.widget.EmptyLayout;
import com.beier.aixunche.widget.LZToast;
import com.beier.aixunche.widget.SwipeListView;
import com.beier.aixunche.widget.pullrefresh.swipe.RefreshLayout;
import com.beier.aixunche.widget.pullrefresh.swipe.RefreshLvLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class BaseListActivity<T> extends BaseActivity implements RefreshLayout.OnRefreshListener,OnEmptyListener,RefreshLayout.OnLoadListener,NetworkListener {

    @BindView(R.id.loading)
    LinearLayout loadingLayout;
    @BindView(R.id.head_view)
    LinearLayout headLayout;
    @BindView(R.id.swiperefreshlayout)
    protected RefreshLvLayout mSwipeRefreshLayout;
    @BindView(R.id.list_view)
    protected SwipeListView mListView;
    @BindView(R.id.empty_layout)
    protected EmptyLayout emptyLayout;
    protected List<T> datas = new ArrayList<T>();
    protected ListBaseAdapter<T> adapter;
    protected int pageSize = 8;
    protected int offset = 0;
    protected int currentPage = 0;
    protected Map<String, String> params = new HashMap<>();
    protected boolean canRefresh = true;
    protected boolean canLoadMore = true;
    protected FrameLayout footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list);
        mListView.setEmptyView(emptyLayout);
        footerView = new FrameLayout(mContext);
        mListView.addFooterView(footerView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadListener(this);
//        user = preference.getUser();
//        if(user != null)
//            params.put("sign",preference.getUser().getSign());
//        params.put("offset", offset + "");
//        params.put("pagesize", pageSize + "");

    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 0;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void onResponseSuccess(String url, Object data) {
        emptyLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        setSwipeRefreshLoadedState();
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        if(mListView.getAdapter() == null)
        {
            mListView.setAdapter(adapter);
        }
        setSwipeRefreshLoadedState();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEmpty() {
        //不是加载更多的情况
        if(offset == 0)
        {
            setSwipeRefreshLoadedState();
            mSwipeRefreshLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);

        }
        loadingLayout.setVisibility(View.GONE);
    }

    public void onReponse(String status) {
        this.status = status;
        switch (status) {
            case BaseActions.LOADING:
                break;
            case BaseActions.ERROR:
                LZToast.getInstance(mContext).showToast(errorMsg);
                break;
            case BaseActions.NO_LOGIN:
//                loadingLayout.setVisibility(View.GONE);
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResponseError(String msg) {
        loadingLayout.setVisibility(View.GONE);
        LZToast.getInstance(mContext).showToast(msg);
    }

    @Override
    public void onLoad() {
        if(!canLoadMore)
        {
            return;
        }
        status = BaseActions.LOAD_MORE;
        currentPage++;
        offset = currentPage * pageSize;

//        if(user != null)
//            params.put("sign",preference.getUser().getSign());
//        params.put("offset", offset + "");
//        params.put("pagesize", pageSize + "");

        mSwipeRefreshLayout.setLoading(false);
    }

    @Override
    public void onRefresh() {
        if(!canRefresh)
        {
            return ;
        }
        status = BaseActions.REFRESH;
        loadingLayout.setVisibility(View.VISIBLE);
        setSwipeRefreshLoadingState();
        currentPage = 0;
        datas.clear();
        offset = 0;

//        if(user != null)
//            params.put("sign",preference.getUser().getSign());
//        params.put("offset", offset + "");
//        params.put("pagesize", pageSize + "");
    }

    /** 设置顶部正在加载的状态 */
    private void setSwipeRefreshLoadingState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    /** 设置顶部加载完毕的状态 */
    public void setSwipeRefreshLoadedState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

}
