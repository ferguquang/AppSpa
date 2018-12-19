package com.ngo.ducquang.appspa.userList;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.userList.model.ResponseGetListUser;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/28/2018.
 */

public class UserListActivity extends BaseActivity
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_list;
    }

    @Override
    protected void initView()
    {
        hideMenu();
        showIconBack();
        title.setText("Danh sách khách hàng");

        showLoadingDialog();
        String token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");
        HashMap<String, String> params = new HashMap<>();
        params.put("Token", token);
        ApiService.Factory.getInstance().getListUser(params).enqueue(new Callback<ResponseGetListUser>()
        {
            @Override
            public void onResponse(@NonNull Call<ResponseGetListUser> call, @NonNull Response<ResponseGetListUser> response)
            {
                if (response.body().getStatus() == 1)
                {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    UserListAdapter adapter = new UserListAdapter(UserListActivity.this, getSupportFragmentManager(), response.body().getData().getUserApps());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);

                    hideLoadingDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGetListUser> call, @NonNull Throwable t) {
                hideLoadingDialog();
            }
        });
    }

    @Override
    protected void initMenu(Menu menu) {}
}
