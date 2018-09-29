package com.ngo.ducquang.appspa.userList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;
import com.ngo.ducquang.appspa.userList.model.ResponseUserDetail;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/29/2018.
 */

public class UserDetailFragment extends BaseFragment
{
    @BindView(R.id.layoutContentPass) LinearLayout layoutContentPass;

    @BindView(R.id.nameEdt) EditText nameEdt;
    @BindView(R.id.addressEdt) EditText addressEdt;
    @BindView(R.id.emailEdt) EditText emailEdt;
    @BindView(R.id.phoneEdt) EditText phoneEdt;
    @BindView(R.id.genderEdt) TextView genderEdt;

    @BindView(R.id.birthdayEdt) TextView birthdayEdt;

    @BindView(R.id.updateProfile) Button updateProfile;

    @BindView(R.id.valueProvince) TextView valueProvince;
    @BindView(R.id.valueDistrict) TextView valueDistrict;

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView(View view)
    {
        showBackPressToolBar();
        title.setText("Thông tin khách hàng");
        layoutContentPass.setVisibility(View.GONE);
        disableUpdate();

        Bundle bundle = getArguments();
        int idUser = bundle.getInt(UserListAdapter.ID_USER);
        HashMap<String, String> params = new HashMap<>();
        params.put("Token", PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, ""));
        params.put("ID", idUser + "");
        ApiService.Factory.getInstance().userDetail(params).enqueue(new Callback<ResponseUserDetail>()
        {
            @Override
            public void onResponse(Call<ResponseUserDetail> call, Response<ResponseUserDetail> response)
            {
                if (response.body().getStatus() == 1)
                {
                    UserApp userApp = response.body().getData().getUserApp();
                    nameEdt.setText(userApp.getName());
                    phoneEdt.setText(userApp.getPhone());
                    addressEdt.setText(userApp.getAddress());
                    setGender(userApp.getGender());
                    emailEdt.setText(userApp.getEmail());
                    birthdayEdt.setText(ManagerTime.convertToMonthDayYear(userApp.getBirthday()));
                    valueProvince.setText(userApp.getProvinceName());
                    valueDistrict.setText(userApp.getDistrictName());
                }
            }

            @Override
            public void onFailure(Call<ResponseUserDetail> call, Throwable t) {

            }
        });
    }

    private void disableUpdate()
    {
        nameEdt.setEnabled(false);
        phoneEdt.setEnabled(false);
//        spinnerCity.setClickable(false);
//        spinnerDistrict.setClickable(false);
        addressEdt.setEnabled(false);
        emailEdt.setEnabled(false);
        genderEdt.setClickable(false);
        nameEdt.setEnabled(false);
        nameEdt.setEnabled(false);

        layoutContentPass.setVisibility(View.GONE);
    }

    private void setGender(int gender) {
        if(gender == 0)
        {
            genderEdt.setText("Nam");
        }
        else if (gender == 1)
        {
            genderEdt.setText("Nữ");
        }
    }
}
