package com.ngo.ducquang.appspa.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseBottomSheetDialogFragment;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.reponseMessage.ResponseMessage;
import com.ngo.ducquang.appspa.base.view.ConfirmDialog;
import com.ngo.ducquang.appspa.base.view.OnConfirmDialogAction;
import com.ngo.ducquang.appspa.storageList.model.Category;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 10/1/2018.
 */

public class BottomSheetServiceFragment extends BaseBottomSheetDialogFragment implements View.OnClickListener {
    @BindView(R.id.nameOption) TextView nameOption;
    @BindView(R.id.editOption) LinearLayout editOption;
    @BindView(R.id.deleteOption) LinearLayout deleteOption;

    private int posotion;
    private ServiceAdminAdapter adapter;
    private Category category;

    @Override
    protected int getContentView() {
        return R.layout.bottom_sheet_dialog_service;
    }

    @Override
    protected void initView(View view) {
        editOption.setOnClickListener(this);
        deleteOption.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.editOption:
            {
                DialogAddService dialogAddService = new DialogAddService();
                dialogAddService.setCategory(category);
                dialogAddService.setPosition(posotion);
                dialogAddService.setUpdate(true);
                dialogAddService.show(getFragmentManager(), dialogAddService.getTag());
                dismiss();
                break;
            }
            case R.id.deleteOption:
            {
                ConfirmDialog.initialize("Bạn có muốn xóa dịch vụ này không?", new OnConfirmDialogAction()
                {
                    @Override
                    public void onCancel() {}

                    @Override
                    public void onAccept()
                    {
                        String token = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, "");
                        int idService = category.getiD();
                        showLoadingDialog();
                        ApiService.Factory.getInstance().deleteService(token, idService).enqueue(new Callback<ResponseMessage>() {
                            @Override
                            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response)
                            {
                                Message message = response.body().getMessages().get(0);
                                if (response.body().getStatus() == 1)
                                {
                                    adapter.removeAt(posotion);
                                    showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
                                }
                                else
                                    showToast(message.getText(), GlobalVariables.TOAST_ERRO);

                                hideLoadingDialog();

                                dismiss();
                            }

                            @Override
                            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                LogManager.tagDefault().error(t.getMessage());
                            }
                        });
                    }
                }).show(getFragmentManager(), "");
                break;
            }
        }
    }

    public void setPosotion(int posotion) {
        this.posotion = posotion;
    }

    public void setAdapter(ServiceAdminAdapter adapter) {
        this.adapter = adapter;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
