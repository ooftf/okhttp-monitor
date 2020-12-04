package com.ooftf.director;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/9/1
 */
class CacheUtil {
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 删除内部缓存。
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 删除内部文件。
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 删除SharedPrefs文件。
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File(context.getFilesDir().getParent() + "/shared_prefs"));
    }

    /**
     * 删除数据库文件。
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File(context.getFilesDir().getParent() + "/databases"));
    }

    /**
     * 清除外部缓存。
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    public static void clearAll(Context context) {
        cleanSharedPreference(context);
        cleanFiles(context);
        cleanDatabases(context);
    }

    public static void clearCache(Context context) {
        cleanInternalCache(context);
        cleanExternalCache(context);
    }


}
