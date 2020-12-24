package com.shl.leakcanary.copy;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class KeyWeakReference<T> extends WeakReference<T> {

    String key;

    public KeyWeakReference(String key,T t) {
        super(t);
        this.key = key;
    }


    public KeyWeakReference(String key, ReferenceQueue<T> referenceQueue,T t) {
        super(t, referenceQueue);
        this.key = key;
    }


}
