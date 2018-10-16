package com.ngo.ducquang.appspa.base;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import com.ngo.ducquang.appspa.R;

/**
 * Created by ducqu on 9/23/2018.
 */

public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init()
    {
        this.setBackground(getResources().getDrawable(R.drawable.edt_bg_selector));
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), GlobalVariables.FONT_BASE);
            setTypeface(tf);
        }
    }
}
