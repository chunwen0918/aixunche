package com.beier.aixunche.flux.action;

/**
 * Created by zhanghongbo on 16-1-13.
 */
public interface BaseActions {
    String LOADING = "loading";
    String LOAD_FINISHED = "load-finished";
    String NETWORK_ERROR = "network-error";
    String RESPONSE = "response-";
    String ERROR_MESSAGE = "error-message";
    String NO_LOGIN = "no-login";
    String REFRESH = "refresh";
    String LOAD_MORE = "load-more";

    String DATA = "data";
    String ERROR = "ERROR";
    String PAGE = "page";
    String EXTRA = "extra";
    String CHECK_FALSE = "check-false";
    String NOTIFY = "notify";
    String INDEX_NOTIFY = "index_notify";
    String ORDER_NOTIFY = "order_notify";
}
