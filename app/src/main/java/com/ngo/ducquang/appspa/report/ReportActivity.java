package com.ngo.ducquang.appspa.report;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.view.TabPagerAdapter;
import com.ngo.ducquang.appspa.base.view.TransformerFadeViewPager;
import com.ngo.ducquang.appspa.base.view.spinner.AdapterSpinner;
import com.ngo.ducquang.appspa.base.view.spinner.SpinnerModel;
import com.ngo.ducquang.appspa.report.byAddress.ByAddressReportFragment;
import com.ngo.ducquang.appspa.report.byStore.ByStoreReportFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 10/8/2018.
 */

public class ReportActivity extends BaseActivity
{
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private TabPagerAdapter adapter;

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
        adapter.addFragment(new ByStoreReportFragment(), "Theo cửa hàng");
        adapter.addFragment(new ByAddressReportFragment(), "Theo địa chỉ");

        viewPager.setPageTransformer(false, new TransformerFadeViewPager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(adapter.getCount());
    }

    @Override
    protected void initMenu(Menu menu) {}
}
