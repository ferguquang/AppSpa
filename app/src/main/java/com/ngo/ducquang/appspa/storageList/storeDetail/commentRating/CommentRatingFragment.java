package com.ngo.ducquang.appspa.storageList.storeDetail.commentRating;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by ducqu on 9/27/2018.
 */

public class CommentRatingFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private boolean isRating;
    private boolean isComment;

    @Override
    protected int getContentView() {
        return R.layout.fragment_comment_rating_detail;
    }

    @Override
    protected void initView(View view) {

    }

    public void setRating(boolean rating) {
        isRating = rating;
    }

    public void setComment(boolean comment) {
        isComment = comment;
    }
}
