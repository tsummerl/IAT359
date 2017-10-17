package com.example.jake.summerlandtwilight_a2;

import java.util.ArrayList;

/**
 * Created by Jake on 10/16/2017.
 */

public class LimitedSizeQueue<K> extends ArrayList<K> {

    private int maxSize;

    public LimitedSizeQueue(int size){
        this.maxSize = size;
    }

    public boolean add(K k){
        boolean r = super.add(k);
        if (size() > maxSize){
            removeRange(0, size() - maxSize - 1);
        }
        return r;
    }

    public K getYongest() {
        return get(size() - 1);
    }

    public K getOldest() {
        return get(0);
    }
}