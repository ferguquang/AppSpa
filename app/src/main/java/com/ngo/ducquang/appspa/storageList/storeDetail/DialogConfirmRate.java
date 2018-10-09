package com.ngo.ducquang.appspa.storageList.storeDetail;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseDialog;
import com.ngo.ducquang.appspa.base.CustomEditText;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.service.model.ResponseCreateService;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.DataRate;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.ResponseRate;

import java.util.HashMap;

import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 10/1/2018.
 */

public class DialogConfirmRate extends BaseDialog implements View.OnClickListener
{
    @BindView(R.id.askText) TextView askText;
    @BindView(R.id.noteEdt) CustomEditText noteEdt;
    @BindView(R.id.dialogAdd) Button dialogAdd;
    @BindView(R.id.dialogCancel) Button dialogCancel;

    private float ratingSelect;
    private float ratingOld;
    private int idStore;

    private SendListRating sendListRating;
    private SendOldRate sendOldRate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendListRating = (SendListRating) context;
        sendOldRate = (SendOldRate) context;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_confirm_rate;
    }

    @Override
    protected void initView(View view) {
        dialogAdd.setOnClickListener(this);
        dialogCancel.setOnClickListener(this);

        askText.setText("Bạn đánh giá cửa hàng này " + ratingSelect + " sao với lý do:");
    }

    public void setRatingSelect(float ratingSelect) {
        this.ratingSelect = ratingSelect;
    }

    public void setRatingOld(float ratingOld) {
        this.ratingOld = ratingOld;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.dialogAdd:
            {
                String note = noteEdt.getText().toString();

                HashMap<String, String> params = new HashMap<>();
                params.put("Token", PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, ""));
                params.put("ID", idStore + "");
                params.put("Rate", ratingSelect + "");
                params.put("Note", note);

                showLoadingDialog();
                ApiService.Factory.getInstance().rateStore(params).enqueue(new Callback<ResponseRate>() {
                    @Override
                    public void onResponse(Call<ResponseRate> call, Response<ResponseRate> response)
                    {
                        Message message = response.body().getMessages().get(0);
                        if (response.body().getStatus() == 1)
                        {
                            sendListRating.sendList(response.body().getData());
                            showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
                        }
                        else
                            showToast(message.getText(), GlobalVariables.TOAST_ERRO);

                        hideLoadingDialog();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseRate> call, Throwable t) {
                        hideLoadingDialog();
                    }
                });
                break;
            }
            case R.id.dialogCancel:
            {
                hideLoadingDialog();
                sendOldRate.sendOldRate(ratingOld);
                dismiss();
                break;
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface SendListRating
    {
        void sendList(DataRate dataRate);
    }

    public interface SendOldRate
    {
        void sendOldRate(float ratingOld);
    }
}
