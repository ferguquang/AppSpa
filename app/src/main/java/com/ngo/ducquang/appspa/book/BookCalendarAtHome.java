package com.ngo.ducquang.appspa.book;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.Share;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ducqu on 9/25/2018.
 */

public class BookCalendarAtHome extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private BookOptionAdapter adapter;
    private List<Category> dataList;

    @Override
    protected int getContentView() {
        return R.layout.fragment_book_calendar_at_home;
    }

    @Override
    protected void initView(View view)
    {
        showBackPressToolBar();
        title.setText("Đặt lịch tại nhà");

        dataList = Share.getInstance().categoryList;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        adapter = new BookOptionAdapter(getActivity(), dataList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
