package com.ngo.ducquang.appspa.storageList.storeDetail.categories;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.service.ServiceAdminAdapter;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ducqu on 9/27/2018.
 */

public class CategoriesFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<Category> categoryList;
    private ServiceAdminAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_categories_detail;
    }

    @Override
    protected void initView(View view)
    {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new ServiceAdminAdapter(getContext(), categoryList, getFragmentManager(), true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
