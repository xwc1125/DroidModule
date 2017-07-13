package com.xwc1125.droidui.language;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xwc1125.droidutils.StringUtils;

/**
 * Class: <br>
 * Description:
 * <p>
 * 使用说明：如果是在xml中使用此控件，请使用R.string.valus。
 * 如果是在代码中使用，需要使用AppTextView.setTextById (strId)
 * <p>
 * 步骤：
 * 1、在需要立即生效的界面注册EventBus<br>
 * EventBus.getDefault().register(this);<br>
 * 2、在需要立即生效的界面注销EventBus<br>
 *
 * @Override protected void onDestroy() {
 * super.onDestroy();
 * EventBus.getDefault().unregister(this);//反注册EventBus
 * }<br>
 * 3、在语言更改的位置：LanguageChangedEvent event = new LanguageChangedEvent();<br>
 * event.msg = "do it";<br>
 * EventBus.getDefault().post(event);<br>
 * 4、在需要立即生效的界面实现<br>
 * @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行<br>
 * public void onLanguageChangedEvent(LanguageChangedEvent event) {
 * ViewUtils.updateView(findViewById(android.R.id.content));
 * }
 * <p>
 * <p>
 * 因为语言更改后，普通的是需要重新setContentView才能切换语言。使用此控件是根据id去获取数据的，所以可以立即生效。
 * @Copyright: Copyright (c) 2017/6/28<br>
 * @date 2017/6/28 11:28<br>
 */
@SuppressLint("AppCompatCustomView")
public class AppTextView extends TextView implements LanguageChangableView {
    private int textId;//文字id
    private int hintId;//hint的id
    private int arrResId, arrResIndex;

    //  app:fontStyle="light" medium
    public AppTextView(Context context) {
        super(context);
        init(context, null);
    }

    public AppTextView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext, paramAttributeSet);
    }

    public AppTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext, paramAttributeSet);
    }

    /**
     * 初始化获取xml的资源id
     *
     * @param context
     * @param attributeSet
     */
    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            String textValue = attributeSet.getAttributeValue(ANDROIDXML, "text");
            if (!(textValue == null || textValue.length() < 2)) {
                //如果是 android:text="@string/testText"
                //textValue会是 @0x7f080000,去掉@号就是资源id
                textId = StringUtils.string2int(textValue.substring(1, textValue.length()));
            }

            String hintValue = attributeSet.getAttributeValue(ANDROIDXML, "hint");
            if (!(hintValue == null || hintValue.length() < 2)) {
                hintId = StringUtils.string2int(hintValue.substring(1, hintValue.length()));
            }
        }
    }

    @Override
    public void setTextById(@StringRes int strId) {
        this.textId = strId;
        setText(strId);
    }

    @Override
    public void setTextWithString(String text) {
        this.textId = 0;
        setText(text);
    }

    @Override
    public void setTextByArrayAndIndex(@ArrayRes int arrId, @StringRes int arrIndex) {
        arrResId = arrId;
        arrResIndex = arrIndex;
        String[] strs = getContext().getResources().getStringArray(arrId);
        setText(strs[arrIndex]);
    }

    @Override
    public void reLoadLanguage() {
        try {
            if (textId > 0) {
                setText(textId);
            } else if (arrResId > 0) {
                String[] strs = getContext().getResources().getStringArray(arrResId);
                setText(strs[arrResIndex]);
            }

            if (hintId > 0) {
                setHint(hintId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
