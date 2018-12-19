package com.ngo.ducquang.appspa.listDauTu;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseBottomSheetDialogFragment;
import com.ngo.ducquang.appspa.base.view.ConfirmDialog;
import com.ngo.ducquang.appspa.base.view.OnConfirmDialogAction;
import com.ngo.ducquang.appspa.listVanHanh.AddVanHanhDialog;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;
import com.ngo.ducquang.appspa.userList.UserListAdapter;

import butterknife.BindView;

public class BottomSheetNewUserFragment extends BaseBottomSheetDialogFragment implements View.OnClickListener
{
    @BindView(R.id.nameOption) TextView nameOption;
    @BindView(R.id.editOption) LinearLayout editOption;
    @BindView(R.id.deleteOption) LinearLayout deleteOption;

    private int posotion;
    private UserApp userApp;
    private UserListAdapter adapter;
    private int type;

    @Override
    protected int getContentView() {
        return R.layout.bottom_sheet_dialog_service;
    }

    @Override
    protected void initView(View view) {
        editOption.setOnClickListener(this);
        deleteOption.setOnClickListener(this);
        nameOption.setText(userApp.getName());

        editOption.setVisibility(View.GONE);
        deleteOption.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.editOption:
            {
                switch (type)
                {
                    case UserListAdapter.TYPE_DAU_TU:
                    {
                        AddDauTuDialog addDauTuDialog = new AddDauTuDialog();
                        addDauTuDialog.setPosition(posotion);
                        addDauTuDialog.setUpdate(true);
                        addDauTuDialog.setUserApp(userApp);
                        addDauTuDialog.show(getFragmentManager(), addDauTuDialog.getTag());
                        break;
                    }
                    case UserListAdapter.TYPE_VAN_HANH:
                    {
                        AddVanHanhDialog addDauTuDialog = new AddVanHanhDialog();
                        addDauTuDialog.setPosition(posotion);
                        addDauTuDialog.setUpdate(true);
                        addDauTuDialog.setUserApp(userApp);
                        addDauTuDialog.show(getFragmentManager(), addDauTuDialog.getTag());
                        break;
                    }
                }

                dismiss();
                break;
            }
            case R.id.deleteOption:
            {
                ConfirmDialog.initialize("Bạn có muốn xóa người này không?", new OnConfirmDialogAction()
                {
                    @Override
                    public void onCancel() {}

                    @Override
                    public void onAccept()
                    {
//                        String token = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, "");
//                        int idService = category.getiD();
//                        showLoadingDialog();
//                        ApiService.Factory.getInstance().deleteService(token, idService).enqueue(new Callback<ResponseMessage>() {
//                            @Override
//                            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response)
//                            {
//                                Message message = response.body().getMessages().get(0);
//                                if (response.body().getStatus() == 1)
//                                {
//                                    adapter.removeAt(posotion);
//                                    showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
//                                }
//                                else
//                                    showToast(message.getText(), GlobalVariables.TOAST_ERRO);
//
//                                hideLoadingDialog();
//
//                                dismiss();
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResponseMessage> call, Throwable t) {
//                                LogManager.tagDefault().error(t.getMessage());
//                            }
//                        });
                    }
                }).show(getFragmentManager(), "");
                break;
            }
        }
    }

    public void setPosotion(int posotion) {
        this.posotion = posotion;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public void setAdapter(UserListAdapter adapter) {
        this.adapter = adapter;
    }

    public void setType(int type) {
        this.type = type;
    }
}
