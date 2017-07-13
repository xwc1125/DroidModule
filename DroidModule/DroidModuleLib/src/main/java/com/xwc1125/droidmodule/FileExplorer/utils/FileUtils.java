package com.xwc1125.droidmodule.FileExplorer.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author xwc1125
 */
public class FileUtils {
    private static String TAG = "FileUtils";

    /**
     * 文件排序
     *
     * @param listFiles
     * @return
     */
    public static File[] sort(File[] listFiles) {
        if (listFiles != null) {
            List<File> list = Arrays.asList(listFiles);
            Collections.sort(list, new FileComparator());
            File[] array = list.toArray(new File[list.size()]);
            return array;
        } else {
            return null;
        }
    }

    static boolean isSuccess = false;

    public static boolean copy(String fromPath, String toPath) throws IOException {

        //1.将原始路径和目标路径转为File
        File fromFile = new File(fromPath);
        if (!fromFile.exists()) {
            isSuccess = false;
        }
        File toFile = new File(toPath);
        //2.如果目标路径不存在，则创建该路径
        if (!toFile.exists()) {
            toFile.mkdir();
        }
        //3.判断原始路径是一个目录还是一个文件，如果是一个文件，直接复制，
        //		递归遍历

        if (fromFile.isFile()) {
            isSuccess = copyFile(fromFile.getAbsolutePath(), toPath + fromFile.getName());
        } else if (fromFile.isDirectory()) {
            File[] listFiles = fromFile.listFiles();

            for (File file : listFiles) {
                if (file.isDirectory()) {
                    //4.如果是一个文件夹，得到该文件夹下的所有文件，需要将该文件夹的所有文件都复制，文件夹需要
                    //		递归遍历,因为是文件夹，所以最后都要加上"/"
                    copy(file.getAbsolutePath() + "/", toPath + file.getName() + "/");
                } else {
                    //5.是一个目录还是一个文件，如果是一个文件，直接复制，
                    isSuccess = copyFile(file.getAbsolutePath(), toPath + file.getName());
                }
            }
        }

        if (!isSuccess) {
            Log.d(TAG, "fail");
        }

        return isSuccess;
    }

    /**
     * 复制文件
     *
     * @param fromPath
     * @param toPath
     * @throws IOException
     */
    private static boolean copyFile(String fromPath, String toPath) {

        boolean isSuccess = true;

        InputStream is = null;
        OutputStream os = null;
        try {
            //1.将源文件路径和目标文件路径转为File对象
            File fromFile = new File(fromPath);
            File toFile = new File(toPath);
            //2.得到一个输入流和输出流
            is = new FileInputStream(fromFile);
            os = new FileOutputStream(toFile);
            //3.输入流读文件，输出流写文件，建立一个缓冲区，提高读写效率
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }

        } catch (FileNotFoundException e) {
            isSuccess = false;
            Log.e(TAG, "copyFile", e);
        } catch (IOException e) {
            isSuccess = false;
            Log.e(TAG, "copyFile", e);
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                isSuccess = false;
                Log.e(TAG, "copyFile", e);
            }
        }
        return isSuccess;
    }
}
