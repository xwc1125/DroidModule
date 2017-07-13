/**
 * <p>
 * Title: BaseProtocol.java
 * </p>
 * <p>
 * Description:网络访问基础协议类
 * </p>
 * <p>
 * <p>
 * </p>
 *
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月17日 下午2:34:14
 * @version V1.0
 */

package com.yuancy.framework.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.json.JSONObject;

import android.content.Context;

import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.StringUtils;
import com.xwc1125.droidutils.app.AppUtils;
import com.xwc1125.droidutils.flexCache.entity.DataInfo;
import com.xwc1125.droidutils.http.HttpHandler;
import com.xwc1125.droidutils.http.HttpRequest;
import com.xwc1125.droidutils.http.RequestCallback;
import com.xwc1125.droidutils.http.entity.RequestInfo;
import com.xwc1125.droidutils.http.entity.ResponseInfo;
import com.xwc1125.droidutils.security.AESUtils;
import com.xwc1125.droidutils.security.MD5Utils;
import com.xwc1125.droidutils.sign.SignUtils;
import com.yuancy.framework.exception.ExcepionCode;

/**
 *
 * <p>
 * Title: BaseProtocol
 * </p>
 * <p>
 * Description:接口网络访问基类
 * </p>
 * <p>
 *
 * </p>
 *
 * @author xwc1125
 * @param <T>
 * @date 2016年2月22日下午4:51:52
 *
 */

public class BaseProtocol<T> {
    private static final String TAG = BaseProtocol.class.getName();
    private static final boolean isDebug = true;
    /**
     * 默认的请求重试次数
     */

    public static int COUNT_REQUEST_DEFALUT = 3;
    /**
     * 请求回调对象
     */

    private BaseCallBack<T> mCallBack;
    /**
     * 上下文
     */

    public Context mContext;
    /**
     *
     * <p>
     * Title: BaseProtocol
     * </p>
     * <p>
     * Description: 构造器
     * </p>
     */

    public BaseProtocol() {
    }


    /**
     *
     * <p>
     * Title:BaseProtocol
     * </p>
     * <p>
     * Description:构造器
     * </p>
     *
     * @param context
     */

    public BaseProtocol(Context context) {
        this.mContext = context;
    }

    /**
     *
     * <p>
     * Title:BaseProtocol
     * </p>
     * <p>
     * Description: 构造器
     * </p>
     *
     * @param context
     * @param callBack
     */

    public BaseProtocol(Context context, BaseCallBack<T> callBack) {
        this.mContext = context;
        this.mCallBack = callBack;
    }

    /**
	 *
	 * <p>
	 * Title: sendRequest
	 * </p>
	 * <p>
	 * Description: 发起网络访问请求
	 * </p>
	 * <p>
	 *
	 * </p>
	 *
	 * @param host
	 *            ：
	 * @param apiUrl
	 *            ：/api/netm/v3.0/gnmu
	 * @param params
	 *            ：请求参数
	 * @param fileList
	 *            ：要上传的数字短信文件附件集合
	 * @param callBack
	 *            ：请求结果回调
	 * @param filter
	 *            ：base64加密过滤器，默认有sign不进行base64加密，取号接口还会加入unikey不进行base64加密
	 * @param retryCount
	 *            ：请求失败后的重试次数，0：表示不重试
	 * @param method
	 *            ：请求方法GET or POST
	 * @return
	 *
	 * @author xwc1125
	 * @date 2016年8月17日 下午2:43:55
	 */
    public HttpHandler<T> sendRequest(String host, String apiUrl, TreeMap<String, Object> params,
                                      ArrayList<File> fileList, RequestCallback<T> callBack, List<String> filter, int retryCount,
                                      HttpRequest.HttpMethod method) {
        return sendRequest(host, apiUrl, params, null, fileList, callBack, filter, retryCount, method, false, null);
    }

    /**
     *
     * <p>
     * Title: sendRequest
     * </p>
     * <p>
     * Description: 发起网络访问请求
     * </p>
     * <p>
     *
     * </p>
     *
     * @param host
     *            ：
     * @param apiUrl
     *            ：
     * @param params
     *            ：请求参数
     * @param callBack
     *            ：请求结果回调
     * @param filter
     *            ：base64加密过滤器，默认有sign不进行base64加密，取号接口还会加入unikey不进行base64加密
     * @param retryCount
     *            ：请求失败后的重试次数，0：表示不重试
     * @param method
     *            ：请求方法GET or POST
     * @return
     *
     * @author xwc1125
     * @date 2016年8月17日 下午2:44:12
     */

    public HttpHandler<T> sendRequest(String host, String apiUrl, TreeMap<String, Object> params,
                                      RequestCallback<T> callBack, List<String> filter, int retryCount, HttpRequest.HttpMethod method) {
        return sendRequest(host, apiUrl, params, null, null, callBack, filter, retryCount, method, false, null);
    }

    /**
     *
     * <p>
     * Title: sendRequest
     * </p>
     * <p>
     * Description: TODO(describe the methods)
     * </p>
     * <p>
     *
     * </p>
     *
     * @tags @param host 域名或Ip+Port
     * @tags @param apiUrl Api地址
     * @tags @param params 请求参数
     * @tags @param fileParams
     * @tags @param callBack 请求结果回调
     * @tags @param filter
     *       base64加密过滤器，默认有sign不进行base64加密，取号接口还会加入unikey不进行base64加密
     * @tags @param retryCount 请求失败后的重试次数，0：表示不重试
     * @tags @param method 请求方法GET or POST
     * @tags @param isCache 是否进行method方法的缓存（GET方法在底层已经设置成了缓存）
     * @tags @param dataInfo
     *       业务核心数据。如果isCache=true，如果dataInfo!=null,那么dataInfo中的数据，除了r随机码外，其他参数+sim卡信息全部进入cacheKey中。如果dataInfo==null，只有sim卡信息进入cacheKey中
     * @tags @return
     *
     * @author xwc1125
     * @date 2017年2月17日 上午9:57:02
     */

    public HttpHandler<T> sendRequest(String host, String apiUrl, TreeMap<String, Object> params,
                                      RequestCallback<T> callBack, List<String> filter, int retryCount, HttpRequest.HttpMethod method, boolean isCache,
                                      DataInfo dataInfo) {
        return sendRequest(host, apiUrl, params, null, null, callBack, filter, retryCount, method, isCache, dataInfo);
    }

    /**
     *
     * <p>
     * Title: sendRequest
     * </p>
     * <p>
     * Description: TODO(describe the methods)
     * </p>
     * <p>
     *
     * </p>
     *
     * @tags @param host 域名或Ip+Port
     * @tags @param apiUrl Api地址
     * @tags @param params 请求参数
     * @tags @param fileParams 要上传的日志文件集合(数据流)【摒弃】
     * @tags @param fileList 要上传的数字短信文件附件集合(直接附件上传)
     * @tags @param callBack 请求结果回调
     * @tags @param filter
     *       base64加密过滤器，默认有sign不进行base64加密，取号接口还会加入unikey不进行base64加密
     * @tags @param retryCount 请求失败后的重试次数，0：表示不重试
     * @tags @param method 请求方法GET or POST
     * @tags @param isCache 是否进行method方法的缓存（GET方法在底层已经设置成了缓存）
     * @tags @param dataInfo
     *       业务核心数据。如果isCache=true，如果dataInfo!=null,那么dataInfo中的数据，除了r随机码外，其他参数+sim卡信息全部进入cacheKey中。如果dataInfo==null，只有sim卡信息进入cacheKey中
     * @tags @return
     *
     * @author xwc1125
     * @date 2017年2月17日 上午9:48:50
     */

    public HttpHandler<T> sendRequest(String host, String apiUrl, TreeMap<String, Object> params,
                                      TreeMap<String, FileInputStream> fileParams, ArrayList<File> fileList, RequestCallback<T> callBack,
                                      List<String> filter, int retryCount, HttpRequest.HttpMethod method, boolean isCache, DataInfo dataInfo) {
        HttpHandler<T> handler = null;
        try {
            TreeMap<String, Object> treeMap = baseParams(params);
            HashMap<String, Object> headerMap = new HashMap<String, Object>();
            if (treeMap != null) {
                String signStr = SignUtils.sign(apiUrl, treeMap, "md5key");
                if (StringUtils.isNotEmpty(signStr)) {
                    treeMap.put("sign", signStr);
                    headerMap.put("sign", signStr);
                    headerMap.put("api-protocol", "1.1");
                }
                // 配置请求信息
                RequestInfo<T> requestInfo = new RequestInfo<T>();
                requestInfo.setFilter(filter);
                requestInfo.setRequestMethod(method.toString());
                requestInfo.setUrl(host + apiUrl);
                requestInfo.setCallBack(callBack);
                requestInfo.setRetryCount(retryCount);
                requestInfo.setParams(treeMap);
                requestInfo.setFileParams(fileParams);
                requestInfo.setFileList(fileList);
                requestInfo.setHeaderMap(headerMap);
                StringBuffer cacheKeyBuffer = new StringBuffer();
//                if (phoneInfo != null && isCache) {
//                    ArrayList<KInfo> kInfos = phoneInfo.getSmis();
//                    if (kInfos != null && kInfos.size() > 0) {
//                        for (int i = 0, len = kInfos.size(); i < len; i++) {
//                            KInfo kInfo = kInfos.get(i);
//                            cacheKeyBuffer.append(kInfo.getIs()).append(kInfo.getIc());
//                        }
//                    }
//                }
//                if (dataInfo != null) {
//                    Iterator<String> iterator = dataInfo.keys();
//                    while (iterator.hasNext()) {
//                        String key = (String) iterator.next();
//                        Object value = dataInfo.get(key);
//                        if (key != null && value != null && !key.equals("r")) {
//                            cacheKeyBuffer.append("&").append(key).append("=").append(value + "");
//                        }
//                    }
//                }
                String cacheKey = cacheKeyBuffer.toString();
                if (StringUtils.isNotEmpty(cacheKey)) {
                    cacheKey = host + apiUrl + "/" + MD5Utils.encrypt(cacheKey);
                    requestInfo.setCacheKey(cacheKey);
                    requestInfo.setCacheEnable(cacheKey, method, isCache);
                }
                HttpRequest<T> request = new HttpRequest<T>(mContext, requestInfo);
                handler = new HttpHandler<T>(request);
                handler.execute(request);
            }
        } catch (Exception e) {
            setFailedResult(1, ExcepionCode.ERROR_NET, "网络访问出错");
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return handler;
    }

    /**
     *
     * <p>
     * Title: baseParams
     * </p>
     * <p>
     * Description:网络访问基本参数
     * </p>
     * <p>
     *
     * </p>
     *
     * @return
     *
     * @author xwc1125
     * @date 2016年1月21日上午11:57:30
     */

    private TreeMap<String, Object> baseParams(TreeMap<String, Object> params) {
        if (params == null) {
            params = new TreeMap<String, Object>();
        }
        return params;
    }

    /**
     *
     * <p>
     * Title: baseFilter
     * </p>
     * <p>
     * Description: 过滤基本参数不进行base64加密
     * </p>
     * <p>
     *
     * </p>
     *
     * @return
     *
     * @author xwc1125
     * @date 2016年3月29日上午9:15:21
     */

    public List<String> baseFilter() {
        List<String> baseFilter = new ArrayList<String>();
        baseFilter.add("sign");
        return baseFilter;
    }

    /**
     *
     * <p>
     * Title: normolRequestCallBack
     * </p>
     * <p>
     * Description: 网络访问请求callback
     * </p>
     * <p>
     * 内部callback,用于对网络请求的结果进行处理后再外传
     * </p>
     *
     * @return
     *
     * @author xwc1125
     * @date 2016年1月21日下午1:17:20
     */

    @SuppressWarnings("unchecked")
    public RequestCallback<T> normalRequestCallBack() {
        RequestCallback<String> normalRequestCallBack = new RequestCallback<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String response = responseInfo == null ? "" : responseInfo.getResponse();
                if (StringUtils.isNotEmpty(response)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("code");
                        int status = jsonObject.getInt("status");
                        if (code == 0) {
                            String object = "";
                            if (jsonObject.has("obj")) {
                                object = jsonObject.get("obj") + "";
                            }
                            setSucceedResult(status, (T) object);
                        } else {
                            String msg = "";
                            if (jsonObject.has("msg")) {
                                msg = jsonObject.getString("msg");
                            }
                            setFailedResult(code, status, msg);
                            return;
                        }
                        return;
                    } catch (Throwable e) {
                        setFailedResult(1, ExcepionCode.ERROR_DATA_FORMAT, "数据格式错误");
                        return;
                    }
                } else {
                    setFailedResult(1, ExcepionCode.ERROR_DATA_NULL, "数据为空");
                }
            }

            @Override
            public void onFailure(int code, Object msg) {
                setFailedResult(1, code, msg + "");
            }
        };
        return (RequestCallback<T>) normalRequestCallBack;
    }

    /**
     *
     * <p>
     * Title: mobileRequestCallBack
     * </p>
     * <p>
     * Description: 返回的成功数据是经过aes加密的
     * </p>
     * <p>
     * 内部callback,用于对网络请求的结果进行处理后再外传
     * </p>
     *
     * @tags @param needCache 是否需要进行数据的缓存。
     * @tags @return
     *
     * @author xwc1125
     * @date 2016年8月19日 下午2:16:11
     */

    @SuppressWarnings("unchecked")
    public RequestCallback<T> mobileRequestCallBack(final boolean needCache) {
        RequestCallback<String> mobileRequestCallBack = new RequestCallback<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String response = responseInfo == null ? null : responseInfo.getResponse();
                if (StringUtils.isNotEmpty(response)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("code");
                        int status = jsonObject.getInt("status");
                        if (code == 0) {// 成功
                            String obj = "";
                            if (jsonObject.has("obj")) {
                                obj = jsonObject.getString("obj");
                            }
                            if (StringUtils.isNotEmpty(obj)) {
                                String de_obj = AESUtils.decrypt(obj, "md5key");
                                if (StringUtils.isNotEmpty(de_obj)) {
                                    String[] arr = de_obj.split(";");
                                    String data = null;
                                    String appPk = null;
                                    if (arr.length > 1) {
                                        data = arr[0];
                                        appPk = arr[1];
                                    }
                                    String pk = AppUtils.getPackageName(mContext);
                                    if (StringUtils.isNotEmpty(appPk) && appPk.trim().equals(pk)) {
                                        setSucceedResult(status, (T) data);
                                    } else {
                                        setFailedResult(1, ExcepionCode.ERROR_PAKEAGE, "包名不匹配");
                                    }
                                    return;
                                }
                            } else {
                                setSucceedResult(status, null);
                                return;
                            }
                        } else {
                            String msg = "";
                            if (jsonObject.has("msg")) {
                                msg = jsonObject.getString("msg");
                            }
                            setFailedResult(code, status, msg);
                            return;
                        }
                    } catch (Throwable e) {
                        setFailedResult(1, ExcepionCode.ERROR_DATA_FORMAT, "数据格式出错");
                        LogUtils.e(TAG, e.getMessage(), isDebug);
                        return;
                    }
                }
                setFailedResult(1, ExcepionCode.ERROR_DATA_NULL, "数据为空");
            }

            @Override
            public void onFailure(int status, Object msg) {
                setFailedResult(1, status, msg + "");
            }
        };
        return (RequestCallback<T>) mobileRequestCallBack;
    }

    /**
     *
     * <p>
     * Title: setSucceedResult
     * </p>
     * <p>
     * Description: 返回结果
     * </p>
     * <p>
     *
     * </p>
     *
     * @param status
     * @param obj
     *
     * @author xwc1125
     * @date 2016年3月25日上午10:02:29
     */

    public void setSucceedResult(int status, T obj) {
        if (mCallBack != null) {
            mCallBack.onSuccess(status, obj);
            mCallBack = null;
        }
    }

    /**
     *
     * <p>
     * Title: setFailedResult
     * </p>
     * <p>
     * Description: 返回结果
     * </p>
     * <p>
     *
     * </p>
     *
     * @param code
     * @param status
     * @param msg
     *
     * @author xwc1125
     * @date 2016年5月18日下午6:18:58
     */

    public void setFailedResult(int code, int status, String msg) {
        if (mCallBack != null) {
            try {
                TempCallBack<T> tempCallBack = (TempCallBack<T>) mCallBack;
                tempCallBack.onFailure(code, status, msg);
            } catch (Throwable e) {
                mCallBack.onFailure(status, msg);
            }
            mCallBack = null;
        }
    }
}
