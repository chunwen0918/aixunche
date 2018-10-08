package com.beier.aixunche.flux.dispatcher;

import com.beier.aixunche.flux.action.Action;
import com.beier.aixunche.flux.store.Store;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgvalle on 19/07/15.
 */
public class Dispatcher {
    private final Bus bus;
    private static Dispatcher instance;
    private List<Object> events = new ArrayList<>();

    public static Dispatcher get(Bus bus) {
        if (instance == null) {
            instance = new Dispatcher(bus);
        }
        return instance;
    }

    Dispatcher(Bus bus) {
        this.bus = bus;
    }

    public void register(final Object cls) {
        if(!events.contains(cls))
        {
            events.add(cls);
            bus.register(cls);
        }

    }

    public void unregister(final Object cls) {
        if(events.contains(cls))
        {
            bus.unregister(cls);
            events.remove(cls);
        }
    }

    public void emitChange(Store.StoreChangeEvent o) {
        post(o);
    }

    public void dispatch(String type, Object... data) {
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(type);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        post(actionBuilder.build());
    }

    private boolean isEmpty(String type) {
        return type == null || type.isEmpty();
    }

    private void post(final Object event) {
        bus.post(event);
    }
}
