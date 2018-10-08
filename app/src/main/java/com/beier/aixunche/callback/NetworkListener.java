package com.beier.aixunche.callback;

/**
 * Created by zhanghongbo on 15-12-30.
 */
public interface NetworkListener {
    public void onResponseSuccess(String url, Object data);
    public void onResponseError(String msg);
}
