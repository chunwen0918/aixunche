package com.beier.aixunche.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;


import com.beier.aixunche.R;
import com.beier.aixunche.flux.action.BaseActions;
import com.beier.aixunche.flux.dispatcher.Dispatcher;
import com.beier.aixunche.utils.AppManager;
import com.beier.aixunche.widget.LZToast;
import com.beier.aixunche.widget.TitleBar;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity implements
        OnClickListener {
    @BindView(R.id.loading)
    LinearLayout loadingLayout;
    protected BaseActivity mContext;
    protected AppManager appManager;
    protected TitleBar titleBar;
    protected String status;
    protected String errorMsg;
    public String ACTION_EXIT;
    public String ACTION_LOGIN;
    public String ACTION_LOGOUT;
    protected Dispatcher dispatcher;
    /**
     * 页面的类型
     */
    public static int DEFAULT = 0;
    public static int ADD = 1;
    public static int EDIT = 2;
    public static int CHOOSE = 3;


    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context paramAnonymousContext,
                              Intent paramAnonymousIntent) {
            String str = paramAnonymousIntent.getAction();
            if (ACTION_EXIT.equals(str)) {
                BaseActivity.this.finish();
            } else if (ACTION_LOGIN.equals(str)) {
                BaseActivity.this.updateUIAfterLoginSuccess();
            } else if (ACTION_LOGOUT.equals(str)) {
                BaseActivity.this.updateUIAfterLogoutSuccess();
            }
        }
    };

    public void onClick(View paramView) {
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        ACTION_EXIT = getApplicationContext().getPackageName() + ".EXIT";
        ACTION_LOGIN = getApplicationContext().getPackageName() + ".LOGIN";
        ACTION_LOGOUT = getApplicationContext().getPackageName() + ".LOGOUT";

        this.mContext = this;
        dispatcher = Dispatcher.get(new Bus());
        appManager = AppManager.getAppManager();
        appManager.addActivity(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        registerReceiver(this.receiver, new IntentFilter(ACTION_EXIT));
        registerReceiver(this.receiver, new IntentFilter(ACTION_LOGIN));
        registerReceiver(this.receiver, new IntentFilter(ACTION_LOGOUT));

    }

    public void initTitle(String title) {
        titleBar = (TitleBar) getSupportFragmentManager().findFragmentById(R.id.title);
        titleBar.setBackgroundColor(getResources().getColor(R.color.theme_color));
        titleBar.setFontColor(getResources().getColor(R.color.white));
        titleBar.setBackButtonVisible();
        titleBar.setTitle(title);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    protected void onDestroy() {
        appManager.finishActivity(this);
        unregisterReceiver(this.receiver);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void onReponse(String status) {
        this.status = status;
        switch (status) {
            case BaseActions.LOADING:
                if(loadingLayout!=null){
                    loadingLayout.setVisibility(View.VISIBLE);
                }
                break;
            case BaseActions.ERROR:
                loadingLayout.setVisibility(View.GONE);
                LZToast.getInstance(mContext).showToast(errorMsg);
                break;
            case BaseActions.NO_LOGIN:
                loadingLayout.setVisibility(View.GONE);
////                if(YSharePreference.getInstance().getIsLoginActivity()==0) {
////                    YSharePreference.getInstance().setIsLoginActivity(1);
////                    appManager.finishAllActivity();
////                }
//                YSharePreference.getInstance().setUser(null);
//                LZToast.getInstance(mContext).showToast("您的登录信息已过期，请重新登录！");
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void addFragment(int containerViewId,
                               Fragment fragment,
                               String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }

    protected void replaceFragmentint(int containerViewId,
                                      Fragment fragment,
                                      String fragmentTag,
                                      String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onChildTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public class EditInputTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            BaseActivity.this.onChildTextChanged(charSequence, i,
                    i1, i2);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    protected void updateUIAfterLoginSuccess() {
    }

    protected void updateUIAfterLogoutSuccess() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
