package com.ngo.ducquang.appspa;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Manager;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.Share;
import com.ngo.ducquang.appspa.base.font.TypefaceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.getAddress.ResponseGetAddress;
import com.ngo.ducquang.appspa.login.LoginActivity;
import com.ngo.ducquang.appspa.modelImageSlide.File;
import com.ngo.ducquang.appspa.modelImageSlide.ResponseGetImage;
import com.ngo.ducquang.appspa.modelStore.DataGetStore;
import com.ngo.ducquang.appspa.modelStore.ResponseGetStore;
import com.ngo.ducquang.appspa.notification.NotificationActivity;
import com.ngo.ducquang.appspa.slideMenu.SlideMenuFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements SlideMenuFragment.EventCloseNavigationInActivity
{
    public static final int BOOK = 1;
    public static final int STORE_LIST = 2;
    public static final int NOTIFICATION = 3;
    public static final int BOOK_AT_HOME = 4;
    public static final int LIST_USER = 5;
    public static final int LIST_SERVICE = 6;
    public static final int LIST_ORDER = 7;
    public static final int REPORT = 8;

    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.leftDrawer) FrameLayout leftDrawer;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private ActionBarDrawerToggle drawerToggle;

    private MainAdapter mainAdapter;
    private List<File> bannerModels = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView()
    {
        TypefaceUtil.overrideFont(getApplicationContext(), "DEFAULT", GlobalVariables.FONT_BASE);

        if (!PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.LOGIN_SUCCESS, false))
        {
            startActivity(LoginActivity.class, null, true);
            finish();
            return;
        }

        enableServiceNotification();

        ApiService.Factory.getInstance().getAddress().enqueue(new Callback<ResponseGetAddress>() {
            @Override
            public void onResponse(Call<ResponseGetAddress> call, Response<ResponseGetAddress> response)
            {
                try
                {
                    Share.getInstance().provinces.clear();
                    Share.getInstance().districts.clear();

                    Share.getInstance().provinces = response.body().getData().getProvinces();
                    Share.getInstance().districts = response.body().getData().getDistricts();
                }
                catch (Exception e)
                {
                    LogManager.tagDefault().error(e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseGetAddress> call, Throwable t) {
                LogManager.tagDefault().debug();
            }
        });

        String token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");
        ApiService.Factory.getInstance().getStore(token, "").enqueue(new Callback<ResponseGetStore>() {
            @Override
            public void onResponse(Call<ResponseGetStore> call, Response<ResponseGetStore> response) {
                if (response.body().getStatus() == 1)
                {
                    DataGetStore dataGetStore = response.body().getData();
                    PreferenceUtil.savePreferences(getApplicationContext(), PreferenceUtil.DATA_GET_STORE, dataGetStore.toJson());
                }

                Manager.checkAuthen(getApplicationContext(), response.body().getStatus(), response.body().getMessages());
            }

            @Override
            public void onFailure(Call<ResponseGetStore> call, Throwable t) {
                LogManager.tagDefault().error(t.getMessage());
            }
        });

        Toasty.Config.getInstance()
                .setInfoColor(getResources().getColor(R.color.colorAccent))
                .setSuccessColor(getResources().getColor(R.color.colorAccent))
                .setErrorColor(getResources().getColor(R.color.main5))
                .tintIcon(true)
                .apply();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.leftDrawer, new SlideMenuFragment())
                .commit();

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // phân quyền:
        int positionID = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.POSITION_ID, -1);

        List<ModelItemMain> dataList = new ArrayList<>();

        switch (positionID)
        {
            case GlobalVariables.IS_ADMIN:
            {
                dataList.add(new ModelItemMain(STORE_LIST, "danh sách cửa hàng", "Thông tin cửa hàng", R.drawable.sale, getResources().getColor(R.color.main1)));
                dataList.add(new ModelItemMain(LIST_ORDER, "danh sách lịch đặt", "Thông tin lịch đặt", R.drawable.option, getResources().getColor(R.color.main2)));
                dataList.add(new ModelItemMain(LIST_SERVICE, "danh sách dịch vụ", "Dịch vụ của cửa hàng", R.drawable.qttt, getResources().getColor(R.color.main3)));
                dataList.add(new ModelItemMain(LIST_USER, "danh sách khách hàng", "Thông tin chi tiết khách hàng", R.drawable.cust, getResources().getColor(R.color.main4)));
                dataList.add(new ModelItemMain(NOTIFICATION, "thông báo", "Thông báo", R.drawable.icon_notification_main, getResources().getColor(R.color.main5)));
                dataList.add(new ModelItemMain(REPORT, "thống kê", "Báo cáo, thống kê", R.drawable.mtask, getResources().getColor(R.color.main6)));
                break;
            }
            case GlobalVariables.IS_STORE:
            {
                dataList.add(new ModelItemMain(STORE_LIST, "cửa hàng của bạn", "Thông tin cửa hàng", R.drawable.sale, getResources().getColor(R.color.main1)));
                dataList.add(new ModelItemMain(LIST_ORDER, "danh sách lịch đặt", "Thông tin lịch đặt", R.drawable.option, getResources().getColor(R.color.main2)));
                dataList.add(new ModelItemMain(LIST_USER, "danh sách khách hàng", "", R.drawable.cust, getResources().getColor(R.color.main3)));
                dataList.add(new ModelItemMain(NOTIFICATION, "thông báo", "Thông báo", R.drawable.icon_notification_main, getResources().getColor(R.color.main4)));
                break;
            }
            case GlobalVariables.IS_USER:
            {
                dataList.add(new ModelItemMain(BOOK, "đặt lịch", "Thông tin đặt lịch", R.drawable.icon_map, getResources().getColor(R.color.main1)));
                dataList.add(new ModelItemMain(BOOK_AT_HOME, "ĐẶT TẠI NHÀ", "Tại nhà", R.drawable.icon_home, getResources().getColor(R.color.main2)));
                dataList.add(new ModelItemMain(STORE_LIST, "danh sách cửa hàng", "Thông tin cửa hàng", R.drawable.sale, getResources().getColor(R.color.main3)));
                dataList.add(new ModelItemMain(LIST_ORDER, "danh sách lịch đặt", "Thông tin lịch đặt", R.drawable.mtask, getResources().getColor(R.color.main4)));
                dataList.add(new ModelItemMain(NOTIFICATION, "thông báo", "Thông báo", R.drawable.icon_notification_main, getResources().getColor(R.color.main5)));
                break;
            }
        }

        title.setText(R.string.app_name);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mainAdapter = new MainAdapter(dataList, getSupportFragmentManager(), this, bannerModels);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mainAdapter);

        ApiService.Factory.getInstance().getImage(token).enqueue(new Callback<ResponseGetImage>()
        {
            @Override
            public void onResponse(Call<ResponseGetImage> call, Response<ResponseGetImage> response)
            {
                if (response.body().getStatus() == 1)
                {
                    bannerModels = response.body().getData().getFiles();
                    mainAdapter.updateImage(bannerModels);
                }
            }

            @Override
            public void onFailure(Call<ResponseGetImage> call, Throwable t) {

            }
        });
    }

    @Override
    protected void initMenu(Menu menu) {}

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        try
        {
            drawerToggle.syncState();
        }
        catch (Exception e)
        {
            LogManager.tagDefault().error(e.toString());
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Thông báo")
                .setIcon(R.drawable.icon_asset_notification)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 1:
            {
                startActivity(NotificationActivity.class, null, false);
                break;
            }
        }

        return true;
    }

    @Override
    public void closeNavigationInActivity() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }
}
