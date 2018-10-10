package com.ngo.ducquang.appspa.notification;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Toast;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.notification.model.Notification;
import com.ngo.ducquang.appspa.notification.model.ResponseNotification;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;

import java.util.HashMap;
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
    private LinearLayoutManager layoutManager;

    private HashMap<String, String> params = new HashMap<>();
    private int take = 5, skip = 1;
    private String token;

    @Override
    protected int getContentView() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initView()
    {
        showIconBack();
        title.setText("Thông báo");

        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");

        params.put("Token", token);
        params.put("Skip", skip + "");
        params.put("Take", take + "");
        showLoadingDialog();
        ApiService.Factory.getInstance().getListNotification(params).enqueue(getNotification());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1))
                {
                    loadMore();
                }
            }
        });
    }

    private void loadMore()
    {
        skip = skip + 1;
        params.clear();
        params.put("Token", token);
        params.put("Skip", skip + "");
        params.put("Take", take + "");
        showLoadingDialog();
        ApiService.Factory.getInstance().getListNotification(params).enqueue(getNotification());
    }

    private Callback<ResponseNotification> getNotification()
    {
        return new Callback<ResponseNotification>()
        {
            @Override
            public void onResponse(Call<ResponseNotification> call, Response<ResponseNotification> response)
            {
                try
                {
                    if (response.body().getStatus() == 1)
                    {
                        List<Notification> notifications = response.body().getData().getNotifications();

                        if (adapter == null)
                        {
                            adapter = new NotificationAdapter(NotificationActivity.this, notifications);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }
                        else
                        {
                            if (notifications.size() > 0)
                            {
                                adapter.addDataNotification(notifications);
                            }
                        }

                        hideLoadingDialog();
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
        };
    }

    @Override
    protected void initMenu(Menu menu) {}
}
