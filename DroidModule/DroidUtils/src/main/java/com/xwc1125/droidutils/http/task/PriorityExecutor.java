package com.xwc1125.droidutils.http.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * <p>
 * Title: PriorityExecutor
 * </p>
 * <p>
 * Description: 任务执行者
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年5月11日下午2:44:33
 * 
 */
public class PriorityExecutor implements Executor {

	private static final int CORE_POOL_SIZE = 5;
	private static final int MAXIMUM_POOL_SIZE = 256;
	private static final int KEEP_ALIVE_TIME = 1;

	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "PriorityExecutor #"
					+ mCount.getAndIncrement());
		}
	};

	private final BlockingQueue<Runnable> mPoolWorkQueue = new PriorityObjectBlockingQueue<Runnable>();
	private final ThreadPoolExecutor mThreadPoolExecutor;

	public PriorityExecutor() {
		this(CORE_POOL_SIZE);
	}

	public PriorityExecutor(int poolSize) {
		mThreadPoolExecutor = new ThreadPoolExecutor(poolSize,// 线程池维护线程的最少数量
				MAXIMUM_POOL_SIZE,// 线程池维护线程的最大数量
				KEEP_ALIVE_TIME,// 线程空闲最大时间 超过则退出
				TimeUnit.SECONDS,// 时间单位
				mPoolWorkQueue, sThreadFactory);
	}

	public int getPoolSize() {
		return mThreadPoolExecutor.getCorePoolSize();
	}

	public void setPoolSize(int poolSize) {
		if (poolSize > 0) {
			mThreadPoolExecutor.setCorePoolSize(poolSize);
		}
	}

	public boolean isBusy() {
		return mThreadPoolExecutor.getActiveCount() >= mThreadPoolExecutor
				.getCorePoolSize();
	}

	@Override
	public void execute(final Runnable r) {
		mThreadPoolExecutor.execute(r);
	}
}
