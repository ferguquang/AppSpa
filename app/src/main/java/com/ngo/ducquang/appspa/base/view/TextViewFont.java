package com.ngo.ducquang.appspa.base.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ngo.ducquang.appspa.base.GlobalVariables;

/**
 * Created by ducqu on 10/13/2018.
 */

public class TextViewFont extends TextView {
    public TextViewFont(Context context) {
        super(context);
        init(context);
    }

    public TextViewFont(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewFont(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), GlobalVariables.FONT_BASE);
            setTypeface(tf);
        }
    }

    public void setTextBold()
    {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), GlobalVariables.FONT_BASE_BOLD);
        setTypeface(tf);
    }
}
