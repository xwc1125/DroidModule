package com.xwc1125.driodutils.json;

import com.xwc1125.droidutils.json.JsonUtils;

/**
 * Class: com.xwc1125.driodutils.json <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/22  13:47 <br>
 */
public class CC {
    private int[] a1;
    private double[] a2;
    private float[] a3;
    private long[] a4;
    private boolean[] a5;
    private byte[] a6;
    private char[] a7;
    private short[] a8;
    private String[] b1;
    private Integer[] b2;
    private Double[] b3;
    private Float[] b4;
    private Long[] b5;
    private Boolean[] b6;
    private Byte[] b7;//111
    private Character[] b8;
    private Short[] b9;//111

    public CC() {
    }

    public int[] getA1() {
        return a1;
    }

    public void setA1(int[] a1) {
        this.a1 = a1;
    }

    public double[] getA2() {
        return a2;
    }

    public void setA2(double[] a2) {
        this.a2 = a2;
    }

    public float[] getA3() {
        return a3;
    }

    public void setA3(float[] a3) {
        this.a3 = a3;
    }

    public long[] getA4() {
        return a4;
    }

    public void setA4(long[] a4) {
        this.a4 = a4;
    }

    public boolean[] getA5() {
        return a5;
    }

    public void setA5(boolean[] a5) {
        this.a5 = a5;
    }

    public byte[] getA6() {
        return a6;
    }

    public void setA6(byte[] a6) {
        this.a6 = a6;
    }

    public char[] getA7() {
        return a7;
    }

    public void setA7(char[] a7) {
        this.a7 = a7;
    }

    public short[] getA8() {
        return a8;
    }

    public void setA8(short[] a8) {
        this.a8 = a8;
    }

    public String[] getB1() {
        return b1;
    }

    public void setB1(String[] b1) {
        this.b1 = b1;
    }

    public Integer[] getB2() {
        return b2;
    }

    public void setB2(Integer[] b2) {
        this.b2 = b2;
    }

    public Double[] getB3() {
        return b3;
    }

    public void setB3(Double[] b3) {
        this.b3 = b3;
    }

    public Float[] getB4() {
        return b4;
    }

    public void setB4(Float[] b4) {
        this.b4 = b4;
    }

    public Long[] getB5() {
        return b5;
    }

    public void setB5(Long[] b5) {
        this.b5 = b5;
    }

    public Boolean[] getB6() {
        return b6;
    }

    public void setB6(Boolean[] b6) {
        this.b6 = b6;
    }

    public Byte[] getB7() {
        return b7;
    }

    public void setB7(Byte[] b7) {
        this.b7 = b7;
    }

    public Character[] getB8() {
        return b8;
    }

    public void setB8(Character[] b8) {
        this.b8 = b8;
    }

    public Short[] getB9() {
        return b9;
    }

    public void setB9(Short[] b9) {
        this.b9 = b9;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this).toString();
    }
}
