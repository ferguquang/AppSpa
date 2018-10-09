package com.ngo.ducquang.appspa.storageList;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseBottomSheetDialogFragment;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.reponseMessage.ResponseMessage;
import com.ngo.ducquang.appspa.profile.ProfileFragment;
import com.ngo.ducquang.appspa.storageList.model.UserStore;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/29/2018.
 */

public class BottomSheetStore extends BaseBottomSheetDialogFragment implements View.OnClickListener
{
    @BindView(R.id.imgNameBottomSheet) ImageView imgNameBottomSheet;
    @BindView(R.id.nameOption) TextView nameOption;
    @BindView(R.id.imgAssetEdit) ImageView imgAssetEdit;
    @BindView(R.id.editOption) LinearLayout editOption;
    @BindView(R.id.switchSetOff) Switch switchSetOff;
    @BindView(R.id.setLockOption) RelativeLayout setLockOption;

    private StorageAdapter storageAdapter;
    private UserStore userStore;
    private boolean isAdmin;
    private int position;

    private int valueStatus;

    @Override
    protected int getContentView() {
        return R.layout.bottom_sheet_dialog_store;
    }

    @Override
    protected void initView(View view) {
        editOption.setOnClickListener(this);
        nameOption.setText(userStore.getName());

        switchSetOff.setChecked(userStore.getStatus() == 1);
        valueStatus = switchSetOff.isChecked() ? 0 : 1;

        switchSetOff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("Token", PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, ""));
            params.put("ID", userStore.getiDUser() + "");
            params.put("Status", valueStatus + "");
            ApiService.Factory.getInstance().activeOrUnActiveStore(params).enqueue(new Callback<ResponseMessage>()
            {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response)
                {
                    userStore.setStatus(switchSetOff.isChecked() ? 1 : 0);
                    storageAdapter.updateItem(userStore, position);
                    showToast(response.body().getMessages().get(0).getText(), GlobalVariables.TOAST_INFO);
                    dismiss();
                }

                @Override
                public void onFailure(Call<ResponseMessage> call, Throwable t) {
                    LogManager.tagDefault().error(t.getMessage());
                }
            });
        });

        if (isAdmin)
        {
            editOption.setVisibility(View.VISIBLE);
            setLockOption.setVisibility(View.VISIBLE);
        }
        else
        {
            editOption.setVisibility(View.GONE);
            setLockOption.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.editOption:
            {
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setType(ProfileFragment.TYPE_STORE);
                profileFragment.setAdmin(isAdmin);
                profileFragment.setUserStorel(userStore);
                profileFragment.setStorageAdapter(storageAdapter);
                profileFragment.setPosition(position);
                addFragment(profileFragment, null, true);
                dismiss();
                break;
            }
        }
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setStorageAdapter(StorageAdapter storageAdapter) {
        this.storageAdapter = storageAdapter;
    }
}
