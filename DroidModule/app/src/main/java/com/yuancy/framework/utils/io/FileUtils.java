/**
 * <p>
 * Title: FileUtils.java
 * </p>
 * <p>
 * Description:文件操作
 * </p>
 * <p>
 * </p>
 *
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月18日 上午11:56:2
 * @version V1.0
 */
package com.yuancy.framework.utils.io;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.yuancy.framework.utils.string.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title: FileUtils
 * </p>
 * <p>
 * Description: 文件操作工具类
 * </p>
 * <p>
 * <p>
 * </p>
 *
 * @author zhangqy
 * @date 2016年1月20日下午3:51:42
 */
public class FileUtils {
    private static final String TAG = FileUtils.class.getName();
    /**
     * 外置SD卡的根路径
     */
    public static String SD_DIR = Environment.getExternalStorageDirectory()
            .toString();
    /**
     * 内置存储的根路径
     */
    public static String DATA_DIR = Environment.getDataDirectory().toString();
    /**
     * 当前存储状态【有外置SD卡还是只有内置存储】
     */
    public static String STORAGE_STATE = Environment.getExternalStorageState();

    /**
     * <p>
     * Title: readFile
     * </p>
     * <p>
     * Description: 文件转为byte
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param filePath
     * @return
     * @author zhangqy
     * @date 2016年2月25日下午3:08:16
     */

    public static byte[] readFile(String filePath) {
        try {
            @SuppressWarnings("resource")
            FileInputStream fin = new FileInputStream(filePath);
            if (fin != null) {
                byte[] bytes = new byte[fin.available()];
                fin.read(bytes);
                fin.close();
                return bytes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * Title: deleteFile
     * </p>
     * <p>
     * Description: 删除指定文件
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param path
     * @author zhangqy
     * @date 2016年4月13日下午4:31:26
     */
    public static void deleteFile(String path) {
        File f = new File(path);
        if (f != null) {
            f.delete();
        }
    }

    /**
     * <p>
     * Title: getFilePathAndName
     * </p>
     * <p>
     * Description: 获取文件路径及名称
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param fileNamePath
     * @return
     * @author zhangqy
     * @date 2016年2月25日下午2:57:25
     */
    public static Map<String, String> getFilePathAndName(String fileNamePath) {
        int index = fileNamePath.lastIndexOf("/");
        String fileName = fileNamePath.substring(index + 1);
        String filePath = fileNamePath.substring(0, index);
        Map<String, String> map = new HashMap<String, String>();
        map.put("path", filePath);
        map.put("name", fileName);
        return map;
    }

    /**
     * <p>
     * Title: createFile
     * </p>
     * <p>
     * Description: 创建文件
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param folderName 绝对路径
     * @param fileName
     * @return
     * @author zhangqy
     * @date 2016年1月20日下午4:07:03
     */
    public static File createFile(String folderName, String fileName,
                                  boolean isRename) {
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        if (StringUtils.isNotEmpty(folderName)) {
            if (!folderName.startsWith(SD_DIR)
                    && !folderName.startsWith(DATA_DIR)) {
                if (STORAGE_STATE.equals(Environment.MEDIA_MOUNTED)) {
                    folderName = SD_DIR + folderName;
                } else {
                    folderName = DATA_DIR + folderName;
                }
            }
        }
        int i = 0;
        if (isRename) {
            String fileExt = getFileExt(fileName);// 文件后缀名称
            while (true) {
                File sameFile = new File(folderName, fileName);
                if (sameFile.exists()) {
                    i++;
                    String fileNameStart = getFileNameStart(fileName);// 去除文件数标记的文件名前缀
                    fileName = fileNameStart + "_" + i + "." + fileExt;
                } else {
                    break;
                }
            }
        }
        File folder = createFolder(folderName);
        File tagFile = new File(folder, fileName);
        if (!tagFile.exists()) {
            try {
                tagFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tagFile;
    }

    /**
     * <p>
     * Title: createFolder
     * </p>
     * <p>
     * Description: 创建文件夹
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param folderName 绝对路径
     * @return
     * @author zhangqy
     * @date 2016年1月20日下午4:06:26
     */
    public static File createFolder(String folderName) {
        if (StringUtils.isEmpty(folderName)) {
            if (STORAGE_STATE.equals(Environment.MEDIA_MOUNTED)) {
                folderName = SD_DIR;
            } else {
                folderName = DATA_DIR;
            }
        }
        File file = new File(folderName);
        if (!file.exists()) {
            boolean flag = file.mkdirs();
            if (flag) {
                return file;
            } else {
                return null;
            }
        }
        return file;
    }

    /**
     * <p>
     * Title: createFolderAuto
     * </p>
     * <p>
     * Description: 自动选择存储路径。
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @tags @param folderName “/xwc1125/pic”
     * @tags @return
     * @author xwc1125
     * @date Jul 22, 20165:01:37 PM
     */
    public static File createFolderAuto(String folderName) {
        String folderPath = "";
        if (STORAGE_STATE.equals(Environment.MEDIA_MOUNTED)) {
            folderPath = SD_DIR;
        } else {
            folderPath = DATA_DIR;
        }
        if (StringUtils.isNotEmpty(folderName)) {
            folderPath = folderPath + "/" + folderName;
        }
        File file = new File(folderPath);
        if (!file.exists()) {
            boolean flag = file.mkdirs();
            if (flag) {
                return file;
            } else {
                return null;
            }
        }
        return file;
    }


    /**
     * <p>
     * Title: getFileExt
     * </p>
     * <p>
     * Description: 获取文件的后缀名
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @tags @param file
     * @tags @return
     * @author xwc1125
     * @date Jul 25, 20169:42:49 AM
     */
    public static String getFileExt(File file) {
        String fileName = file.getName();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return prefix;
    }

    public static String getFileExt(String fileName) {
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return prefix;
    }

    /**
     * 3423_1.apk 获取文件名称的前缀
     */
    public static String getFileNameStart(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        int indexOf = prefix.indexOf("_");
        if (indexOf > 0) {
            prefix = prefix.substring(0, indexOf);
        }
        return prefix;
    }

    private static HashMap<String, String> MIMEHashMap;

    @SuppressLint("DefaultLocale")
    public static String getIMMEType(String extName) {
        if (MIMEHashMap == null) {
            MIMEHashMap = getMIMEHashMap();
        }
        if (StringUtils.isEmpty(extName)) {
            extName = "";
        }
        String type = MIMEHashMap.get("." + extName.toLowerCase().toString());
        if (StringUtils.isEmpty(type)) {
            type = "*/*";
        }
        return type;
    }

    /**
     * 获取附件的类型
     */

    private static HashMap<String, String> getMIMEHashMap() {
        HashMap<String, String> dataHashMap = new HashMap<String, String>();
        // {后缀名，MIME类型}
        dataHashMap.put(".3gp", "video/3gpp");
        dataHashMap.put(".apk", "application/vnd.android.package-archive");
        dataHashMap.put(".asf", "video/x-ms-asf");
        dataHashMap.put(".avi", "video/x-msvideo");
        dataHashMap.put(".bin", "application/octet-stream");
        dataHashMap.put(".bmp", "image/bmp");
        dataHashMap.put(".c", "text/plain");
        dataHashMap.put(".class", "application/octet-stream");
        dataHashMap.put(".conf", "text/plain");
        dataHashMap.put(".cpp", "text/plain");
        dataHashMap.put(".doc", "application/msword");
        dataHashMap
                .put(".docx",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        dataHashMap.put(".xls", "application/vnd.ms-excel");
        dataHashMap
                .put(".xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        dataHashMap.put(".exe", "application/octet-stream");
        dataHashMap.put(".gif", "image/gif");
        dataHashMap.put(".gtar", "application/x-gtar");
        dataHashMap.put(".gz", "application/x-gzip");
        dataHashMap.put(".h", "text/plain");
        dataHashMap.put(".htm", "text/html");
        dataHashMap.put(".html", "text/html");
        dataHashMap.put(".jar", "application/java-archive");
        dataHashMap.put(".java", "text/plain");
        dataHashMap.put(".jpeg", "image/jpeg");
        dataHashMap.put(".jpg", "image/jpeg");
        dataHashMap.put(".js", "application/x-javascript");
        dataHashMap.put(".log", "text/plain");
        dataHashMap.put(".m3u", "audio/x-mpegurl");
        dataHashMap.put(".m4a", "audio/mp4a-latm");
        dataHashMap.put(".m4b", "audio/mp4a-latm");
        dataHashMap.put(".m4p", "audio/mp4a-latm");
        dataHashMap.put(".m4u", "video/vnd.mpegurl");
        dataHashMap.put(".m4v", "video/x-m4v");
        dataHashMap.put(".mov", "video/quicktime");
        dataHashMap.put(".mp2", "audio/x-mpeg");
        dataHashMap.put(".mp3", "audio/x-mpeg");
        dataHashMap.put(".mp4", "video/mp4");
        dataHashMap.put(".mpc", "application/vnd.mpohun.certificate");
        dataHashMap.put(".mpe", "video/mpeg");
        dataHashMap.put(".mpeg", "video/mpeg");
        dataHashMap.put(".mpg", "video/mpeg");
        dataHashMap.put(".mpg4", "video/mp4");
        dataHashMap.put(".mpga", "audio/mpeg");
        dataHashMap.put(".msg", "application/vnd.ms-outlook");
        dataHashMap.put(".ogg", "audio/ogg");
        dataHashMap.put(".pdf", "application/pdf");
        dataHashMap.put(".png", "image/png");
        dataHashMap.put(".pps", "application/vnd.ms-powerpoint");
        dataHashMap.put(".ppt", "application/vnd.ms-powerpoint");
        dataHashMap
                .put(".pptx",
                        "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        dataHashMap.put(".prop", "text/plain");
        dataHashMap.put(".rc", "text/plain");
        dataHashMap.put(".rmvb", "audio/x-pn-realaudio");
        dataHashMap.put(".rtf", "application/rtf");
        dataHashMap.put(".sh", "text/plain");
        dataHashMap.put(".tar", "application/x-tar");
        dataHashMap.put(".tgz", "application/x-compressed");
        dataHashMap.put(".txt", "text/plain");
        dataHashMap.put(".wav", "audio/x-wav");
        dataHashMap.put(".wma", "audio/x-ms-wma");
        dataHashMap.put(".wmv", "audio/x-ms-wmv");
        dataHashMap.put(".wps", "application/vnd.ms-works");
        dataHashMap.put(".xml", "text/plain");
        dataHashMap.put(".z", "application/x-compress");
        dataHashMap.put(".zip", "application/x-zip-compressed");
        dataHashMap.put("", "*/*");
        return dataHashMap;
    }

    /**
     * sd卡是否可用
     *
     * @return
     */
    public static boolean isSdCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * <p>
     * Title: getCacheRootPath
     * </p>
     * <p>
     * Description: 获取缓存的根目录
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @tags @param context
     * @tags @return
     * @author xwc1125
     * @date 2016年8月17日 下午1:41:36
     */
    public static String getCacheRootPath(Context context) {
        String cacheRootPath = "";
        try {
            if (isSdCardAvailable()) {
                // /sdcard/Android/data/<application package>/cache
                cacheRootPath = context.getExternalCacheDir().getPath();
            } else {
                // /data/data/<application package>/cache
                cacheRootPath = context.getCacheDir().getPath();
            }
        } catch (Exception e) {
            cacheRootPath = context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    /**
     * <p>
     * Title: getType
     * </p>
     * <p>
     * Description: 判断文件的类型
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param fileName
     * @return
     * @author zhangqy
     * @date 2016年9月19日 上午10:45:27
     */
    @SuppressLint("DefaultLocale")
    public static String getType(String fileName) {
        String ext = fileName;
        if (ext != null && ext.length() > 0) {
            ext = ext.toLowerCase();
            if (ext.endsWith(".gif") || ext.endsWith(".jpg")
                    || ext.endsWith(".jpeg") || ext.endsWith(".png")) {
                return "image";
            } else if (ext.endsWith(".mp3") || ext.endsWith(".m4a")
                    || ext.endsWith(".wav")) {
                return "audio";
            } else if (ext.endsWith(".mp4") || ext.endsWith(".3gp")
                    || ext.endsWith(".3gpp")) {
                return "video";
            } else if (ext.endsWith(".txt")) {
                return "text";
            } else if (ext.endsWith(".doc") || ext.endsWith(".docx")
                    || ext.endsWith(".xls")) {
                return "doc";
            }
        }
        return "";
    }

    /**
     * <p>
     * Title: getFileName
     * </p>
     * <p>
     * Description: 获取文件名称
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param path
     * @return
     * @author zhangqy
     * @date 2016年9月19日 上午10:45:41
     */
    public static String getFileName(String path) {
        if (path == null)
            return null;
        path = path.replaceAll("\\\\", "/").replaceAll("//", "/");
        int pos = path.lastIndexOf("/");
        if (pos > 0)
            return path.substring(pos + 1);
        return path;
    }

    /**
     * <p>
     * Title: getFileNameByUrl
     * </p>
     * <p>
     * Description: 视屏卡测试
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param path
     * @return
     * @author zhangqy
     * @date 2017年1月18日 下午3:55:58
     */
    public static String getFileNameByUrl(String path) {
        if (path == null)
            return null;
        String[] split = path.split("=");
        if (split == null || split.length < 1) {
            return null;
        }
        String name = split[split.length - 1];
        if (StringUtils.isNotEmpty(name) && !name.endsWith(".apk")) {
            name = name + ".apk";
        }
        return name;
    }

    /**
     * <p>
     * Title: getRootPath
     * </p>
     * <p>
     * Description: 获取根目录
     * </p>
     * <p>
     * 如果检测到有外部SDK，那么优先获取SDK的目录地址
     * </p>
     *
     * @tags @param context
     * @tags @return
     * @author xwc1125
     * @date 2016年8月18日 下午4:12:29
     */
    public static String getRootPath(Context context) {
        String rootPath = "";
        try {
            if (isSdCardAvailable()) {
                rootPath = SD_DIR;
            } else {
                rootPath = DATA_DIR;
            }
        } catch (Exception e) {
            rootPath = DATA_DIR;
        }
        return rootPath;
    }

    /**
     * <p>
     * Title: getAsssetsFile
     * </p>
     * <p>
     * Description: 获取assets中的文件，并复制到指定目录下
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @tags @param context
     * @tags @param assetFilePath assets下文件
     * @tags @param targetFilePath 目标文件目录
     * @tags @return
     * @tags @throws IOException
     * @author xwc1125
     * @date 2017年2月20日 下午1:43:15
     */
    public static File getAsssetsFile(Context context, String assetFilePath,
                                      String targetFilePath) throws IOException {
        AssetManager assetManager = context.getAssets();
        File dex = new File(targetFilePath);
        InputStream in = assetManager.open(assetFilePath);
        if (!dex.exists()) {
            OutputStream out = new FileOutputStream(dex);
            byte[] buffer = new byte[2048];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        return dex;
    }
}
