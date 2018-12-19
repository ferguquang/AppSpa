package com.ngo.ducquang.appspa.storageList;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.EventBusManager;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.Share;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.login.RegisterFragment;
import com.ngo.ducquang.appspa.storageList.model.DataStoreList;
import com.ngo.ducquang.appspa.storageList.model.ResponseStoreList;
import com.ngo.ducquang.appspa.storageList.model.UserStore;
import com.ngo.ducquang.appspa.storageList.storeDetail.StoreDetailActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/22/2018.
 */

public class StoreActivity extends BaseActivity implements View.OnClickListener, RegisterFragment.SendUserStore
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;

    private String token = "";
    private LinearLayoutManager layoutManager;

    private HashMap<String, String> params = new HashMap<>();
    private int take = 10, skip = 1;

    private StorageAdapter adapter;
    private boolean loadMore = true;

    private SubcriberStoreActivity subcriberStoreActivity = new SubcriberStoreActivity()
    {
        @Override
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onEventSendUserStore(EventSendUserStore event) {
            adapter.addStore(event.getUserStore());
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initMenu(Menu menu) {}

    @Override
    protected void onStart() {
        super.onStart();
        EventBusManager.instance().register(subcriberStoreActivity);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBusManager.instance().unregister(subcriberStoreActivity);
    }

    @Override
    protected void initView()
    {
        hideMenu();
        showIconBack();

        title.setText("Danh sách cửa hàng");

        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");
        int positionID = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.POSITION_ID, -1);
        if (positionID == GlobalVariables.IS_ADMIN || positionID == GlobalVariables.IS_VAN_HANH)
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.GONE);

        fab.setOnClickListener(this);

        params.put("Token", token);
        params.put("Skip", skip + "");
        params.put("Take", take + "");
        showLoadingDialog();
        ApiService.Factory.getInstance().getStoreList(params).enqueue(callbackStoreList());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (loadMore)
                {
                    if (!recyclerView.canScrollVertically(1))
                    {
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        skip = skip + 1;
        params.clear();
        params.put("Token", token);
        params.put("Skip", skip + "");
        params.put("Take", take + "");
        showLoadingDialog();
        ApiService.Factory.getInstance().getStoreList(params).enqueue(callbackStoreList());
    }

    private Callback<ResponseStoreList> callbackStoreList()
    {
        return new Callback<ResponseStoreList>()
        {
            @Override
            public void onResponse(Call<ResponseStoreList> call, Response<ResponseStoreList> response)
            {
                if (response.body().getStatus() == 1)
                {
                    DataStoreList dataStoreList = response.body().getData();
                    if (adapter == null)
                    {
                        adapter = new StorageAdapter(StoreActivity.this, dataStoreList.getUserStores(), getSupportFragmentManager(), dataStoreList.getAdmin(), dataStoreList.getCategories());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                    else
                    {
                        if (dataStoreList.getUserStores().size() > 0)
                        {
                            adapter.addDataList(dataStoreList.getUserStores());
                        }
                        else
                        {
                            loadMore = false;
                        }
                    }

                    Share.getInstance().categoryList = response.body().getData().getCategories();

                    int positionID = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.POSITION_ID, -1);
                    if (positionID == GlobalVariables.IS_STORE)
                    {
                        if (dataStoreList.getUserStores().size() == 1)
                        {
                            finish();
                            UserStore userStore = dataStoreList.getUserStores().get(0);
                            Bundle bundle = new Bundle();
                            bundle.putInt(StorageAdapter.ID_STORE, userStore.getiDUser());
                            startActivity(StoreDetailActivity.class, bundle, false);
                        }
                    }
                }

                hideLoadingDialog();
            }

            @Override
            public void onFailure(Call<ResponseStoreList> call, Throwable t) {
                LogManager.tagDefault().error(t.getMessage());
                hideLoadingDialog();
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab:
            {
                RegisterFragment registerFragment = new RegisterFragment();
                registerFragment.setType(RegisterFragment.TYPE_STORE);
                addFragment(registerFragment, null, true);
                break;
            }
        }
    }

    @Override
    public void sendUserStore(UserStore userStore) {
        adapter.addStore(userStore);
    }
}
