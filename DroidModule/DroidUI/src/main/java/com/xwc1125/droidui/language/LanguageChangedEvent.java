package com.xwc1125.droidui.language;

/**
 * Created by george.yang on 2016-4-27.
 */
public class LanguageChangedEvent {
    public String msg;

    public String onChanged() {
        return "msg:" + msg;
    }
}
