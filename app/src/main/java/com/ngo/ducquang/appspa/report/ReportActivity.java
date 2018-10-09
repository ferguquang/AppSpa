package com.ngo.ducquang.appspa.report;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.view.spinner.AdapterSpinner;
import com.ngo.ducquang.appspa.base.view.spinner.SpinnerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 10/8/2018.
 */

public class ReportActivity extends BaseActivity
{
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<SpinnerModel> dataSpinner;

    @Override
    protected int getContentView() {
        return R.layout.activity_report;
    }

    @Override
    protected void initView() {
        title.setText("Thống kê");

        dataSpinner = new ArrayList<>();
        dataSpinner.add(new SpinnerModel(1, "Thống kê theo cửa hàng"));
        dataSpinner.add(new SpinnerModel(2, "Thống kê theo địa chỉ khách hàng"));

        AdapterSpinner adapterSpinner = new AdapterSpinner(this, R.layout.item_dialog_spinner, dataSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                int idSelected = dataSpinner.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    @Override
    protected void initMenu(Menu menu) {}
}
