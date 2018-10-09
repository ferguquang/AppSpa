package com.ngo.ducquang.appspa.report.byStore;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.modelStore.Store;
import com.ngo.ducquang.appspa.report.model.DataReportByStore;
import com.ngo.ducquang.appspa.report.model.ResponseReportByStore;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;
import com.ngo.ducquang.appspa.storageList.StoreActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 10/9/2018.
 */

public class ByStoreReportFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private ByStoreReportAdapter adapter;

    private HashMap<String, String> params = new HashMap<>();
    private SendStoreList sendStoreList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendStoreList = (SendStoreList) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sendStoreList = null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_report;
    }

    @Override
    protected void initView(View view)
    {
        String token = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, "");
        params.put("Token", token);
        params.put("IDStore", "");
        showLoadingDialog();
        ApiService.Factory.getInstance().reportByStore(params).enqueue(callbackReport());
    }

    private Callback<ResponseReportByStore> callbackReport()
    {
        return new Callback<ResponseReportByStore>()
        {
            @Override
            public void onResponse(Call<ResponseReportByStore> call, Response<ResponseReportByStore> response) {
                if (response.body().getStatus() == 1)
                {
                    DataReportByStore dataReportByStore = response.body().getData();

                    if (adapter == null)
                    {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        adapter = new ByStoreReportAdapter(getContext(), dataReportByStore);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                    else
                    {
                        adapter.updateData(dataReportByStore);
                    }

                    sendStoreList.sendStoreList(dataReportByStore.getStore());
                }

                hideLoadingDialog();
            }

            @Override
            public void onFailure(Call<ResponseReportByStore> call, Throwable t) {
                hideLoadingDialog();
                showToast(t.getMessage(), GlobalVariables.TOAST_ERRO);
            }
        };
    }

    public void sendRequestFilter(String idSelected)
    {
        String token = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, "");
        params.clear();
        params.put("Token", token);
        params.put("IDStore", idSelected);
        showLoadingDialog();
        ApiService.Factory.getInstance().reportByStore(params).enqueue(callbackReport());
    }

    public interface SendStoreList
    {
        void sendStoreList(List<Store> stores);
    }
}
