package com.ngo.ducquang.appspa.report.byAddress;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.getAddress.District;
import com.ngo.ducquang.appspa.base.getAddress.Province;
import com.ngo.ducquang.appspa.report.byStore.ByStoreReportAdapter;
import com.ngo.ducquang.appspa.report.model.DataReportByAddress;
import com.ngo.ducquang.appspa.report.model.DataReportByStore;
import com.ngo.ducquang.appspa.report.model.ResponseReportByAddress;

import java.util.HashMap;
import java.util.List;

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
    private SendListAddress sendListAddress;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendListAddress = (SendListAddress) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sendListAddress = null;
    }

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
        params.put("IDProvince", "");
        params.put("IDDistrict", "");
        showLoadingDialog();
        ApiService.Factory.getInstance().reportByAddress(params).enqueue(callback());
    }

    private Callback<ResponseReportByAddress> callback()
    {
        return new Callback<ResponseReportByAddress>() {
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

                    sendListAddress.sendLisAddress(dataReportByAddress.getProvinces(), dataReportByAddress.getDistricts());
                }

                hideLoadingDialog();
            }

            @Override
            public void onFailure(Call<ResponseReportByAddress> call, Throwable t) {
                hideLoadingDialog();
                showToast(t.getMessage(), GlobalVariables.TOAST_ERRO);
            }
        };
    }

    public void requestFilter(int idProvince, int idDictrict)
    {
        String token = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, "");
        HashMap<String, String> params = new HashMap<>();
        params.put("Token", token);
        if (idProvince > 0)
        {
            params.put("IDProvince", idProvince + "");
        }
        if (idDictrict > 0)
        {
            params.put("IDDistrict", idDictrict + "");
        }
        showLoadingDialog();
        ApiService.Factory.getInstance().reportByAddress(params).enqueue(callback());
    }

    public interface SendListAddress
    {
        void sendLisAddress(List<Province> provinces, List<District> districts);
    }
}
