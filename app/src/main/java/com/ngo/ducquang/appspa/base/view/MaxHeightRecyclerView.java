package com.ngo.ducquang.appspa.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ngo.ducquang.appspa.base.Manager;

import butterknife.internal.Utils;

/**
 * Created by ducqu on 10/18/2018.
 */

public class MaxHeightRecyclerView extends RecyclerView {
    public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        heightSpec = MeasureSpec.makeMeasureSpec(Manager.dpToPx(240), MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }
}
