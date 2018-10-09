package com.ngo.ducquang.appspa.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by QuangND on 2/27/2018.
 */

public class DrawableHelper
{
    @NonNull
    Context mContext;
    @ColorRes
    private int mColor;
    private int customColor;
    private String colorString;
    private Drawable mDrawable;
    private Drawable mWrappedDrawable;

    public DrawableHelper(@NonNull Context context) {
        mContext = context;
    }

    public static DrawableHelper withContext(@NonNull Context context) {
        return new DrawableHelper(context);
    }

    public DrawableHelper withDrawable(@DrawableRes int drawableRes) {
        mDrawable = ContextCompat.getDrawable(mContext, drawableRes);
        return this;
    }

    public DrawableHelper withDrawable(@NonNull Drawable drawable) {
        mDrawable = drawable;
        return this;
    }

    @SuppressLint("ResourceAsColor")
    public DrawableHelper withColor(@ColorRes int colorRes) {
        mColor = ContextCompat.getColor(mContext, colorRes);
        return this;
    }

    public DrawableHelper customColor(String color)
    {
        colorString = color;
        return this;
    }

    public DrawableHelper customTint() {
        if (mDrawable == null) {
            throw new NullPointerException("mDrawable null");
        }

        if (StringUtilities.isEmpty(colorString)) {
            throw new IllegalStateException("colorString null");
        }

        mWrappedDrawable = mDrawable.mutate();
        mWrappedDrawable = DrawableCompat.wrap(mWrappedDrawable);
        DrawableCompat.setTint(mWrappedDrawable, Color.parseColor(colorString));
        DrawableCompat.setTintMode(mWrappedDrawable, PorterDuff.Mode.SRC_IN);

        return this;
    }

    @SuppressLint("ResourceAsColor")
    public DrawableHelper tint() {
        if (mDrawable == null) {
            throw new NullPointerException("mDrawable null");
        }

        if (mColor == 0) {
            throw new IllegalStateException("mColor no value");
        }

        mWrappedDrawable = mDrawable.mutate();
        mWrappedDrawable = DrawableCompat.wrap(mWrappedDrawable);
        DrawableCompat.setTint(mWrappedDrawable, mColor);
        DrawableCompat.setTintMode(mWrappedDrawable, PorterDuff.Mode.SRC_IN);

        return this;
    }

    @SuppressWarnings("deprecation")
    public void applyToBackground(@NonNull View view) {
        if (mWrappedDrawable == null) {
            throw new NullPointerException("drawable null");
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(mWrappedDrawable);
        } else {
            view.setBackgroundDrawable(mWrappedDrawable);
        }
    }

    public void applyTo(@NonNull ImageView imageView) {
        if (mWrappedDrawable == null) {
            throw new NullPointerException("drawable null");
        }

        imageView.setImageDrawable(mWrappedDrawable);
    }

    public void applyTo(@NonNull MenuItem menuItem) {
        if (mWrappedDrawable == null) {
            throw new NullPointerException("drawable null");
        }

        menuItem.setIcon(mWrappedDrawable);
    }

    public Drawable get() {
        if (mWrappedDrawable == null) {
            throw new NullPointerException("drawable null");
        }

        return mWrappedDrawable;
    }
}
