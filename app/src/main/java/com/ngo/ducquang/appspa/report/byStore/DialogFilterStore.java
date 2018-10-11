package com.ngo.ducquang.appspa.report.byStore;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseDialog;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.modelStore.DataGetStore;
import com.ngo.ducquang.appspa.modelStore.Store;
import com.ngo.ducquang.appspa.report.byAddress.ByAddressReportAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ducqu on 10/9/2018.
 */

public class DialogFilterStore extends BaseDialog implements DialogFilterStoreAdapter.SendDataCurrent
{
    @BindView(R.id.btnOK) Button btnOK;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private SendIDStoreSelected sendIDStoreSelected;
    private List<Store> stores;
    private DialogFilterStoreAdapter adapter;
    private String idSelected;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendIDStoreSelected = (SendIDStoreSelected) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sendIDStoreSelected = null;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_filter_store;
    }

    @Override
    protected void initView(View view)
    {
        String storeString = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.DATA_GET_STORE, "");
        DataGetStore dataGetStore = DataGetStore.initialize(storeString);
        stores = dataGetStore.getStores();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new DialogFilterStoreAdapter(getContext(), stores, this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIDStoreSelected.send(idSelected);
                dismiss();
            }
        });
    }

    @Override
    public void sendList(List<Store> dataCurrent)
    {
        idSelected = "";
        List<String> listID = new ArrayList<>();
        for (int i = 0; i < dataCurrent.size(); i++)
        {
            Store store = dataCurrent.get(i);
            listID.add(store.getiD() + "");
        }

        idSelected = TextUtils.join(",", listID);
    }

    public interface SendIDStoreSelected
    {
        void send(String idSelected);
    }
}
