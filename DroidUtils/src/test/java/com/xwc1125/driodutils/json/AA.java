package com.xwc1125.driodutils.json;

import com.xwc1125.droidutils.json.JsonUtils;

/**
 * Class: com.xwc1125.driodutils.json <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/21  15:42 <br>
 */
public class AA {
    private String a;

    private int b;
    private Integer b1;

    private float c;
    private Float c1;

    private boolean d;
    private Boolean d1;

    private byte e;
    private Byte e1;

    private char f;
    private Character f1;

    private short g;
    private Short g1;

    private long h;
    private Long h1;

    private double i;
    private Double i1;


    private BB bb;

    public AA() {
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

    public BB getBb() {
        return bb;
    }

    public void setBb(BB bb) {
        this.bb = bb;
    }

    public boolean isD() {
        return d;
    }

    public void setD(boolean d) {
        this.d = d;
    }

    public Boolean getD1() {
        return d1;
    }

    public void setD1(Boolean d1) {
        this.d1 = d1;
    }

    public byte getE() {
        return e;
    }

    public void setE(byte e) {
        this.e = e;
    }

    public Byte getE1() {
        return e1;
    }

    public void setE1(Byte e1) {
        this.e1 = e1;
    }

    public char getF() {
        return f;
    }

    public void setF(char f) {
        this.f = f;
    }

    public Character getF1() {
        return f1;
    }

    public void setF1(Character f1) {
        this.f1 = f1;
    }

    public short getG() {
        return g;
    }

    public void setG(short g) {
        this.g = g;
    }

    public Short getG1() {
        return g1;
    }

    public void setG1(Short g1) {
        this.g1 = g1;
    }

    public long getH() {
        return h;
    }

    public void setH(long h) {
        this.h = h;
    }

    public Long getH1() {
        return h1;
    }

    public void setH1(Long h1) {
        this.h1 = h1;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public Double getI1() {
        return i1;
    }

    public void setI1(Double i1) {
        this.i1 = i1;
    }

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
