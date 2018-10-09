package com.ngo.ducquang.appspa.report.byAddress;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.report.byStore.ByStoreReportAdapter;
import com.ngo.ducquang.appspa.report.model.DataReportByAddress;
import com.ngo.ducquang.appspa.report.model.DataReportByStore;
import com.ngo.ducquang.appspa.report.model.ResponseReportByAddress;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 10/9/2018.
 */

public class ByAddressReportFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private ByAddressReportAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_report_by_address;
    }

    @Override
    protected void initView(View view)
    {
        String token = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, "");
        HashMap<String, String> params = new HashMap<>();
        params.put("Token", token);
        params.put("IDProvince", token);
        params.put("IDDistrict", token);
        showLoadingDialog();
        ApiService.Factory.getInstance().reportByAddress(params).enqueue(new Callback<ResponseReportByAddress>()
        {
            @Override
            public void onResponse(Call<ResponseReportByAddress> call, Response<ResponseReportByAddress> response)
            {
                if (response.body().getStatus() == 1)
                {
                    DataReportByAddress dataReportByAddress = response.body().getData();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    adapter = new ByAddressReportAdapter(getContext(), dataReportByAddress);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                }

                hideLoadingDialog();
            }

            @Override
            public void onFailure(Call<ResponseReportByAddress> call, Throwable t) {
                hideLoadingDialog();
                showToast(t.getMessage(), GlobalVariables.TOAST_ERRO);
            }
        });
    }
}
