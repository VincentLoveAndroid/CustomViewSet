package com.example.mingren.customviewset.view.bezierview;

import android.content.Context;
import android.widget.ImageView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ImageViewPools {
    private List<ImageView> pools;
    private int capacity;
    private boolean ok;
    private Object emptyLock = new Object();
    private Object fullLock = new Object();

    public ImageViewPools(Context context, int capacity) {
        this.capacity = capacity;
        pools = Collections.synchronizedList(new LinkedList<ImageView>());
        for (int i = 0; i < capacity; i++) {
            pools.add(new ImageView(context));
        }
        ok = true;
    }

    public void shutdown() {
        ok = false;
        synchronized (emptyLock) {
            emptyLock.notify();
        }

        synchronized (fullLock) {
            fullLock.notify();
        }
    }

    public ImageView allocImageView() {
        while (pools.isEmpty() && ok) {
            synchronized (emptyLock) {
                try {
                    emptyLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!ok) {
            return null;
        }

        synchronized (pools) {
            ImageView imageView = pools.remove(0);
            synchronized (fullLock) {
                fullLock.notify();
            }
            return imageView;
        }
    }

    public void releaseImageView(ImageView imageView) {
        while (pools.size() == capacity && ok) {
            synchronized (fullLock) {
                try {
                    fullLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!ok) {
            return;
        }
        synchronized (pools) {
            if (imageView != null) {
                pools.add(imageView);
                synchronized (emptyLock) {
                    emptyLock.notify();
                }
            }
        }
    }
}
