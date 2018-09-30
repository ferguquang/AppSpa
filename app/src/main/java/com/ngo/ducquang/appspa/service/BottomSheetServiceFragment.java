package com.ngo.ducquang.appspa.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseBottomSheetDialogFragment;
import com.ngo.ducquang.appspa.base.view.ConfirmDialog;
import com.ngo.ducquang.appspa.base.view.OnConfirmDialogAction;
import com.ngo.ducquang.appspa.storageList.model.Category;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
                        adapter.removeAt(posotion);
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
