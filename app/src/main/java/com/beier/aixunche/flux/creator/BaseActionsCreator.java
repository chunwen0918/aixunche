package com.beier.aixunche.flux.creator;


import com.beier.aixunche.flux.dispatcher.Dispatcher;

/**
 * Created by zhanghongbo on 16-1-20.
 */
public class BaseActionsCreator {
    protected Dispatcher dispatcher;

    public BaseActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
