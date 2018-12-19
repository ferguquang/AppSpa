package com.ngo.ducquang.appspa.listDauTu;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;
import com.ngo.ducquang.appspa.userList.UserListAdapter;
import com.ngo.ducquang.appspa.userList.model.ResponseGetListUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DauTuActivity extends BaseActivity implements View.OnClickListener, DauTuListener
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;

    private List<UserApp> dataList = new ArrayList<>();
    private UserListAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_dau_tu;
    }

    @Override
    protected void initView() {
        title.setText("Danh sách đầu tư");
        fab.setOnClickListener(this);
        showIconBack();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new UserListAdapter(DauTuActivity.this, getSupportFragmentManager(), dataList);
        adapter.setNew(true);
        adapter.setFrom(UserListAdapter.FROM_ADMIN);
        adapter.setType(UserListAdapter.TYPE_DAU_TU);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        showLoadingDialog();
        String token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");
        HashMap<String, String> params = new HashMap<>();
        params.put("Token", token);
        params.put("IDPosition", 4 + "");
        ApiService.Factory.getInstance().getListUser(params).enqueue(new Callback<ResponseGetListUser>()
        {
            @Override
            public void onResponse(@NonNull Call<ResponseGetListUser> call, @NonNull Response<ResponseGetListUser> response)
            {
                if (response.body().getStatus() == 1)
                {
                    adapter.updateData(response.body().getData().getUserApps());
                    hideLoadingDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGetListUser> call, @NonNull Throwable t) {
                hideLoadingDialog();
            }
        });

        int idPosition = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.POSITION_ID, 0);
        if (idPosition == GlobalVariables.IS_ADMIN)
        {
            fab.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initMenu(Menu menu) {
        hideMenu();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab:
            {
                AddDauTuDialog dauTuDialog = new AddDauTuDialog();
                dauTuDialog.show(getSupportFragmentManager(), dauTuDialog.getTag());

                break;
            }
        }
    }

    @Override
    public void addItem(UserApp userApp) {
        adapter.addItem(userApp);
    }

    @Override
    public void updateItem(UserApp userApp, int position) {
        adapter.updateItem(userApp, position);
    }
}
