package com.ngo.ducquang.appspa.report;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.getAddress.District;
import com.ngo.ducquang.appspa.base.getAddress.Province;
import com.ngo.ducquang.appspa.base.view.TabPagerAdapter;
import com.ngo.ducquang.appspa.base.view.TransformerFadeViewPager;
import com.ngo.ducquang.appspa.base.view.spinner.AdapterSpinner;
import com.ngo.ducquang.appspa.base.view.spinner.SpinnerModel;
import com.ngo.ducquang.appspa.modelStore.Store;
import com.ngo.ducquang.appspa.report.byAddress.ByAddressReportFragment;
import com.ngo.ducquang.appspa.report.byAddress.DialogFilterAddress;
import com.ngo.ducquang.appspa.report.byStore.ByStoreReportFragment;
import com.ngo.ducquang.appspa.report.byStore.DialogFilterStore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 10/8/2018.
 */

public class ReportActivity extends BaseActivity implements ByStoreReportFragment.SendStoreList, ByAddressReportFragment.SendListAddress, DialogFilterStore.SendIDStoreSelected, DialogFilterAddress.SendIDAddress
{
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private TabPagerAdapter adapter;

    private ByStoreReportFragment byStoreReportFragment = new ByStoreReportFragment();
    private ByAddressReportFragment byAddressReportFragment = new ByAddressReportFragment();
    private int selectionTab = 0;

    private List<Store> stores;
    private List<District> districts = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_report;
    }

    @Override
    protected void initView()
    {
        hideMenu();
        showIconBack();

        title.setText("Thống kê");

        adapter = new TabPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(byStoreReportFragment, "Theo cửa hàng");
        adapter.addFragment(byAddressReportFragment, "Theo địa chỉ");

        viewPager.setPageTransformer(false, new TransformerFadeViewPager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position)
            {
                selectionTab = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void initMenu(Menu menu) {}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "Lọc")
                .setIcon(R.drawable.icon_asset_filter)
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
                switch (selectionTab)
                {
                    case 0:
                    {
                        DialogFilterStore dialogFilterStore = new DialogFilterStore();
                        dialogFilterStore.setStores(stores);
                        dialogFilterStore.show(getSupportFragmentManager(), dialogFilterStore.getTag());
                        break;
                    }
                    case 1:
                    {
                        DialogFilterAddress dialogFilterAddress = new DialogFilterAddress();
                        dialogFilterAddress.setProvinces(provinces);
                        dialogFilterAddress.setDistricts(districts);
                        dialogFilterAddress.show(getSupportFragmentManager(), dialogFilterAddress.getTag());
                        break;
                    }
                }

                break;
            }
        }
        return true;
    }

    @Override
    public void sendStoreList(List<Store> stores) {
        this.stores = stores;
    }

    @Override
    public void sendLisAddress(List<Province> provinces, List<District> districts) {
        this.provinces = provinces;
        this.districts = districts;
    }

    @Override
    public void send(String idSelected) {
        byStoreReportFragment.sendRequestFilter(idSelected);
    }

    @Override
    public void sendAddress(int idProvince, int idDistrict) {
        byAddressReportFragment.requestFilter(idProvince, idDistrict);
    }
}
