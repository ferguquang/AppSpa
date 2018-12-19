package com.ngo.ducquang.appspa.listVanHanh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
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
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.view.AddingArrayDialog;
import com.ngo.ducquang.appspa.base.view.TextViewFont;
import com.ngo.ducquang.appspa.listDauTu.DauTuListener;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;
import com.ngo.ducquang.appspa.login.modelRegister.ResponseRegister;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVanHanhDialog extends BaseDialog implements View.OnClickListener
{
    @BindView(R.id.dialogAdd) Button dialogAdd;
    @BindView(R.id.dialogCancel) Button dialogCancel;
    @BindView(R.id.dialogTitle) TextView dialogTitle;
    @BindView(R.id.userNameEdt) CustomEditText userNameEdt;
    @BindView(R.id.titleFullName) TextView titleFullName;
    @BindView(R.id.fullNameEdt) CustomEditText fullNameEdt;
    @BindView(R.id.emailEdt) CustomEditText emailEdt;
    @BindView(R.id.addressEdt) CustomEditText addressEdt;
    @BindView(R.id.txtBirthday) TextView txtBirthday;
    @BindView(R.id.llBirthday) LinearLayout llBirthday;
    @BindView(R.id.phoneEdt) CustomEditText phoneEdt;
    @BindView(R.id.genderEdt) TextViewFont genderEdt;
    @BindView(R.id.passwordEdt) CustomEditText passwordEdt;
    @BindView(R.id.repeatPasswordEdt) CustomEditText repeatPasswordEdt;

    private int valueGender = 2;

    private DauTuListener dauTuListener;
    private UserApp userApp;
    private int position;
    private boolean isUpdate = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dauTuListener = (DauTuListener) context;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_add_dau_tu;
    }

    @Override
    protected void initView(View view) {
        dialogAdd.setOnClickListener(this);
        dialogCancel.setOnClickListener(this);
        llBirthday.setOnClickListener(this);
        genderEdt.setOnClickListener(this);

        if (isUpdate)
        {
            dialogTitle.setText("Sửa nhà vận hành");
            fullNameEdt.setText(userApp.getName());
            userNameEdt.setText(userApp.getUsername());
            emailEdt.setText(userApp.getEmail());
            txtBirthday.setText(ManagerTime.convertToMonthDayYear(userApp.getBirthday()));
            phoneEdt.setText(userApp.getPhone());
            valueGender = userApp.getGender();
            setGender(valueGender);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setGender(int gender) {
        if(gender == 1)
        {
            genderEdt.setText("Nam");
        }
        else if (gender == 2)
        {
            genderEdt.setText("Nữ");
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.dialogCancel:
            {
                dismiss();
                break;
            }
            case R.id.dialogAdd:
            {
                String fullName = fullNameEdt.getText().toString();
                String userName = userNameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String birthday = txtBirthday.getText().toString();
                String phone = phoneEdt.getText().toString();
                String address = addressEdt.getText().toString();
                String pass = passwordEdt.getText().toString();
                String repeatPass = repeatPasswordEdt.getText().toString();
                if (StringUtilities.isEmpty(fullName))
                {
                    fullNameEdt.requestFocus();
                    fullNameEdt.setError("Không được để trống");
                    return;
                }
                if (StringUtilities.isEmpty(userName))
                {
                    userNameEdt.requestFocus();
                    userNameEdt.setError("Không được để trống");
                    return;
                }
                if (!pass.equals(repeatPass))
                {
                    passwordEdt.requestFocus();
                    passwordEdt.setError("2 mật khẩu phải khớp nhau");
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("Token", PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, ""));
                params.put("Username", userName);
                params.put("Name", fullName);
                params.put("Password", pass);
                params.put("Phone", phone);
                params.put("Birthday", birthday);
                params.put("Address", address);
                params.put("Email", email);
                params.put("Gender", valueGender + "");
                params.put("IDPosition", 5 + "");

                if (isUpdate)
                {
                    params.put("ID", userApp.getiDUser() + "");
                    ApiService.Factory.getInstance().updateProfile(params).enqueue(new Callback<ResponseRegister>()
                    {
                        @Override
                        public void onResponse(@NonNull Call<ResponseRegister> call, @NonNull Response<ResponseRegister> response)
                        {
                            if (response.body().getStatus() == 1)
                            {
                                showToast(response.body().getMessages().get(0).getText(), GlobalVariables.TOAST_SUCCESS);
                                UserApp userApp = response.body().getData().getUserApp();
                                dauTuListener.updateItem(userApp, position);
                                dismiss();
                            }
                            else
                            {
                                showToast(response.body().getMessages().get(0).getText(), GlobalVariables.TOAST_ERRO);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseRegister> call, Throwable t) {
                            showToast(t.getMessage(), GlobalVariables.TOAST_ERRO);
                        }
                    });
                }
                else
                {
                    ApiService.Factory.getInstance().register(params).enqueue(new Callback<ResponseRegister>()
                    {
                        @Override
                        public void onResponse(@NonNull Call<ResponseRegister> call, @NonNull Response<ResponseRegister> response)
                        {
                            if (response.body().getStatus() == 1)
                            {
                                showToast(response.body().getMessages().get(0).getText(), GlobalVariables.TOAST_SUCCESS);
                                dauTuListener.addItem(response.body().getData().getUserApp());
                                dismiss();
                            }
                            else
                            {
                                showToast(response.body().getMessages().get(0).getText(), GlobalVariables.TOAST_ERRO);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseRegister> call, @NonNull Throwable t)
                        {
                            showToast(t.getMessage(), GlobalVariables.TOAST_ERRO);
                        }
                    });
                }

                break;
            }
            case R.id.genderEdt:
            {
                final String[] array = {"Nam", "Nữ"};
                AddingArrayDialog addingArrayDialog = AddingArrayDialog.initialize("Chọn giới tính", array, (position, value) ->
                {
                    valueGender = position + 1;
                    switch (position)
                    {
                        case 0:
                        {
                            genderEdt.setText(value);
                            break;
                        }
                        case 1:
                        {
                            genderEdt.setText(value);
                            break;
                        }
                        default:
                        {
                            LogManager.tagDefault().error("what value " + value);
                            break;
                        }
                    }
                });

                addingArrayDialog.show(getFragmentManager(), "Giới tính");
                break;
            }
            case R.id.llBirthday:
            {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                new SlideDateTimePicker.Builder(getFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {
                                txtBirthday.setText(format.format(date));
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

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
