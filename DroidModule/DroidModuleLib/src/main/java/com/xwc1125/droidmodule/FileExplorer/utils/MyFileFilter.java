package com.xwc1125.droidmodule.FileExplorer.utils;

import java.io.File;
import java.io.FileFilter;

/**
 * 文件过滤器
 *
 * @author xwc1125
 */
public class MyFileFilter implements FileFilter {

    private boolean isFile;

    public MyFileFilter(boolean isFile) {
        this.isFile = isFile;
    }

    @Override
    public boolean accept(File pathname) {
        if (pathname.getName().startsWith(".")) {
            return false;
        }
        if (isFile) {
            // 过滤掉所有以.开头的文件
            return true;
        } else {
            if (pathname.isDirectory()) {
                return true;
            } else {
                return false;
            }
        }
    }

}
