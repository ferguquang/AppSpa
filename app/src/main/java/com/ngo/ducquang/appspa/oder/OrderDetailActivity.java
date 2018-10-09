package com.ngo.ducquang.appspa.oder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.oder.model.Order;
import com.ngo.ducquang.appspa.oder.model.ResponseDetailOrder;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 10/6/2018.
 */

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener
{
    public static final String ORDER_MODEL = "ordermodel";
    public static final String ID_ORDER = "idorder";
    public static final String ID_NOTI = "idnoti";

    public static final String FROM_NOTIFICATION_ACTIVITY = "fromNotification";

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
    @BindView(R.id.reponseTime) TextView reponseTime;
    @BindView(R.id.imgOption) ImageView imgOption;

    String orderJson = "";
    int idOrder;
    int idNoti;
    private boolean fromNotificationActivity = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView()
    {
        hideMenu();
        showIconBack();
        title.setText("Đăng kí chi tiết");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            orderJson = bundle.getString(ORDER_MODEL, "");
            idOrder = bundle.getInt(ID_ORDER, 0);
            fromNotificationActivity = bundle.getBoolean(FROM_NOTIFICATION_ACTIVITY, false);
            idNoti = bundle.getInt(ID_NOTI, 0);
        }

        if (!StringUtilities.isEmpty(orderJson))
        {
            Order order = Order.initialize(orderJson);
            if (order != null)
            {
                setData(order);
            }
        }

        showLoadingDialog();
        String token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");
        HashMap<String, String> params = new HashMap<>();
        params.put("Token", token);
        params.put("ID", idOrder + "");
        if (fromNotificationActivity)
        {
            params.put("IDNoti", idNoti + "");
        }
        ApiService.Factory.getInstance().detailOrder(params).enqueue(new Callback<ResponseDetailOrder>()
        {
            @Override
            public void onResponse(Call<ResponseDetailOrder> call, Response<ResponseDetailOrder> response)
            {
                if (response.body().getStatus() == 1)
                {
                    Order order = response.body().getData().getOrder();
                    setData(order);
                }

                hideLoadingDialog();
            }

            @Override
            public void onFailure(Call<ResponseDetailOrder> call, Throwable t)
            {
                showToast(t.getMessage(), GlobalVariables.TOAST_ERRO);
                hideLoadingDialog();
            }
        });

        imgOption.setOnClickListener(this);
    }

    @Override
    protected void initMenu(Menu menu) {}

    private void setData(Order order)
    {
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
        reponseTime.setText("Ngày phản hồi: " + ManagerTime.convertToMonthDayYearHourMinuteFormatSlash(order.getResponsed()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgOption:
            {
                BottomSheetOrder bottomSheetOrder = new BottomSheetOrder();
                break;
            }
        }
    }
}
