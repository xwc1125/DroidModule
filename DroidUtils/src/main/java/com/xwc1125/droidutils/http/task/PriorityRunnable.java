package com.xwc1125.droidutils.http.task;

/**
 * 
 * <p>
 * Title: PriorityRunnable
 * </p>
 * <p>
 * Description: 带优先级的Runable
 * </p>
 * <p>
 * 
 * </p>
 * @author zhangqy
 * @date 2016年5月11日下午2:25:21
 *
 */
public class PriorityRunnable extends PriorityObject<Runnable> implements Runnable {

    public PriorityRunnable(Priority priority, Runnable obj) {
        super(priority, obj);
    }

    @Override
    public void run() {
        this.obj.run();
    }
}
