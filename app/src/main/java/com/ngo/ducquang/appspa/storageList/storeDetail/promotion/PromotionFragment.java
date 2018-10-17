package com.ngo.ducquang.appspa.storageList.storeDetail.promotion;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.Promotion;
import com.ngo.ducquang.appspa.storageList.storeDetail.rate.RateAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ducqu on 10/16/2018.
 */

public class PromotionFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private List<Promotion> promotions;
    private PromotionAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_promotion;
    }

    @Override
    protected void initView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new PromotionAdapter(getContext(), promotions);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}
