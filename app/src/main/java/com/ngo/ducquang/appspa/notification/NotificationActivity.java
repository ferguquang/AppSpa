package com.ngo.ducquang.appspa.notification;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.notification.model.Notification;
import com.ngo.ducquang.appspa.notification.model.ResponseNotification;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        String token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");
        ApiService.Factory.getInstance().getListNotification(token).enqueue(new Callback<ResponseNotification>()
        {
            @Override
            public void onResponse(Call<ResponseNotification> call, Response<ResponseNotification> response)
            {
                try {
                    if (response.body().getStatus() == 1)
                    {
                        List<Notification> notifications = response.body().getData().getNotifications();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        adapter = new NotificationAdapter(NotificationActivity.this, notifications);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                }
                catch (Exception e)
                {
                    LogManager.tagDefault().error(e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseNotification> call, Throwable t) {
                LogManager.tagDefault().error(t.getMessage());
            }
        });
    }

    @Override
    protected void initMenu(Menu menu) {

    }
}
