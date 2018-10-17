package com.ngo.ducquang.appspa.service;

import android.content.Context;
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
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.service.model.ResponseCreateService;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/30/2018.
 */

public class DialogAddService extends BaseDialog implements View.OnClickListener
{
    @BindView(R.id.dialogTitle) TextView dialogTitle;
    @BindView(R.id.nameEdt) CustomEditText nameEdt;
    @BindView(R.id.describeEdt) CustomEditText describeEdt;
    @BindView(R.id.hourEdt) CustomEditText hourEdt;
    @BindView(R.id.minuteEdt) CustomEditText minuteEdt;

    @BindView(R.id.dialogCancel) Button dialogCancel;
    @BindView(R.id.dialogAdd) Button dialogAdd;

    private SendCategory sendCategory;

    private boolean isUpdate;
    private Category category;
    private int position;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendCategory = (SendCategory) context;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_add_service;
    }

    @Override
    protected void initView(View view) {
        dialogCancel.setOnClickListener(this);
        dialogAdd.setOnClickListener(this);

        if (isUpdate)
        {
            nameEdt.setText(category.getName());
            describeEdt.setText(category.getDescribe());
            hourEdt.setText(category.getHour() + "");
            minuteEdt.setText(category.getMinute() + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.dialogAdd:
            {
                String name = nameEdt.getText().toString();
                String describe = describeEdt.getText().toString();
                String hour = hourEdt.getText().toString();
                String minutes = minuteEdt.getText().toString();

                if (StringUtilities.isEmpty(name))
                {
                    nameEdt.requestFocus();
                    nameEdt.setError("Không được để trống!!!");
                    return;
                }

                if (StringUtilities.isEmpty(describe))
                {
                    describeEdt.requestFocus();
                    describeEdt.setError("Không được để trống!!!");
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("Token", PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, ""));
                params.put("Name", name);
                params.put("Describe", describe);
                params.put("Hour", hour);
                params.put("Minute", minutes);

                showLoadingDialog();
                if (!isUpdate) // thêm mới
                {
                    ApiService.Factory.getInstance().createService(params).enqueue(new Callback<ResponseCreateService>()
                    {
                        @Override
                        public void onResponse(Call<ResponseCreateService> call, Response<ResponseCreateService> response)
                        {
                            Message message = response.body().getMessages().get(0);
                            if (response.body().getStatus() == 1)
                            {
                                Category category = response.body().getData().getCategory();
                                sendCategory.sendCategory(category, -1, false);
                                showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
                            }
                            else
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_ERRO);
                            }

                            hideLoadingDialog();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseCreateService> call, Throwable t) {
                            LogManager.tagDefault().error(t.getMessage());
                        }
                    });
                }
                else // chỉnh sửa
                {
                    params.put("ID", category.getiD() + "");
                    ApiService.Factory.getInstance().updateService(params).enqueue(new Callback<ResponseCreateService>()
                    {
                        @Override
                        public void onResponse(Call<ResponseCreateService> call, Response<ResponseCreateService> response)
                        {
                            Message message = response.body().getMessages().get(0);
                            if (response.body().getStatus() == 1)
                            {
                                Category category = response.body().getData().getCategory();
                                sendCategory.sendCategory(category, position, true);
                                showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
                            }
                            else
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_ERRO);
                            }

                            hideLoadingDialog();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseCreateService> call, Throwable t) {
                            LogManager.tagDefault().error(t.getMessage());
                        }
                    });
                }

                break;
            }
            case R.id.dialogCancel:
            {
                dismiss();
                break;
            }
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public interface SendCategory
    {
        void sendCategory(Category category, int position, boolean isUpdate);
    }
}
