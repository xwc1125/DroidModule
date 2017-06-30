package com.xwc1125.ui.recycler;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by xwc1125 on 2017/4/27.
 */

public class Item implements Parcelable {
    public int imgId;
    public String desc;

    public Item() {
    }

    public Item(int imgId, String desc) {
        this.imgId = imgId;
        this.desc = desc;
    }

    protected Item(Parcel in) {
        imgId = in.readInt();
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imgId);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Item{" +
                "imgId=" + imgId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
