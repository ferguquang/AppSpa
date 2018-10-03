package com.ngo.ducquang.appspa.storageList.storeDetail.comment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.Discuss;
import com.ngo.ducquang.appspa.storageList.storeDetail.rate.RateAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ducqu on 10/3/2018.
 */

public class CommentFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private CommentAdapter adapter;
    private List<Discuss> discusses;
    private int idStore;

    @Override
    protected int getContentView() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView(View view)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new CommentAdapter(getContext(), discusses, idStore);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void setDiscusses(List<Discuss> discusses) {
        this.discusses = discusses;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }
}
