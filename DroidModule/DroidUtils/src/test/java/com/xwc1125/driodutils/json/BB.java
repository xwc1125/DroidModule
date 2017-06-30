package com.xwc1125.driodutils.json;

import com.xwc1125.droidutils.json.JsonUtils;

import java.util.List;
import java.util.TreeMap;

/**
 * Class: com.xwc1125.driodutils.json <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/21  15:42 <br>
 */
public class BB {
    private String a;
    private int b;
    private Integer b1;
    private float c;
    private Float c1;

    private String[] dd;
    private int[] aaa;
    private List<String> list;
    private TreeMap<String, String> treeMap;

    public BB() {
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public Integer getB1() {
        return b1;
    }

    public void setB1(Integer b1) {
        this.b1 = b1;
    }

    public float getC() {
        return c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public Float getC1() {
        return c1;
    }

    public void setC1(Float c1) {
        this.c1 = c1;
    }


    public String[] getDd() {
        return dd;
    }

    public void setDd(String[] dd) {
        this.dd = dd;
    }

    public int[] getAaa() {
        return aaa;
    }

    public void setAaa(int[] aaa) {
        this.aaa = aaa;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public TreeMap<String, String> getTreeMap() {
        return treeMap;
    }

    public void setTreeMap(TreeMap<String, String> treeMap) {
        this.treeMap = treeMap;
    }

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
