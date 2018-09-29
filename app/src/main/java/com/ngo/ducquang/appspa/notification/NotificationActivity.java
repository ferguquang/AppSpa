package com.ngo.ducquang.appspa.notification;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;

import butterknife.BindView;

/**
 * Created by ducqu on 9/21/2018.
 */

public class NotificationActivity extends BaseActivity
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private NotificationAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initView()
    {
        showIconBack();
        title.setText("Thông báo");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new NotificationAdapter();
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initMenu(Menu menu) {

    }
}
