package com.ngo.ducquang.appspa.storageList.createStore;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
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
import com.ngo.ducquang.appspa.base.CustomEditText;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
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
 * Created by ducqu on 9/26/2018.
 */

public class CreateStoreFragment extends BaseFragment implements View.OnClickListener
{
    @BindView(R.id.register) Button register;
    @BindView(R.id.tilUserName) TextInputLayout tilUserName;

    @BindView(R.id.userNameEdt) CustomEditText userNameEdt;
    @BindView(R.id.fullNameEdt) CustomEditText fullNameEdt;
    @BindView(R.id.phoneEdt) CustomEditText phoneEdt;
    @BindView(R.id.addressEdt) CustomEditText addressEdt;
    @BindView(R.id.emailEdt) CustomEditText emailEdt;
    @BindView(R.id.passwordEdt) CustomEditText passwordEdt;
    @BindView(R.id.repeatPasswordEdt) CustomEditText repeatPasswordEdt;

    @BindView(R.id.birthdayEdt) TextView birthdayEdt;
    @BindView(R.id.llChooseDate) LinearLayout llChooseDate;

    @BindView(R.id.tilDescribe) TextInputLayout tilDescribe;
    @BindView(R.id.describeEdt) CustomEditText describeEdt;

    @BindView(R.id.genderEdt) TextView genderEdt;
    private int valueGender = -1;


    private List<Category> categoryList = new ArrayList<>();
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @BindView(R.id.cbChangePassword) CheckBox cbChangePassword;
    @BindView(R.id.passwordOldEdt) EditText passwordOldEdt;
    @BindView(R.id.llChangePassUpdate) LinearLayout llChangePassUpdate;

    private int idProvince = -1, idDistrict = -1;

    private List<District> districts = Share.getInstance().districts;
    private List<Province> provinces = Share.getInstance().provinces;

    @BindView(R.id.valueProvince) TextView valueProvince;
    @BindView(R.id.valueDistrict) TextView valueDistrict;
    private ListPopupWindow popupProvince;
    private List<ItemPopupMenu> listMenuProvince = new ArrayList<>();

    private ListPopupWindow popupDistrict;
    private List<ItemPopupMenu> listMenuDistrict = new ArrayList<>();
    private String codeProvince = "";

    private boolean isCreateStore = false;
    private UserStore userStore;
    private int postion = -1;

    private SendUserStore sendUserStore;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendUserStore = (SendUserStore) context;
    }

    @Override
    protected int getContentView() {
        return R.layout.framgment_create_store;
    }

    @Override
    protected void initView(View view) {
        showBackPressToolBar();
        title.setText("Thêm mới cửa hàng");

        register.setOnClickListener(this);
        llChooseDate.setOnClickListener(this);
        genderEdt.setOnClickListener(this);
        valueProvince.setOnClickListener(this);
        valueDistrict.setOnClickListener(this);

        tilDescribe.setVisibility(View.VISIBLE);

        categoryList = Share.getInstance().categoryList;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        CategoryOptionAdapter adapter = new CategoryOptionAdapter(getActivity(), categoryList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        if (isCreateStore)
        {
            llChangePassUpdate.setVisibility(View.GONE);
        }
        else
        {
            llChangePassUpdate.setVisibility(View.VISIBLE);
            tilUserName.setVisibility(View.GONE);

            if (userStore != null)
            {
                userNameEdt.setText(userStore.getName());
            }
        }

        setListPopupWindow();
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.register:
            {
                String userName = userNameEdt.getText().toString();
                String fullName = fullNameEdt.getText().toString();
                String phone = phoneEdt.getText().toString();
                String address = addressEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String pass = passwordEdt.getText().toString();
                String birthday = birthdayEdt.getText().toString();
                String repeatPass = repeatPasswordEdt.getText().toString();
                String describe = describeEdt.getText().toString();

                if (StringUtilities.isEmpty(userName))
                {
                    userNameEdt.setError("Không được để trống!!!");
                    userNameEdt.requestFocus();
                    return;
                }
                if (StringUtilities.isEmpty(fullName))
                {
                    fullNameEdt.setError("Không được để trống!!!");
                    fullNameEdt.requestFocus();
                    return;
                }
                if (StringUtilities.isEmpty(phone))
                {
                    phoneEdt.setError("Không được để trống!!!");
                    phoneEdt.requestFocus();
                    return;
                }

                if (valueGender == -1)
                {
                    showToast("Vui lòng chọn giới tính!!!", GlobalVariables.TOAST_ERRO);
                    return;
                }

                if (!pass.equals(repeatPass))
                {
                    passwordEdt.setError("Mật khẩu không khớp, vui lòng thử lại");
                    passwordEdt.requestFocus();
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


                HashMap<String, String> params = new HashMap<>();
                params.put("Token", PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, ""));
                params.put("Username", userName);
                params.put("Name", fullName);
                params.put("Password", pass);
                params.put("Phone", phone);
                params.put("Address", address);
                params.put("Birthday", birthday);
                params.put("Email", email);
                params.put("Describe", describe);
                params.put("Gender", valueGender + "");
                params.put("IDProvince", idProvince + "");
                params.put("IDDistrict", idDistrict + "");
                params.put("IDCategory", iDCategory);

                showLoadingDialog();

                if (isCreateStore) // thêm mới cửa hàng
                {
                    ApiService.Factory.getInstance().createStore(params).enqueue(new Callback<ResponseCreateStore>()
                    {
                        @Override
                        public void onResponse(Call<ResponseCreateStore> call, Response<ResponseCreateStore> response)
                        {
                            Message message = response.body().getMessages().get(0);
                            if (response.body().getStatus() == 1)
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
                                getActivity().onBackPressed();
                                UserStore userStore = response.body().getData().getUserStore();
                                sendUserStore.sendUserStore(userStore);
                            }
                            else
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_ERRO);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseCreateStore> call, Throwable t) {
                            hideLoadingDialog();
                            LogManager.tagDefault().error(t.getMessage());
                        }
                    });
                }
                else // chỉnh sửa cửa hàng
                {
                    params.put("ID", "");
                    params.put("IsResetPass", "");
                    params.put("PasswordChange", "");
                    params.put("PasswordOld", "");
//                    ApiService.Factory.getInstance().updateStore(params).enqueue(new Callback<ResponseRegister>() {
//                        @Override
//                        public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseRegister> call, Throwable t) {
//
//                        }
//                    });
                }

                break;
            }
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
            case R.id.llChooseDate:
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
                AddingArrayDialog addingArrayDialog = AddingArrayDialog.initialize("Chọn giới tính", array, (position, value) -> {
                    valueGender = position;
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

    public void setCreateStore(boolean createStore) {
        isCreateStore = createStore;
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public interface SendUserStore
    {
        void sendUserStore(UserStore userStore);
    }
}
