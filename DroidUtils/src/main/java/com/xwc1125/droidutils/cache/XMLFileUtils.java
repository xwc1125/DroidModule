/**
 * <p>
 * Title: XMLFileUtils.java
 * </p>
 * <p>
 * Description: xml文件缓存
 * </p>
 * <p>
 * <p>
 * </p>
 *
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月18日 上午11:04:45
 * @version V1.0
 */
package com.xwc1125.droidutils.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.StringUtils;
import com.xwc1125.droidutils.UtilsConfig;

/**
 *
 * <p>
 * Title: XMLFileUtils
 * </p>
 * <p>
 * Description: xml缓存工具类
 * </p>
 * <p>
 *
 * </p>
 *
 * @author zhangqy
 * @date 2016年1月21日下午2:31:09
 *
 */
public class XMLFileUtils {
    private static final String TAG = XMLFileUtils.class.getName();
    public static final boolean isDebug = UtilsConfig.isDebug;
    /**
     * 默认的标志
     */
    private static String XML_TAG = "Settings";
    /**
     * xml文件对象
     */
    private File xmlFile;

    /**
     *
     * <p>
     * Title: XMLFileUtils
     * </p>
     * <p>
     * Description: 构造方法
     * </p>
     *
     * @param folderName
     *            ：文件路径
     * @param xmlName
     *            ：文件名称
     */
    public XMLFileUtils(String folderName, String xmlName) {
        String path = folderName + "/" + xmlName;
        xmlFile = new File(path.trim());
        File dir = xmlFile.getParentFile();// 获取上一级的文件夹
        if (dir.exists() || dir.mkdirs()) {
            try {
                if (!xmlFile.exists()) {
                    xmlFile.createNewFile();
                    createXmlFile();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     *
     * <p>
     * Title: createXmlFile
     * </p>
     * <p>
     * Description: 创建XML文件
     * </p>
     * <p>
     * 默认标志为“Settings”
     * </p>
     *
     * @tags @return 创建成功返回true，否则返回false
     *
     * @author xwc1125
     * @date 2015-11-26下午1:45:22
     */
    private boolean createXmlFile() {
        return createXmlFile(XML_TAG);
    }

    /**
     *
     * <p>
     * Title: createXmlFile
     * </p>
     * <p>
     * Description: 创建XML文件
     * </p>
     * <p>
     *
     * </p>
     *
     * @tags @param tag 起始和截止的标志
     * @tags @return 创建成功返回true，否则返回false
     *
     * @author xwc1125
     * @date 2015-11-26下午1:31:35
     */
    private boolean createXmlFile(String tag) {
        if (StringUtils.isNotEmpty(tag)) {
            XMLFileUtils.XML_TAG = tag;
        }
        OutputStream os;
        try {
            os = new FileOutputStream(xmlFile);
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(os, "utf-8");
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", tag);
            serializer.endTag("", tag);
            serializer.endDocument();
            os.flush();
            os.close();
            return true;
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return false;
    }

    /**
     *
     * <p>
     * Title: setValue
     * </p>
     * <p>
     * Description: 设置键值对
     * </p>
     * <p>
     *
     * </p>
     *
     * @tags @param tag 起始标识
     * @tags @param key 主键
     * @tags @param value 值
     *
     * @author xwc1125
     * @date 2015-11-26下午1:42:06
     */
    public void setValue(String key, String value) {
        try {
            // create a new document
            FileInputStream fis = new FileInputStream(xmlFile);
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(fis);
            Element root = document.getDocumentElement();
            // 查询重复节点
            NodeList nodeList = document.getElementsByTagName(key);
            Node oldElement = null;
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    oldElement = nodeList.item(i);
                }
            }
            // 创建新节点
            Element newElement = document.createElement(key);
            Text name = document.createTextNode(value);
            newElement.appendChild(name);
            if (oldElement != null) { // 已写入，则替换
                root.replaceChild(newElement, oldElement);
            } else { // 否则，添加
                root.appendChild(newElement);
            }
            // Write a document to a file
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Source source = new DOMSource(document);
            Transformer transformer = transformerFactory.newTransformer();
            OutputStream bout = new FileOutputStream(xmlFile);
            OutputStreamWriter out = new OutputStreamWriter(bout, "utf-8");
            StreamResult streamResult = new StreamResult(out);
            transformer.transform(source, streamResult);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
    }

    /**
     *
     * <p>
     * Title: getValue
     * </p>
     * <p>
     * Description: 根据key获取值
     * </p>
     * <p>
     *
     * </p>
     *
     * @tags @param key
     * @tags @return
     *
     * @author xwc1125
     * @date 2015-11-26下午1:51:59
     */
    public String getValue(String key) {
        String info = null;
        try {
            FileInputStream fis = new FileInputStream(xmlFile);
            XmlPullParser pullParser = Xml.newPullParser();
            pullParser.setInput(fis, "utf-8");
            int event = pullParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (key.equals(pullParser.getName())) {
                            info = pullParser.nextText();
                        }
                        break;
                }
                event = pullParser.next();
            }
            fis.close();
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return info;
    }

}
