package com.shl.leakcanary.copy;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.UUID;

import javax.naming.Context;

public class Watcher {

    HashMap<String, KeyWeakReference> watched = new HashMap<>();
    HashMap<String, KeyWeakReference> suspicion = new HashMap<>();


    ReferenceQueue referenceQueue = new ReferenceQueue();

    public void addWatch(Object o
    ) {
        UUID uuid = new UUID(11l, 123l);
        KeyWeakReference reference = new KeyWeakReference<Object>(uuid.toString(), referenceQueue,o);

        watched.put(uuid.toString(), reference);
        
        
        
    }


    public void clean() {

    }


}
