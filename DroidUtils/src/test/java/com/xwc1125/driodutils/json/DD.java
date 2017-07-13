package com.xwc1125.driodutils.json;

import com.xwc1125.droidutils.json.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Class: com.xwc1125.driodutils.json <br>
 * Description: java.util
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/23  08:16 <br>
 */
public class DD {

    private List<String> list;
    private ArrayList<Integer> arrayList;

    private TreeMap<String, String> treeMap;
    private HashMap<Integer, Integer> hashMap;
    private Map map;


    private LinkedList linkedList;
    private LinkedHashMap linkedHashMap;
    private LinkedHashSet linkedHashSet;
    private HashSet hashSet;
    private Hashtable hashtable;
    private Set set;
    private TreeSet treeSet;
    private Vector vector;

    private List<BB> bbList;


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

    public ArrayList<Integer> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Integer> arrayList) {
        this.arrayList = arrayList;
    }

    public HashMap<Integer, Integer> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<Integer, Integer> hashMap) {
        this.hashMap = hashMap;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public LinkedList getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList linkedList) {
        this.linkedList = linkedList;
    }

    public LinkedHashMap getLinkedHashMap() {
        return linkedHashMap;
    }

    public void setLinkedHashMap(LinkedHashMap linkedHashMap) {
        this.linkedHashMap = linkedHashMap;
    }

    public LinkedHashSet getLinkedHashSet() {
        return linkedHashSet;
    }

    public void setLinkedHashSet(LinkedHashSet linkedHashSet) {
        this.linkedHashSet = linkedHashSet;
    }

    public HashSet getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet hashSet) {
        this.hashSet = hashSet;
    }

    public Hashtable getHashtable() {
        return hashtable;
    }

    public void setHashtable(Hashtable hashtable) {
        this.hashtable = hashtable;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public TreeSet getTreeSet() {
        return treeSet;
    }

    public void setTreeSet(TreeSet treeSet) {
        this.treeSet = treeSet;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    public List<BB> getBbList() {
        return bbList;
    }

    public void setBbList(List<BB> bbList) {
        this.bbList = bbList;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this).toString();
    }
}
