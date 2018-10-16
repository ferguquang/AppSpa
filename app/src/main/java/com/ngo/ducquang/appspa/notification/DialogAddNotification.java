package com.ngo.ducquang.appspa.notification;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseDialog;
import com.ngo.ducquang.appspa.base.CustomEditText;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.reponseMessage.ResponseMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 10/16/2018.
 */

public class DialogAddNotification extends BaseDialog implements View.OnClickListener
{
    @BindView(R.id.dialogTitle) TextView dialogTitle;
    @BindView(R.id.describeEdt) CustomEditText describeEdt;

    @BindView(R.id.llStartDate) LinearLayout llStartDate;
    @BindView(R.id.llEndDate) LinearLayout llEndDate;

    @BindView(R.id.startDate) TextView startDate;
    @BindView(R.id.endDate) TextView endDate;

    @BindView(R.id.dialogCancel) Button dialogCancel;
    @BindView(R.id.dialogAdd) Button dialogAdd;

    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");


    @Override
    protected int getContentView() {
        return R.layout.dialog_add_notification;
    }

    @Override
    protected void initView(View view) {
        dialogCancel.setOnClickListener(this);
        dialogAdd.setOnClickListener(this);
        llStartDate.setOnClickListener(this);
        llEndDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.dialogCancel:
            {
                dismiss();
                break;
            }
            case R.id.dialogAdd:
            {
                String describe = describeEdt.getText().toString();
                String endDateString = endDate.getText().toString();
                String startDateString = startDate.getText().toString();

                if (StringUtilities.isEmpty(describe))
                {
                    describeEdt.requestFocus();
                    describeEdt.setError("Không được để trống!!!");
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("Token", PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, ""));
                params.put("Describe", describe);
                params.put("StartDate", startDateString);
                params.put("EndDate", endDateString);

                ApiService.Factory.getInstance().createNotification(params).enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if (response.body().getStatus() == 1)
                        {
                            Message message = response.body().getMessages().get(0);
                            showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
                            dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        dismiss();
                    }
                });

                break;
            }
            case R.id.llStartDate:
            {
                new SlideDateTimePicker.Builder(getFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {
                                startDate.setText(format.format(date));
                            }
                        })
                        .setInitialDate(new Date())
                        .setIs24HourTime(true)
                        .setIndicatorColor(Color.parseColor(GlobalVariables.COLOR_PRIMARY))
                        .build()
                        .show();
                break;
            }
            case R.id.llEndDate:
            {
                new SlideDateTimePicker.Builder(getFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {
                                endDate.setText(format.format(date));
                            }
                        })
                        .setInitialDate(new Date())
                        .setIs24HourTime(true)
                        .setIndicatorColor(Color.parseColor(GlobalVariables.COLOR_PRIMARY))
                        .build()
                        .show();
                break;
            }
        }
    }
}
