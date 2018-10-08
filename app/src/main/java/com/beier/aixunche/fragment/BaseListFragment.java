package com.beier.aixunche.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.beier.aixunche.R;
import com.beier.aixunche.adapter.ListBaseAdapter;
import com.beier.aixunche.callback.NetworkListener;
import com.beier.aixunche.callback.OnEmptyListener;
import com.beier.aixunche.widget.EmptyLayout;
import com.beier.aixunche.widget.SwipeListView;
import com.beier.aixunche.widget.pullrefresh.swipe.RefreshLayout;
import com.beier.aixunche.widget.pullrefresh.swipe.RefreshLvLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacob on 15/6/28.
 */
public class BaseListFragment<T> extends BaseFragment implements NetworkListener,OnEmptyListener,SwipeRefreshLayout.OnRefreshListener,RefreshLayout.OnLoadListener{

    @BindView(R.id.head_view)
    LinearLayout headLayout;
    @BindView(R.id.loading)
    LinearLayout loadingLayout;
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
    protected Map<String,String> params = new HashMap<>();
    protected FrameLayout footerView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_base_list,null,false);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadListener(this);
        mListView.setEmptyView(emptyLayout);
        mListView.setCanSwipe(false);
        footerView = new FrameLayout(mContext);
        mListView.addFooterView(footerView);
//         user = preference.getUser();
//        if(user != null)
//        params.put("sign",preference.getUser().getSign());

        params.put("offset", offset + "");
        params.put("pagesize", pageSize + "");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResponseSuccess(String url, Object data) {
        setSwipeRefreshLoadedState();
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.GONE);
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
        else
        {
//            View footer = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_view, null, false);
//            footerView.removeAllViews();
//            footerView.addView(footer);
        }
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void  onResponseError(String msg) {
        setSwipeRefreshLoadedState();
        loadingLayout.setVisibility(View.GONE);

    }

    @Override
    public void onLoad() {
        currentPage++;
        offset = currentPage * 8;

//        if(user != null)
//            params.put("sign",preference.getUser().getSign());
//        params.put("offset", offset + "");
//        params.put("pagesize", pageSize + "");

        mSwipeRefreshLayout.setLoading(false);
    }

    @Override
    public void onRefresh() {
        setSwipeRefreshLoadingState();
        loadingLayout.setVisibility(View.GONE);
        currentPage = 0;
        datas.clear();
        offset = 0;

//        if(user != null)
//        params.put("sign",preference.getUser().getSign());
//        params.put("offset", offset + "");
//        params.put("pagesize", pageSize + "");
    }

    /** 设置顶部正在加载的状态 */
    public void setSwipeRefreshLoadingState() {
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
    @OnClick(R.id.loading)
    public void onClick()
    {

    }
}
