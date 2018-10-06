package com.ngo.ducquang.appspa.oder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.oder.model.Order;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 10/6/2018.
 */

public class OrderDetailActivity extends BaseActivity {
    public static final String ORDER_MODEL = "ordermodel";

    @BindView(R.id.name) TextView name;
    @BindView(R.id.nameStore) TextView nameStore;
    @BindView(R.id.dateTime) TextView dateTime;
    @BindView(R.id.status) TextView status;
    @BindView(R.id.cardViewStatus) CardView cardViewStatus;
    @BindView(R.id.describe) TextView describe;
    @BindView(R.id.userName) TextView userName;
    @BindView(R.id.userAddress) TextView userAddress;
    @BindView(R.id.userPhone) TextView userPhone;
    @BindView(R.id.userEmail) TextView userEmail;

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        hideMenu();
        showIconBack();
        title.setText("Đăng kí chi tiết");
        String orderJson =  getIntent().getStringExtra(ORDER_MODEL);
        Order order = Order.initialize(orderJson);

        name.setText(order.getName());
        nameStore.setText(order.getStoreName());
        dateTime.setText(ManagerTime.convertToMonthDayYearHourMinuteFormatSlash(order.getOnDate()));
        status.setText(order.getStatusName());
        cardViewStatus.setCardBackgroundColor(Color.parseColor(order.getStatusColor()));
        describe.setText(order.getDescribe());
        userName.setText(order.getUser().getName());
        userAddress.setText(order.getUser().getAddress());
        userPhone.setText(order.getUser().getPhone());
        userEmail.setText(order.getUser().getEmail());
    }

    @Override
    protected void initMenu(Menu menu) {

    }
}
