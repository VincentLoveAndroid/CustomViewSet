package com.example.mingren.customviewset.Utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.example.mingren.customviewset.diskLruCache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by vincent on 2016/12/29.
 * email-address:674928145@qq.com
 * description:
 */

public class DiskCacheUtil {

    private Context mContext;
    private DiskLruCache mDiskLruCache;

    public static DiskCacheUtil newInstance(Context context) {
        return new DiskCacheUtil(context);
    }

    private DiskCacheUtil() {

    }

    private DiskCacheUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 方法里面使用了成员变量mDiskLruCache，线程不安全，需要synchronized
     *
     * @param maxSize    缓存的最大数量
     * @param uniqueName 缓存独立名字
     */
    public synchronized void open(long maxSize, String uniqueName) {
        mDiskLruCache = null;
        try {
            File cacheDir = getDiskCacheDir(mContext, uniqueName);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(mContext), 1, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从网络下载文件并且缓存到(以url的MD5作为文件名)的文件上
     */
    public synchronized void cache(final String imageUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String key = hashKeyForDisk(imageUrl);
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);//key将作为缓存文件的文件名
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(imageUrl, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 获取磁盘缓存文件夹
     */

    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取版本号
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获取url的outputStream流
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * MD5加密url，安全，规范文件名。
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {//消息摘要算法对文件名进行加密，防止文件名不规范，编码后的字符串肯定是唯一的，并且只会包含0-F这样的字符，完全符合文件的命名规则。
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 根据url从磁盘中获取文件输入流
     */
    public synchronized InputStream get(String imageUrl) {
        String key = hashKeyForDisk(imageUrl);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            return snapshot.getInputStream(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据url移除相应的缓存
     */
    public synchronized void remove(String imageUrl) {
        try {
            String key = hashKeyForDisk(imageUrl);
            mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前缓存的大小
     */
    public synchronized long size() {
        return mDiskLruCache.size();
    }

    /**
     * 将内存中的操作记录同步到日志文件(也就是journal中)，DiskLruCache能正常工作的前提是依赖journal文件中的内容，
     * 但并不是每次写入缓存都要flush一次，比较标准的做法是在Activity的flush去调用一次即可。
     */
    public synchronized void flush() {
        try {
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于将DiskLruCache关闭掉，是和open方法对应的一个方法，通常只应该在Activity的OnDestroy()方法中调用close方法。
     */
    public synchronized void close() {
        try {
            mDiskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将该缓存下所有的缓存数据删除,可以实现清除缓存的功能
     */
    public synchronized void delete() {
        try {
            mDiskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
