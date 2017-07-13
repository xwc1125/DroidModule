/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xwc1125.droidutils.http.handler;

/**
 * 
 * <p>
 * Title: RequestCallBackHandler
 * </p>
 * <p>
 * Description: 文件下载进度回调
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年8月16日 下午6:09:46
 * 
 */
public interface RequestCallBackHandler {
	/**
	 * 
	 * <p>
	 * Title: updateProgress
	 * </p>
	 * <p>
	 * Description: 更新下载进度
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param total
	 *            ：总大小
	 * @param current
	 *            ：当前已下载大小
	 * @param forceUpdateUI
	 *            ：是否强制更新UI
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月16日 下午6:10:09
	 */
	boolean updateProgress(long total, long current, boolean forceUpdateUI);
}
