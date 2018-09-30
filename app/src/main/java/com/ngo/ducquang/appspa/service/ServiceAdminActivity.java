package com.ngo.ducquang.appspa.service;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.service.model.ResponseServiceAdmin;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/30/2018.
 */

public class ServiceAdminActivity extends BaseActivity implements DialogAddService.SendCategory, View.OnClickListener
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;

    private ServiceAdminAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_service_admin;
    }

    @Override
    protected void initView()
    {
        showIconBack();
        hideMenu();

        title.setText("Danh sách dịch vụ");
        String token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");
        showLoadingDialog();
        ApiService.Factory.getInstance().getListServiceAdmin(token).enqueue(new Callback<ResponseServiceAdmin>() {
            @Override
            public void onResponse(Call<ResponseServiceAdmin> call, Response<ResponseServiceAdmin> response)
            {
                if (response.body().getStatus() == 1)
                {
                    List<Category> categories = response.body().getData().getCategories();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    adapter = new ServiceAdminAdapter(getApplicationContext(), categories, getSupportFragmentManager());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                }

                hideLoadingDialog();
            }

            @Override
            public void onFailure(Call<ResponseServiceAdmin> call, Throwable t) {
                LogManager.tagDefault().error(t.getMessage());
                hideLoadingDialog();
            }
        });

        fab.setOnClickListener(this);
    }

    @Override
    protected void initMenu(Menu menu) {}

    @Override
    public void sendCategory(Category category, int position, boolean isUpdate)
    {
        if (isUpdate)
        {
            adapter.updateItem(category, position);
        }
        else
        {
            adapter.addStore(category);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab:
            {
                DialogAddService dialogAddService = new DialogAddService();
                dialogAddService.show(getSupportFragmentManager(), dialogAddService.getTag());
                break;
            }
        }
    }
}
