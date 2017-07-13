package com.xwc1125.driodutils.json;

import com.xwc1125.droidutils.json.JsonUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Class: com.xwc1125.driodutils.json <br>
 * Description: java.sql
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/23  08:34 <br>
 */
public class FF {
    private Date date1;
    private java.sql.Date date2;
    private Timestamp date3;

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public java.sql.Date getDate2() {
        return date2;
    }

    public void setDate2(java.sql.Date date2) {
        this.date2 = date2;
    }

    public Timestamp getDate3() {
        return date3;
    }

    public void setDate3(Timestamp date3) {
        this.date3 = date3;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this).toString();
    }
}
