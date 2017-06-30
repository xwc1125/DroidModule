package com.xwc1125.droidutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import android.annotation.SuppressLint;

import com.xwc1125.droidutils.log.logger.Logger;

/**
 * 
 * <p>
 * Title: LogUtils
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author xwc1125
 * @date 2015-5-30下午6:30:12
 */
public class LogUtils {
	/**
	 * 
	 * <p>
	 * Title: d
	 * </p>
	 * <p>
	 * Description: Send a DEBUG log message.
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param TAG Used to identify the source of a log message. It usually
	 *       identifies the class or activity where the log call occurs.
	 * @tags @param msg The message you would like logged.
	 * @tags @param isDebug whether log the msg
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午10:17:02
	 */
	public static void d(String TAG, String msg, Boolean isDebug, Object... args) {
		if (isDebug != null && isDebug && StringUtils.isNotEmpty(msg)) {
			Logger.init(TAG);
			Logger.d(msg, args);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: i
	 * </p>
	 * <p>
	 * Description: Send a INFO log message.
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param TAG Used to identify the source of a log message. It usually
	 *       identifies the class or activity where the log call occurs.
	 * @tags @param msg The message you would like logged.
	 * @tags @param isDebug whether log the msg
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午10:17:02
	 */
	public static void i(String TAG, String msg, Boolean isDebug, Object... args) {
		if (isDebug != null && isDebug && StringUtils.isNotEmpty(msg)) {
			Logger.init(TAG);
			Logger.i(msg, args);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: e
	 * </p>
	 * <p>
	 * Description: Send a ERROR log message.
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param TAG Used to identify the source of a log message. It usually
	 *       identifies the class or activity where the log call occurs.
	 * @tags @param msg The message you would like logged.
	 * @tags @param isDebug whether log the msg
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午10:17:02
	 */
	public static void e(String TAG, String msg, Boolean isDebug, Object... args) {
		if (isDebug != null && isDebug && StringUtils.isNotEmpty(msg)) {
			Logger.init(TAG);
			Logger.e(msg, args);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: e
	 * </p>
	 * <p>
	 * Description: Send a ERROR log message and log the exception.
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param TAG Used to identify the source of a log message. It usually
	 *       identifies the class or activity where the log call occurs.
	 * @tags @param msg The message you would like logged.
	 * @tags @param tr An exception to log
	 * @tags @param isDebug whether log the msg
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午10:17:02
	 */
	public static void e(String TAG, String msg, Throwable tr, Boolean isDebug, Object... args) {
		if (isDebug != null && isDebug && StringUtils.isNotEmpty(msg)) {
			Logger.init(TAG);
			Logger.e(tr, msg, args);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: v
	 * </p>
	 * <p>
	 * Description: Send a VERBOSE log message.
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param TAG Used to identify the source of a log message. It usually
	 *       identifies the class or activity where the log call occurs.
	 * @tags @param msg The message you would like logged.
	 * @tags @param isDebug whether log the msg
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午10:17:02
	 */
	public static void v(String TAG, String msg, Boolean isDebug, Object... args) {
		if (isDebug != null && isDebug && StringUtils.isNotEmpty(msg)) {
			Logger.init(TAG);
			Logger.v(msg, args);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: w
	 * </p>
	 * <p>
	 * Description: Send a WARN log message.
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param TAG Used to identify the source of a log message. It usually
	 *       identifies the class or activity where the log call occurs.
	 * @tags @param msg The message you would like logged.
	 * @tags @param isDebug whether log the msg
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午10:17:02
	 */
	public static void w(String TAG, String msg, Boolean isDebug, Object... args) {
		if (isDebug != null && isDebug && StringUtils.isNotEmpty(msg)) {
			Logger.init(TAG);
			Logger.w(msg, args);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: wtf
	 * </p>
	 * <p>
	 * Description: This method is synchronized in order to avoid messy of logs'
	 * order.
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param TAG Used to identify the source of a log message. It usually
	 *       identifies the class or activity where the log call occurs.
	 * @tags @param msg The message you would like logged.
	 * @tags @param isDebug whether log the msg
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:48:55
	 */
	public static void wtf(String TAG, String msg, Boolean isDebug, Object... args) {
		if (isDebug != null && isDebug && StringUtils.isNotEmpty(msg)) {
			Logger.init(TAG);
			Logger.wtf(msg, args);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: json
	 * </p>
	 * <p>
	 * Description: Formats the json content and print it
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param TAG Used to identify the source of a log message. It usually
	 *       identifies the class or activity where the log call occurs.
	 * @tags @param json the json content.
	 * @tags @param isDebug whether log the msg
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:51:07
	 */
	public static void json(String TAG, String json, Boolean isDebug) {
		if (isDebug != null && isDebug && StringUtils.isNotEmpty(json)) {
			Logger.init(TAG);
			Logger.json(json);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: xml
	 * </p>
	 * <p>
	 * Description: Formats the json content and print it
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param TAG Used to identify the source of a log message. It usually
	 *       identifies the class or activity where the log call occurs.
	 * @tags @param xml the xml content.
	 * @tags @param isDebug whether log the msg
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:51:03
	 */
	public static void xml(String TAG, String xml, Boolean isDebug) {
		if (isDebug != null && isDebug && StringUtils.isNotEmpty(xml)) {
			Logger.init(TAG);
			Logger.xml(xml);
		}
	}

	/**
	 * 日志存入文档的路径
	 */
	// private String filePath;
	/**
	 * 日期格式
	 */
	private SimpleDateFormat logFormat = null;
	/**
	 * 链表
	 */
	// private ConcurrentLinkedQueue<String> logQueue = null;
	/**
	 * 将队列中的数据写入到文件
	 */
	private LogFileRunnable logFileRunnable = null;
	/**
	 * 是否在运行中
	 */
	private static HashMap<String, LogInfo> isRunningMap;
	// private boolean isRun = false;
	private boolean isRunnable = false;
	/**
	 * 对象
	 */
	private static LogUtils instance;

	@SuppressLint("SimpleDateFormat")
	private LogUtils() {
		// init the propertis
		logFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss/SSS");
		isRunningMap = new HashMap<String, LogInfo>();
		// logQueue = new ConcurrentLinkedQueue<String>();
	}

	/**
	 * 
	 * <p>
	 * Title: getInstance
	 * </p>
	 * <p>
	 * Description: the singleton
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2017年2月24日 下午5:21:40
	 */
	public static LogUtils getInstance() {
		if (instance == null) {
			instance = new LogUtils();
		}
		return instance;
	}

	/**
	 * 
	 * <p>
	 * Title: start
	 * </p>
	 * <p>
	 * Description: start Thread
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param filePath the file path
	 * 
	 * @author xwc1125
	 * @date 2017年2月24日 下午5:22:40
	 */
	public void start(String filePath) {
		LogInfo logInfo = isRunningMap.get(filePath);
		if (logInfo == null) {
			logInfo = new LogInfo();
			logInfo.setLogFile(filePath);
			logInfo.setLogQueue(new ConcurrentLinkedQueue<String>());
			isRunningMap.put(filePath, logInfo);
		}
		if (!isRunnable) {
			logFileRunnable = new LogFileRunnable();
			new Thread(logFileRunnable).start();
			this.isRunnable = true;
		}

		// if (isRun == false) {
		// this.filePath = filePath;
		// logFileRunnable = new LogFileRunnable();
		// new Thread(logFileRunnable).start();
		// this.isRun = true;
		// }
	}

	/**
	 * 
	 * <p>
	 * Title: stop
	 * </p>
	 * <p>
	 * Description: stop the Thread
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags
	 * 
	 * @author xwc1125
	 * @date 2017年2月24日 下午5:23:16
	 */
	public void stop() {
		if (logFileRunnable != null) {
			logFileRunnable.stop();
		}
		this.isRunnable = false;
	}

	/**
	 * 
	 * <p>
	 * Title: clear
	 * </p>
	 * <p>
	 * Description: clear the logfile
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags
	 * 
	 * @author xwc1125
	 * @date 2017年2月24日 下午5:23:20
	 */
	public void clear() {
		try {
			if (isRunningMap != null) {
				for (Entry<String, LogInfo> entry : isRunningMap.entrySet()) {
					String key = entry.getKey();
					File logFile = new File(key);
					if (!logFile.exists()) {
						logFile.createNewFile();
					} else {
						FileOutputStream clearFos = new FileOutputStream(logFile, false);
						if (clearFos != null) {
							clearFos.write("".getBytes());
							clearFos.close();
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * <p>
	 * Title: write
	 * </p>
	 * <p>
	 * Description: write the message
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param msg
	 * 
	 * @author xwc1125
	 * @date 2017年2月24日 下午5:23:59
	 */
	public void write(final String filePath, final String msg) {
		new Thread() {
			@Override
			public void run() {
				String content = createContent(msg);
				LogInfo logInfo = isRunningMap.get(filePath);
				if (logInfo != null) {
					ConcurrentLinkedQueue<String> logQueue = logInfo.getLogQueue();
					logQueue.add(content + "\n\r");
				}
			}
		}.start();
	}

	/**
	 * 
	 * <p>
	 * Title: createContent
	 * </p>
	 * <p>
	 * Description: create the log content
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param msg
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2017年2月24日 下午5:24:19
	 */
	private String createContent(String msg) {
		StringBuffer logContent = new StringBuffer();
		logContent.append(logFormat.format(new Date()) + " ");
		logContent.append(msg);
		return logContent.toString();
	}

	/**
	 * 
	 * <p>
	 * Title: LogFileRunnable
	 * </p>
	 * <p>
	 * Description: 将队列中的数据写入到文件
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2017年2月24日 下午5:24:37
	 *
	 */
	class LogFileRunnable implements Runnable {
		// private HashMap<String, LogInfo> isRunningMap = new HashMap<String,
		// LogInfo>();
		// File logFile = new File(filePath);

		public LogFileRunnable() {
		}

		public void run() {
			try {
				while (true) {// 循环监听
					if (isRunningMap != null) {
						for (Entry<String, LogInfo> entry : isRunningMap.entrySet()) {
							String key = entry.getKey();

							File logFile = new File(key);
							if (!logFile.exists()) {
								if (!logFile.getParentFile().exists()) {
									logFile.getParentFile().mkdirs();
								}
								logFile.createNewFile();
							}

							LogInfo logInfo = entry.getValue();
							if (logInfo == null) {
								continue;
							}
							ConcurrentLinkedQueue<String> logQueue = logInfo.getLogQueue();
							if (!logQueue.isEmpty()) {
								FileOutputStream out = null;
								try {
									out = new FileOutputStream(key, true);
									out.write(logQueue.poll().getBytes("UTF-8"));
									out.flush();
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									try {
										if (out != null) {
											out.close();
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public void stop() {
			try {
				if (isRunningMap != null) {
					for (Entry<String, LogInfo> entry : isRunningMap.entrySet()) {
						if (entry != null) {
							LogInfo logInfo = entry.getValue();
							String logFile = logInfo.getLogFile();
							ConcurrentLinkedQueue<String> logQueue = logInfo.getLogQueue();
							// logInfo.setIsRun(false);
							if (!logQueue.isEmpty()) {
								FileOutputStream out = null;
								try {
									out = new FileOutputStream(logFile, true);
									out.write(logQueue.poll().getBytes("UTF-8"));
									out.flush();
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									try {
										if (out != null) {
											out.close();
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							isRunningMap.put(logFile, logInfo);
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	class LogInfo {
		private String logFile;
		private ConcurrentLinkedQueue<String> logQueue;

		/**
		 * <p>
		 * Title:
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 */
		public LogInfo() {
			super();
		}

		/**
		 * <p>
		 * Title:
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param logFile
		 * @param logQueue
		 */
		public LogInfo(String logFile, ConcurrentLinkedQueue<String> logQueue) {
			super();
			this.logFile = logFile;
			this.logQueue = logQueue;
		}

		/**
		 * @return the logFile
		 */
		public String getLogFile() {
			return logFile;
		}

		/**
		 * @param logFile
		 *            the logFile to set
		 */
		public void setLogFile(String logFile) {
			this.logFile = logFile;
		}

		/**
		 * @return the logQueue
		 */
		public ConcurrentLinkedQueue<String> getLogQueue() {
			return logQueue;
		}

		/**
		 * @param logQueue
		 *            the logQueue to set
		 */
		public void setLogQueue(ConcurrentLinkedQueue<String> logQueue) {
			this.logQueue = logQueue;
		}

	}
}
