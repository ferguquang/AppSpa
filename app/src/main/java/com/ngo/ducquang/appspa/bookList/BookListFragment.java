package com.ngo.ducquang.appspa.bookList;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;

import butterknife.BindView;

/**
 * Created by ducqu on 9/22/2018.
 */

public class BookListFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private BookListAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_book_list;
    }

    @Override
    protected void initView(View view) {
        showBackPressToolBar();

        title.setText("Danh sách cửa hàng");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new BookListAdapter();
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
