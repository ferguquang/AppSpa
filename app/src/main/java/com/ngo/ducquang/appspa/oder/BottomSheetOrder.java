package com.ngo.ducquang.appspa.oder;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseBottomSheetDialogFragment;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.reponseMessage.ResponseMessage;
import com.ngo.ducquang.appspa.oder.model.Order;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 10/7/2018.
 */

public class BottomSheetOrder extends BaseBottomSheetDialogFragment implements View.OnClickListener
{
    @BindView(R.id.nameOption) TextView nameOption;
    @BindView(R.id.editOption) LinearLayout editOption;
    @BindView(R.id.approvedOption) LinearLayout approvedOption;
    @BindView(R.id.rejectOption) LinearLayout rejectOption;
    @BindView(R.id.cancelOption) LinearLayout cancelOption;
    @BindView(R.id.doneOption) LinearLayout doneOption;
    @BindView(R.id.deleteOption) LinearLayout deleteOption;

    private Order order;
    private int idOrder;
    private String token = "";

    private CallBackActionOrder callBackActionOrder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackActionOrder = (CallBackActionOrder) context;
    }

    @Override
    protected int getContentView() {
        return R.layout.bottom_sheet_dialog_order;
    }

    @Override
    protected void initView(View view)
    {
        nameOption.setText(order.getName());
        idOrder = order.getiD();
        token = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, "");

        editOption.setOnClickListener(this);
        approvedOption.setOnClickListener(this);
        rejectOption.setOnClickListener(this);
        cancelOption.setOnClickListener(this);
        doneOption.setOnClickListener(this);
        deleteOption.setOnClickListener(this);

        setPermission();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.editOption:
            {
                Bundle bundle = new Bundle();
                bundle.putString(OrderFragment.ORDER_MODEL_STRING, order.toJson());
                bundle.putBoolean(OrderFragment.UPDATE, true);
                addFragment(new OrderFragment(), bundle, true);
                dismiss();
                break;
            }
            case R.id.approvedOption:
            {
                showLoadingDialog();
                ApiService.Factory.getInstance().approvedOrder(token, idOrder).enqueue(responseMessage());
                break;
            }
            case R.id.rejectOption:
            {
                showLoadingDialog();
                ApiService.Factory.getInstance().rejectOrder(token, idOrder).enqueue(responseMessage());
                break;
            }
            case R.id.cancelOption:
            {
                showLoadingDialog();
                ApiService.Factory.getInstance().cancelOrder(token, idOrder).enqueue(responseMessage());
                break;
            }
            case R.id.doneOption:
            {
                showLoadingDialog();
                ApiService.Factory.getInstance().doneOrder(token, idOrder).enqueue(responseMessage());
                break;
            }
            case R.id.deleteOption:
            {

                break;
            }
        }
    }

    private Callback<ResponseMessage> responseMessage()
    {
        return new Callback<ResponseMessage>()
        {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response)
            {
                Message message = response.body().getMessages().get(0);
                if (response.body().getStatus() == 1)
                {
                    showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
                    callBackActionOrder.refreshOrder();
                }

                hideLoadingDialog();
                dismiss();
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t)
            {
                hideLoadingDialog();
                showToast(t.getMessage(), GlobalVariables.TOAST_ERRO);
                dismiss();
            }
        };
    }

    private void setPermission()
    {
        if (order.getApproved())
            approvedOption.setVisibility(View.VISIBLE);
        else
            approvedOption.setVisibility(View.GONE);

        if (order.getCancel())
            cancelOption.setVisibility(View.VISIBLE);
        else
            cancelOption.setVisibility(View.GONE);

        if (order.getDelete())
            deleteOption.setVisibility(View.VISIBLE);
        else
            deleteOption.setVisibility(View.GONE);

        if (order.getDone())
            doneOption.setVisibility(View.VISIBLE);
        else
            doneOption.setVisibility(View.GONE);

        if (order.getUpdate())
            editOption.setVisibility(View.VISIBLE);
        else
            editOption.setVisibility(View.GONE);

        if (order.getReject())
            rejectOption.setVisibility(View.VISIBLE);
        else
            rejectOption.setVisibility(View.GONE);
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public interface CallBackActionOrder
    {
        void refreshOrder();
    }
}
