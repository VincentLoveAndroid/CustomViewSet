package com.example.mingren.customviewset.Utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by vincent on 2016/12/27.
 * email-address:674928145@qq.com
 * description:
 */

public class MemoryCacheUtil extends LruCache<String, Bitmap> {

    public MemoryCacheUtil getInstance() {
        return new MemoryCacheUtil();
    }

    private MemoryCacheUtil() {
        super((int) (Runtime.getRuntime().maxMemory() / 8));//指定缓存大小

    }

    //计算缓存目标的大小
    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    // 移除eldest目标直到LinkHashMap大小小于缓存指定的大小，每移除一个，调用一次entryRemoved
    @Override
    public void trimToSize(int maxSize) {
        super.trimToSize(maxSize);
    }

    //有缓存被移除的时候调用

    /**
     * @param evicted  true if the entry is being removed to make space, false if
     *                 the removal(移除) was caused by a {@link #put} or {@link #remove}.
     * @param key
     * @param oldValue the value was removed
     * @param newValue the new value for {@code key}, if it exists. If non-null,
     *                 this removal was caused by a {@link #put}. Otherwise it was caused by
     *                 an eviction or a {@link #remove}.
     */
    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }

    @Override
    protected Bitmap create(String key) {
        return super.create(key);
    }
}
