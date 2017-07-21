package com.xwc1125.droidui.webview.config;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/7/20  17:07 <br>
 */
public class JsConfig {
    /**
     * 图片点击的事件
     * <p>
     * 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
     * <p>
     * 如要点击一张图片在弹出的页面查看所有的图片集合,则获取的值应该是个图片数组
     */
    public static String WEB_JAVASCRIPT_IMAGECLICK = "(function(){" +
            "var objs = document.getElementsByTagName(\"img\");" +
            "for(var i=0;i<objs.length;i++)" +
            "{" +
            //图片点击的回调
            "objs[i].onclick=function(){" +
            "window." + Html5Config.JAVASCRIPT_BRIDGE + ".onImageClick(this.getAttribute(\"src\"),this.getAttribute(\"has_link\"));}" +
            "}" +
            "})();";
    /**
     * 内容点击的事件
     * <p>
     * 遍历所有的a节点,将节点里的属性传递过去(属性自定义,用于页面跳转)
     */
    public static String WEB_JAVASCRIPT_TEXTCLICK = "(function(){" +
            "var objs =document.getElementsByTagName(\"a\");" +
            "for(var i=0;i<objs.length;i++)" +
            "{" +
            "objs[i].onclick=function(){" +
            //内容点击的回调
            "window." + Html5Config.JAVASCRIPT_BRIDGE + ".onTextClick(this.getAttribute(\"type\"),this.getAttribute(\"item_pk\"));}" +
            "}" +
            "})();";
    /**
     * java和js的桥接
     */
    public static String WEB_JAVASCRIPT_BRIDGE = "(function() " +
            "{" +

            "if (window." + Html5Config.JAVASCRIPT_BRIDGE + ") {" +
            "return; " +
            "}    " +

            "var messagingIframe;" +
            "var sendMessageQueue = [];" +
            "var receiveMessageQueue = [];" +
            "var messageHandlers = {};" +

            "var CUSTOM_PROTOCOL_SCHEME = '" + Html5Config.DROID_OVERRIDE_SCHEMA_NAME + "';" +
            "var QUEUE_HAS_MESSAGE = '__QUEUE_MESSAGE__/';" +

            "var responseCallbacks = {};" +
            "var uniqueId = 1; " +

            "function _createQueueReadyIframe(doc) {" +
            "messagingIframe = doc.createElement('iframe');" +
            "messagingIframe.style.display = 'none';" +
            "doc.documentElement.appendChild(messagingIframe);" +
            "}    " +

            //set default messageHandler
            "function init(messageHandler) {" +
            "if (" + Html5Config.JAVASCRIPT_BRIDGE + "._messageHandler) {" +
            "throw new Error('" + Html5Config.JAVASCRIPT_BRIDGE + ".init called twice');" +
            "} " +
            Html5Config.JAVASCRIPT_BRIDGE + "._messageHandler = messageHandler;" +
            "var receivedMessages = receiveMessageQueue;" +
            "receiveMessageQueue = null; " +
            "for (var i = 0; i < receivedMessages.length; i++) {" +
            "            _dispatchMessageFromNative(receivedMessages[i]);" +
            "   }    " +
            "}    " +

            "function send(data, responseCallback) {" +
            "_doSend({" +
            "data: data" +
            "}, responseCallback);" +
            "}    " +

            "function registerHandler(handlerName, handler) {" +
            "messageHandlers[handlerName] = handler;" +
            "} " +

            "function callHandler(handlerName, data, responseCallback) { " +
            "_doSend({" +
            "handlerName: handlerName," +
            "data: data " +
            "}, responseCallback);" +
            "}    " +

            //sendMessage add message, 触发native处理 sendMessage
            "function _doSend(message, responseCallback) {" +
            "if (responseCallback) { " +
            "var requestId = 'cb_' + (uniqueId++) + '_' + new Date().getTime();            " +
            "responseCallbacks[requestId] = responseCallback;            " +
            "message.requestId = requestId;        " +
            "}        " +
            "sendMessageQueue.push(message);        " +
            "messagingIframe.src = CUSTOM_PROTOCOL_SCHEME + '://' + QUEUE_HAS_MESSAGE;    }    " +
            "function _fetchQueue() {        " +
            "var messageQueueString = JSON.stringify(sendMessageQueue);        " +
            "sendMessageQueue = [];        " +
            "messagingIframe.src = CUSTOM_PROTOCOL_SCHEME + '://return/_fetchQueue/' + encodeURIComponent(messageQueueString);   " +
            " }   " +
            " function _dispatchMessageFromNative(messageJSON) {        " +
            "setTimeout(function() {           " +
            " var message = JSON.parse(messageJSON);            " +
            "var responseCallback;            " +
            "if (message.responseId) {               " +
            " responseCallback = responseCallbacks[message.responseId];      " +
            "          if (!responseCallback) {            " +
            "        return;                " +
            "}                " +
            "responseCallback(message.responseData);   " +
            "             delete responseCallbacks[message.responseId];        " +
            "    } else {                " +
            "if (message.requestId) {         " +
            "           var callbackResponseId = message.requestId;  " +
            "                  responseCallback = function(responseData) {   " +
            "                     _doSend({                     " +
            "       responseId: callbackResponseId,                            responseData: responseData   " +
            "                     });                  " +
            "  };              " +
            "  }                var handler = " + Html5Config.JAVASCRIPT_BRIDGE + "._messageHandler;            " +
            "    if (message.handlerName) {                   " +
            " handler = messageHandlers[message.handlerName];             " +
            "   }                try {                   " +
            " handler(message.data, responseCallback);              " +
            " } catch (exception) {                   " +
            " if (typeof console != 'undefined') {       " +
            "                 console.log(\"" + Html5Config.JAVASCRIPT_BRIDGE + ": WARNING: javascript handler threw.\", message, exception);   " +
            "                 }              " +
            "  }         " +
            "   }       " +
            " });  " +
            "  }   " +
            " function _handleMessageFromNative(messageJSON) {     " +
            "   console.log(messageJSON);       " +
            " if (receiveMessageQueue) {     " +
            "       receiveMessageQueue.push(messageJSON);       " +
            " } else {           " +
            " _dispatchMessageFromNative(messageJSON);      " +
            "  }   " +
            " }   " +
            " var WebViewJavascriptBridge = window." + Html5Config.JAVASCRIPT_BRIDGE + " = {      " +
            "  init: init,       " +
            " send: send,        " +
            "registerHandler: registerHandler,      " +
            "  callHandler: callHandler,        " +
            "_fetchQueue: _fetchQueue,       " +
            " _handleMessageFromNative: _handleMessageFromNative   " +
            " };    " +
            "var doc = document;   " +
            " _createQueueReadyIframe(doc);   " +
            " var readyEvent = doc.createEvent('Events');    " +
            "readyEvent.initEvent('WebViewJavascriptBridgeReady');   " +
            " readyEvent.bridge = WebViewJavascriptBridge;    " +
            "doc.dispatchEvent(readyEvent);})();";
}
