package com.ngo.ducquang.appspa.listVanHanh;

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
import com.ngo.ducquang.appspa.listDauTu.DauTuListener;
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

public class VanHanhActivity extends BaseActivity implements View.OnClickListener, DauTuListener
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;

    private List<UserApp> dataList = new ArrayList<>();
    private UserListAdapter adapter;

    public static final String IDINVEST = "idinvest";
    private int iDInvest;

    @Override
    protected int getContentView() {
        return R.layout.activity_dau_tu;
    }

    @Override
    protected void initView() {
        title.setText("Danh sách vận hành");
        fab.setOnClickListener(this);
        showIconBack();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new UserListAdapter(VanHanhActivity.this, getSupportFragmentManager(), dataList);
        adapter.setNew(true);
        adapter.setFrom(UserListAdapter.FROM_DAU_TU);
        adapter.setType(UserListAdapter.TYPE_VAN_HANH);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        showLoadingDialog();
        String token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");
        HashMap<String, String> params = new HashMap<>();
        params.put("Token", token);
        params.put("IDPosition", 5 + "");
        iDInvest = getIntent().getIntExtra(IDINVEST, 0);
        if (iDInvest != 0)
        {
            params.put("IDInvest", iDInvest + "");
        }
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
        if (idPosition == GlobalVariables.IS_DAU_TU)
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
                AddVanHanhDialog dauTuDialog = new AddVanHanhDialog();
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
