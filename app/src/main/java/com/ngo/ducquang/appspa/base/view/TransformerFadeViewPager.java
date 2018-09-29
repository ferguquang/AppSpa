package com.ngo.ducquang.appspa.base.view;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by QuangND on 12/6/2017.
 */

public class TransformerFadeViewPager implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(@NonNull View view, float position)
    {
        if (position < -1 || position > 1)
        {
            view.setAlpha(0);
        }
        else if (position <= 0 || position <= 1)
        {
            // Calculate alpha. Position is decimal in [-1,0] or [0,1]
            float alpha = (position <= 0) ? position + 1 : 1 - position;
            view.setAlpha(alpha);
        }
        else if (position == 0)
        {
            view.setAlpha(1);
        }
    }
}
