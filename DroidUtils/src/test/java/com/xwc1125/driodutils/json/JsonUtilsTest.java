package com.xwc1125.driodutils.json;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Class: com.xwc1125.driodutils.json <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/21  15:42 <br>
 */
public class JsonUtilsTest {
    @Test
    public void toJsonString() throws Exception {
        //=========基础类型=====
//        AA aa = new AA();
//        aa.setA("1");
//        aa.setB(2);
//        aa.setB1(3);
//        aa.setC(0.1f);
//        aa.setC1(0.2f);
//        aa.setD1(true);
//        aa.setD(false);
//        aa.setE((byte) 3);
//        aa.setE1(new Byte("3"));
//        aa.setF('1');
//        aa.setF1('u');
//        aa.setG(Short.parseShort("1"));
//        aa.setG1(Short.parseShort("1"));
//        aa.setH(1L);
//        aa.setH1(1L);
//        aa.setI(1D);
//        aa.setI1(0.1);
        //============list,map
        BB bb = new BB();
        bb.setA("BB");
        bb.setB(22);
        bb.setB1(33);
        bb.setC(0.3f);
        bb.setAaa(new int[]{1, 2, 3});
        bb.setDd(new String[]{"1", "2", "3"});
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        bb.setList(list);
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("11", "111");
        treeMap.put("22", "22");
        bb.setTreeMap(treeMap);
//
//        aa.setBb(bb);
//        System.out.print(aa.toString());

//        String jsonStr = aa.toString();
//        AA aa1 = (AA) JsonUtils.jsonToBean(jsonStr, AA.class);
//        System.out.print(aa1.toString());


//        byte[] b ={113,49};
//        String aa=new String(b);
//        System.out.println(aa);
//
//        int d = 97;
//        char e = (char)d;
//        System.out.println(e);

        //==========基础类型的数组==============
//        CC cc = new CC();
//        cc.setA1(new int[]{1, 2, 3});
//        cc.setA2(new double[]{0.0, 0.1});
//        cc.setA3(new float[]{0.01f, 0.22f});
//        cc.setA4(new long[]{0l, 1l});
//        cc.setA5(new boolean[]{true, false});
//        cc.setA6(new byte[]{'q', '1'});
//        cc.setA7(new char[]{'a', 's'});
//        cc.setA8(new short[]{0, 1});
//
//        cc.setB1(new String[]{"1", "2", "3"});
//        cc.setB2(new Integer[]{11, 22});
//        cc.setB3(new Double[]{0.11, 0.33});
//        cc.setB4(new Float[]{0.1f, 0.2f});
//        cc.setB5(new Long[]{0l, 1l});
//        cc.setB6(new Boolean[]{false, true});
//        cc.setB7(new Byte[]{'1', '2'});
//        cc.setB8(new Character[]{'1', '3'});
//        cc.setB9(new Short[]{0, 1});
//
//        String jsonStr2 = cc.toString();
//        CC cc1 = (CC) JsonUtils.jsonToBean(jsonStr2, CC.class);
//        System.out.print(cc1.toString());

        //================java.util
        DD dd = new DD();
        //list
        List<String> list2 = new ArrayList<>();
        list2.add("111");
        list2.add("222");
        dd.setList(list2);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        dd.setArrayList(arrayList);
        LinkedList linkedList = new LinkedList();
        linkedList.add(bb);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("1", bb);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(bb);

        //map
        TreeMap<String, String> treeMap2 = new TreeMap<>();
        treeMap2.put("11", "111");
        treeMap2.put("22", "22");
        dd.setTreeMap(treeMap2);
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(1, 2);
        hashMap.put(2, 3);
        dd.setHashMap(hashMap);
        Map map = new HashMap();
        map.put("1", 1);
        map.put("1", "2");
        dd.setMap(map);

        List<BB> list1 = new ArrayList<>();
        list1.add(bb);
        list1.add(bb);
        dd.setBbList(list1);


        String jsonStr3 = dd.toString();
//        DD dd1 = (DD) JsonUtils.jsonToBean(jsonStr3, DD.class);
//        System.out.print(dd1.toString());

        //=================java.math
//        EE ee = new EE();
//        BigDecimal bigDecimal = BigDecimal.valueOf(0.48);
//        ee.setC(bigDecimal);
//        ee.setC1(new BigDecimal[]{bigDecimal, bigDecimal});
//        BigInteger bigInteger = new BigInteger("1");
//        ee.setD(bigInteger);
//        ee.setD1(new BigInteger[]{bigInteger, bigInteger});
//        String jsonStr5 = ee.toString();
//        EE ee1 = (EE) JsonUtils.jsonToBean(jsonStr5, EE.class);
//        System.out.print(ee1.toString());

        //================java.sql
//        FF ff = new FF();
//        Date date = new Date();
//        ff.setDate1(date);
//        java.sql.Date date2 = new java.sql.Date(date.getTime());
//        ff.setDate2(date2);
//        ff.setDate3(new Timestamp(date.getTime()));
//        String jsonStr4 = ff.toString();
//        FF ff1 = (FF) JsonUtils.jsonToBean(jsonStr4, FF.class);
//        System.out.print(ff1.toString());

    }

}