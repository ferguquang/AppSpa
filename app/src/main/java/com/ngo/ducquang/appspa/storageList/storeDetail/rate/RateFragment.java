package com.ngo.ducquang.appspa.storageList.storeDetail.rate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;
import com.ngo.ducquang.appspa.storageList.StoreActivity;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.DataRate;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.Rating;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ducqu on 9/27/2018.
 */

public class RateFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private RateAdapter adapter;

    private boolean isRating;
    private List<Rating> dataList;

    @Override
    protected int getContentView() {
        return R.layout.fragment_rate;
    }

    @Override
    protected void initView(View view)
    {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new RateAdapter(getContext(), dataList);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void setRating(boolean rating) {
        isRating = rating;
    }

    public void setDataList(List<Rating> dataList) {
        this.dataList = dataList;
    }

    public void updateData(List<Rating> ratings) {
        adapter.updateData(ratings);
    }
}
