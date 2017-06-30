package com.xwc1125.driodutils.json;

import com.xwc1125.droidutils.json.JsonUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Class: com.xwc1125.driodutils.json <br>
 * Description: java.math
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/23  08:33 <br>
 */
public class EE {
    private BigDecimal c;
    private BigDecimal[] c1;
    private BigInteger d;
    private BigInteger[] d1;

    public BigDecimal getC() {
        return c;
    }

    public void setC(BigDecimal c) {
        this.c = c;
    }

    public BigDecimal[] getC1() {
        return c1;
    }

    public void setC1(BigDecimal[] c1) {
        this.c1 = c1;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public BigInteger[] getD1() {
        return d1;
    }

    public void setD1(BigInteger[] d1) {
        this.d1 = d1;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this).toString();
    }
}
