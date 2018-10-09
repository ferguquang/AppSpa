package com.ngo.ducquang.appspa.profile;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.Share;
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.getAddress.District;
import com.ngo.ducquang.appspa.base.getAddress.Province;
import com.ngo.ducquang.appspa.base.view.AddingArrayDialog;
import com.ngo.ducquang.appspa.base.view.popupWindow.ItemPopupMenu;
import com.ngo.ducquang.appspa.base.view.popupWindow.ListPopupWindowAdapter;
import com.ngo.ducquang.appspa.oder.CategoryOptionAdapter;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;
import com.ngo.ducquang.appspa.login.modelRegister.ResponseRegister;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;
import com.ngo.ducquang.appspa.storageList.createStore.model.DataCreateStore;
import com.ngo.ducquang.appspa.storageList.createStore.model.ResponseCreateStore;
import com.ngo.ducquang.appspa.storageList.model.Category;
import com.ngo.ducquang.appspa.storageList.model.UserStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/23/2018.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener
{
    public static final int TYPE_USER = 1;
    public static final int TYPE_STORE = 2;

    private int type;
    private int position;
    private UserStore userStore;
    private boolean isAdmin;
    private StorageAdapter storageAdapter;

    @BindView(R.id.nameEdt) EditText nameEdt;
    @BindView(R.id.addressEdt) EditText addressEdt;
    @BindView(R.id.emailEdt) EditText emailEdt;
    @BindView(R.id.phoneEdt) EditText phoneEdt;
    @BindView(R.id.describeEdt) EditText describeEdt;
    @BindView(R.id.genderEdt) TextView genderEdt;
    private int valueGender;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.categorylayout) LinearLayout categorylayout;
    private List<Category> categoryList = new ArrayList<>();

    @BindView(R.id.oldPassEdt) EditText oldPassEdt;
    @BindView(R.id.newPassEdt) EditText newPassEdt;
    @BindView(R.id.repeatNewPassEdt) EditText repeatNewPassEdt;

    @BindView(R.id.cbChangePassword) CheckBox cbChangePassword;
    @BindView(R.id.layoutChangePassword) LinearLayout layoutChangePassword;

    @BindView(R.id.layoutContentPass) LinearLayout layoutContentPass;

    @BindView(R.id.llBirthday) LinearLayout llBirthday;
    @BindView(R.id.birthdayEdt) TextView birthdayEdt;

    @BindView(R.id.updateProfile) Button updateProfile;

    @BindView(R.id.valueProvince) TextView valueProvince;
    @BindView(R.id.valueDistrict) TextView valueDistrict;

    private UserApp userApp;

    private int idProvince = -1, idDistrict = -1;
    private String codeProvince = "";

    private List<District> districts = Share.getInstance().districts;
    private List<Province> provinces = Share.getInstance().provinces;


    private ListPopupWindow popupProvince;
    private List<ItemPopupMenu> listMenuProvince = new ArrayList<>();

    private ListPopupWindow popupDistrict;
    private List<ItemPopupMenu> listMenuDistrict = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView(View view)
    {
        showBackPressToolBar();
        genderEdt.setOnClickListener(this);
        updateProfile.setOnClickListener(this);
        llBirthday.setOnClickListener(this);

        valueProvince.setOnClickListener(this);
        valueDistrict.setOnClickListener(this);

        layoutChangePassword.setVisibility(View.GONE);
        cbChangePassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                layoutChangePassword.setVisibility(View.VISIBLE);
            }
            else
            {
                layoutChangePassword.setVisibility(View.GONE);
            }
        });

        if (type == TYPE_USER)
        {
            title.setText("Thông tin cá nhân");

            userApp = UserApp.initialize(PreferenceUtil.getPreferences(getContext(), PreferenceUtil.USER_APP, ""));
            nameEdt.setText(userApp.getName());
            phoneEdt.setText(userApp.getPhone());
            addressEdt.setText(userApp.getAddress());
            emailEdt.setText(userApp.getEmail());
            birthdayEdt.setText(ManagerTime.convertToMonthDayYear(userApp.getBirthday()));
            setGender(userApp.getGender());

            idProvince = userApp.getProvinceID();
            idDistrict = userApp.getDistrictID();

            valueProvince.setText(userApp.getProvinceName());
            valueDistrict.setText(userApp.getDistrictName());

            categorylayout.setVisibility(View.GONE);
        }
        else if (type == TYPE_STORE)
        {
            title.setText("Thông tin cửa hàng");

            nameEdt.setText(userStore.getName());
            phoneEdt.setText(userStore.getPhone());
            addressEdt.setText(userStore.getAddress());
            emailEdt.setText(userStore.getEmail());
            birthdayEdt.setText(ManagerTime.convertToMonthDayYear(userStore.getBirthday()));
            setGender(userStore.getGender());

            idProvince = userStore.getProvinceID();
            idDistrict = userStore.getDistrictID();

            valueProvince.setText(userStore.getProvinceName());
            valueDistrict.setText(userStore.getDistrictName());
            describeEdt.setText(userStore.getDescribe());

            // setCheck
            categoryList = Share.getInstance().categoryList;
            List<Category> categories = new ArrayList<>();
            categories.addAll(categoryList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
            CategoryOptionAdapter adapter = new CategoryOptionAdapter(getContext(), categories);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);

            for (int i = 0; i < userStore.getCategories().size(); i++)
            {
                Category category = userStore.getCategories().get(i);
                if (category.getiD() == categoryList.get(i).getiD())
                {
                    categories.get(i).setChecked(true);
                }
            }

            adapter.refreshData(categories);
        }

        setListPopupWindow();

        if (genderEdt.getText().toString().equals("Nam"))
        {
            valueGender = 1;
        }
        else
            valueGender = 2;
    }

    private void setListPopupWindow()
    {
        popupProvince = new ListPopupWindow(getContext());
        for (int i = 0; i < provinces.size(); i++)
        {
            int id = provinces.get(i).getiD();
            String name = provinces.get(i).getName();
            String code = provinces.get(i).getCode();
            listMenuProvince.add(new ItemPopupMenu(id, name, code));
        }

        ListAdapter adapterMenuProvince = new ListPopupWindowAdapter(getContext(), listMenuProvince);
        popupProvince.setAnchorView(valueProvince);
        popupProvince.setAdapter(adapterMenuProvince);
        popupProvince.setOnItemClickListener((parent, view1, position, id) ->
        {
            idProvince = listMenuProvince.get(position).getId();
            valueProvince.setText(listMenuProvince.get(position).getTitle());
            codeProvince = listMenuProvince.get(position).getCode();
            popupProvince.dismiss();

            valueDistrict.setText("Chọn Quận/huyện");
            idDistrict = -1;
            listMenuDistrict.clear();

            for (int i = 0; i < districts.size(); i++)
            {
                int idDistrict = districts.get(i).getiD();
                String name = districts.get(i).getName();
                String code = districts.get(i).getProvinceCode();
                if (codeProvince.equals(code))
                {
                    listMenuDistrict.add(new ItemPopupMenu(idDistrict, name, code));
                }
            }
        });

        popupDistrict = new ListPopupWindow(getContext());
        ListAdapter adapterDistrict = new ListPopupWindowAdapter(getContext(), listMenuDistrict);
        popupDistrict.setAnchorView(valueDistrict);
        popupDistrict.setAdapter(adapterDistrict);
        popupDistrict.setOnItemClickListener((parent, view1, position, id) ->
        {
            idDistrict = listMenuDistrict.get(position).getId();
            valueDistrict.setText(listMenuDistrict.get(position).getTitle());
            popupDistrict.dismiss();
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.valueDistrict:
            {
                popupDistrict.show();
                if (listMenuDistrict.size() == 0)
                {
                    showToast("Chọn Tỉnh/Thành phố trước", GlobalVariables.TOAST_INFO);
                }
                break;
            }
            case R.id.valueProvince:
            {
                popupProvince.show();
                break;
            }
            case R.id.updateProfile:
            {
                String fullName = nameEdt.getText().toString();
                String phone = phoneEdt.getText().toString();
                String address = addressEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String gender = genderEdt.getText().toString();
                String birthday = birthdayEdt.getText().toString();

                String describe = describeEdt.getText().toString();

                String oldPass = oldPassEdt.getText().toString();
                String newPass = newPassEdt.getText().toString();
                String repeatNewPass = repeatNewPassEdt.getText().toString();

                if (StringUtilities.isEmpty(fullName))
                {
                    nameEdt.setError("Không được để trống!!!");
                    nameEdt.requestFocus();
                    return;
                }
                if (StringUtilities.isEmpty(phone))
                {
                    phoneEdt.setError("Không được để trống!!!");
                    phoneEdt.requestFocus();
                    return;
                }

                if (StringUtilities.isEmpty(oldPass) || oldPass.length() < 6)
                {
                    oldPassEdt.requestFocus();
                    oldPassEdt.setError("Mật khẩu cần nhập tối thiểu 6 kí tự");
                    return;
                }

                if (idDistrict == -1)
                {
                    showToast("Vui lòng chọn Quận/huyện", GlobalVariables.TOAST_ERRO);
                    return;
                }
                if (idProvince == -1)
                {
                    showToast("Vui lòng chọn Tỉnh/Thành phố", GlobalVariables.TOAST_ERRO);
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("Token", PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, ""));
                params.put("Name", fullName);
                params.put("Phone", phone);
                params.put("Address", address);
                params.put("Email", email);
                params.put("Gender", valueGender + "");
                params.put("Birthday", birthday);
                params.put("IDProvince", idProvince + "");
                params.put("IDDistrict", idDistrict + "");

                if (cbChangePassword.isChecked())
                {
                    if (!newPass.equals(repeatNewPass))
                    {
                        newPassEdt.requestFocus();
                        newPassEdt.setError("Mật khẩu không khớp, vui lòng thử lại");
                        return;
                    }

                    params.put("IsResetPass", true + "");
                    params.put("PasswordChange", newPass);
                }
                else
                {
                    params.put("IsResetPass", false + "");
                    params.put("PasswordChange", "");
                }

                if (type == TYPE_USER)
                {
                    params.put("ID", userApp.getiDUser() + "");
                    params.put("PasswordOld", oldPass);

                    ApiService.Factory.getInstance().updateProfile(params).enqueue(new Callback<ResponseRegister>()
                    {
                        @Override
                        public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response)
                        {
                            if (response.body().getStatus() == 1)
                            {
                                showToast(response.body().getMessages().get(0).getText(), GlobalVariables.TOAST_SUCCESS);
                                UserApp userApp = response.body().getData().getUserApp();
                                PreferenceUtil.savePreferences(getContext(), PreferenceUtil.USER_APP, userApp.toJson());
                            }
                            else
                            {
                                showToast(response.body().getMessages().get(0).getText(), GlobalVariables.TOAST_ERRO);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseRegister> call, Throwable t) {

                        }
                    });
                }
                else if (type == TYPE_STORE)
                {
                    List<String> listIDCategory = new ArrayList<>();
                    for (int i = 0; i < categoryList.size(); i++)
                    {
                        RecyclerView.ViewHolder viewMain = recyclerView.findViewHolderForAdapterPosition(i);
                        CheckBox checkBox = viewMain.itemView.findViewById(R.id.checkbox);
                        if (checkBox.isChecked())
                        {
                            Category category = categoryList.get(i);
                            listIDCategory.add(category.getiD() + "");
                        }
                    }

                    String iDCategory = TextUtils.join(",", listIDCategory);

                    params.put("Describe", describe);
                    params.put("IDCategory", iDCategory);
                    params.put("PasswordAdmin", oldPass);
                    params.put("ID", userStore.getiDUser() + "");

                    showLoadingDialog();
                    ApiService.Factory.getInstance().updateStore(params).enqueue(new Callback<ResponseCreateStore>()
                    {
                        @Override
                        public void onResponse(@NonNull Call<ResponseCreateStore> call, @NonNull Response<ResponseCreateStore> response)
                        {
                            Message message = response.body().getMessages().get(0);
                            if (response.body().getStatus() == 1)
                            {
                                DataCreateStore dataCreateStore = response.body().getData();
                                storageAdapter.updateItem(dataCreateStore.getUserStore(), position);
                                showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
                                getActivity().onBackPressed();
                            }
                            else
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_ERRO);
                            }

                            hideLoadingDialog();
                        }

                        @Override
                        public void onFailure(Call<ResponseCreateStore> call, Throwable t) {
                            LogManager.tagDefault().error(t.getMessage());
                            hideLoadingDialog();
                        }
                    });
                }


                break;
            }
            case R.id.llBirthday:
            {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                new SlideDateTimePicker.Builder(getFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {
                                birthdayEdt.setText(format.format(date));
                            }
                        })
                        .setInitialDate(new Date())
                        .setIs24HourTime(true)
                        .setIndicatorColor(Color.parseColor(GlobalVariables.COLOR_PRIMARY))
                        .build()
                        .show();
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
        }
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

    private void disableUpdate()
    {
        nameEdt.setEnabled(false);
        phoneEdt.setEnabled(false);
//        spinnerCity.setClickable(false);
//        spinnerDistrict.setClickable(false);
        addressEdt.setEnabled(false);
        emailEdt.setEnabled(false);
        llBirthday.setEnabled(false);
        genderEdt.setClickable(false);
        nameEdt.setEnabled(false);
        nameEdt.setEnabled(false);

        layoutContentPass.setVisibility(View.GONE);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUserStorel(UserStore userStore) {
        this.userStore = userStore;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setStorageAdapter(StorageAdapter storageAdapter) {
        this.storageAdapter = storageAdapter;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
