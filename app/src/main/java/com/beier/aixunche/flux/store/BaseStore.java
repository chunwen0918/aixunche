package com.beier.aixunche.flux.store;

import com.google.gson.Gson;
import com.zhangdong.yidian_client.flux.action.Action;
import com.zhangdong.yidian_client.flux.action.BaseActions;
import com.zhangdong.yidian_client.flux.dispatcher.Dispatcher;

/**
 * Created by zhanghongbo on 16-1-13.
 */
public class BaseStore extends Store {
    protected Gson gson;
    protected String status;
    protected String errorMsg;
    protected int notify;
    protected int index_notify;
    protected int order_notify;

    protected BaseStore(Dispatcher dispatcher) {
        super(dispatcher);
        gson = new Gson();
    }

    @Override
    StoreChangeEvent changeEvent() {
        return new CommonStoreChangeEvent();
    }

    @Override
    public void onAction(Action action) {
        status = "";
        if (action.getData().get(BaseActions.NOTIFY) != null) {
            notify = (int) action.getData().get(BaseActions.NOTIFY);
        }
        if (action.getData().get(BaseActions.INDEX_NOTIFY) != null) {
            index_notify = (int) action.getData().get(BaseActions.INDEX_NOTIFY);
        }
        if (action.getData().get(BaseActions.ORDER_NOTIFY) != null) {
            order_notify = (int) action.getData().get(BaseActions.ORDER_NOTIFY);
        }
        switch (action.getType()) {
            case BaseActions.LOADING:
                status = BaseActions.LOADING;
                emitStoreChange();
                break;
            case BaseActions.NO_LOGIN:
                status = BaseActions.NO_LOGIN;
                emitStoreChange();
                break;
            case BaseActions.ERROR:
                status = BaseActions.ERROR;
                errorMsg = (String) action.getData().get(BaseActions.DATA);
                emitStoreChange();
                break;

        }
    }

    public class CommonStoreChangeEvent implements StoreChangeEvent {
        @Override
        public String getAction() {
            return null;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    public int getIndex_notify() {
        return index_notify;
    }

    public void setIndex_notify(int index_notify) {
        this.index_notify = index_notify;
    }

    public int getOrder_notify() {
        return order_notify;
    }

    public void setOrder_notify(int order_notify) {
        this.order_notify = order_notify;
    }
}
