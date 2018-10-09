package com.ngo.ducquang.appspa.storageList;

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
import com.ngo.ducquang.appspa.storageList.createStore.CreateStoreFragment;
import com.ngo.ducquang.appspa.storageList.model.DataStoreList;
import com.ngo.ducquang.appspa.storageList.model.ResponseStoreList;
import com.ngo.ducquang.appspa.storageList.model.UserStore;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    private StorageAdapter adapter;

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

        int positionID = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.POSITION_ID, -1);
        if (positionID == GlobalVariables.IS_ADMIN)
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.GONE);

        fab.setOnClickListener(this);

        showLoadingDialog();
        ApiService.Factory.getInstance().getStoreList(PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "")).enqueue(new Callback<ResponseStoreList>()
        {
            @Override
            public void onResponse(Call<ResponseStoreList> call, Response<ResponseStoreList> response)
            {
                if (response.body().getStatus() == 1)
                {
                    DataStoreList dataStoreList = response.body().getData();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    adapter = new StorageAdapter(StoreActivity.this, dataStoreList.getUserStores(), getSupportFragmentManager(), dataStoreList.getAdmin());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);

                    hideLoadingDialog();

                    Share.getInstance().categoryList = response.body().getData().getCategories();
                }
            }

            @Override
            public void onFailure(Call<ResponseStoreList> call, Throwable t) {
                LogManager.tagDefault().error(t.getMessage());
            }
        });
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
