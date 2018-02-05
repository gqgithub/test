package com.gq.app.model;

/**
 * 同步消息：阅读量
 */
public class EventBusCount {

    private int count;

    public EventBusCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
