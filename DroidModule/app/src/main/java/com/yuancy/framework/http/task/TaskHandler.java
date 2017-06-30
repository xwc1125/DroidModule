package com.yuancy.framework.http.task;

/**
 * 
 * <p>
 * Title: TaskHandler
 * </p>
 * <p>
 * Description: 任务处理
 * </p>
 * <p>
 * 
 * </p>
 * @author zhangqy
 * @date 2016年5月11日下午2:12:54
 *
 */
public interface TaskHandler {
    void cancel(); // 取消
    boolean isCancelled(); //是否已取消
}