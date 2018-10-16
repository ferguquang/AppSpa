package com.ngo.ducquang.appspa.base;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.view.TextViewFont;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 9/21/2018.
 */

public class EmptyViewHolder extends RecyclerView.ViewHolder
{
    @BindView(R.id.textEmpty) TextViewFont textEmpty;
    @Nullable
    @BindView(R.id.imgEmpty) ImageView imgEmpty;
    @BindView(R.id.relativeLayoutEmpty) RelativeLayout relativeLayoutEmpty;
    @BindView(R.id.llContent) LinearLayout llContent;

    public EmptyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setTextEmpty(String text)
    {
        textEmpty.setText(text);
    }

    public void setRelativeLayoutEmpty(int visibility)
    {
        relativeLayoutEmpty.setVisibility(visibility);
    }

    public void setGravity()
    {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 64, 0, 0);
        llContent.setLayoutParams(lp);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llContent.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        llContent.setLayoutParams(params);
    }
}
