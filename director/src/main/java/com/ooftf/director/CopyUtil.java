package com.ooftf.director;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/11 0011
 */
public class CopyUtil {
    public static  void copy(Context context, String message) {
        //获取剪贴版
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        //第一个参数只是一个标记，随便传入。
        //第二个参数是要复制到剪贴版的内容
        ClipData clip = ClipData.newPlainText("simple text", message);
        //传入clipdata对象.
        clipboard.setPrimaryClip(clip);
    }
}
