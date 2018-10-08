package com.beier.aixunche.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beier.aixunche.R;
import com.beier.aixunche.flux.dispatcher.Dispatcher;
import com.beier.aixunche.widget.TitleBar;
import com.squareup.otto.Bus;

/**
 * Created by zhanghongbo on 15-11-13.
 */
public class BaseFragment extends Fragment {
//    protected IMPreference preference;
    protected Context mContext;
    protected TitleBar titleBar;
    protected Dispatcher dispatcher;
    protected String status;
    protected String msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        dispatcher = Dispatcher.get(new Bus());
//        preference = IMPreference.getInstance(getActivity());

    }

    public void setTitleBar(String title) {
        titleBar = (TitleBar) getChildFragmentManager().findFragmentById(R.id.title);
        showTitleBar(true);
        titleBar.setThemeColor(getResources().getColor(R.color.theme_color));
        titleBar.setTitle(title);
        titleBar.setFontColor(getResources().getColor(R.color.white));
        titleBar.setTitileBackgroundColor(getResources().getColor(R.color.theme_color));
    }

    public void showTitleBar(boolean show){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if (show) {
            ft.show(titleBar);
        } else {
            ft.hide(titleBar);
        }
        ft.commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

