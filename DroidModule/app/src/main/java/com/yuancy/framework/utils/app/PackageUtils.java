/**
 * <p>
 * Title: PackageUtils.java
 * </p>
 * <p>
 * Description: 应用程序安装包管理
 * </p>
 * <p>
 * 1、应用程序安装（普通、静默）；
 * 2、应用程序卸载（普通、静默）；
 * 3、使用此类应用程序可获取到短信箱操作权限
 * </p>
 *
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月17日 下午4:38:36
 * @version V1.0
 */
package com.yuancy.framework.utils.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

public class PackageUtils {
    private PackageUtils() {
        throw new AssertionError();
    }

    /**
     * install package normal by system intent
     *
     * @param context
     * @param filePath
     *            file path of package
     * @return whether apk exist
     */
    public static boolean installNormal(Context context, String filePath) {
        File file = new File(filePath);
        if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 判读版本是否在7.0以上
            if (Build.VERSION.SDK_INT >= 24) {
                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
                // 由于没有在Activity环境下启动Activity,设置下面的标签
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // 添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
