package com.ngo.ducquang.appspa.login;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.alarmService.GPSTracker;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.CustomEditText;
import com.ngo.ducquang.appspa.base.EventBusManager;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.Share;
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.getAddress.DataGetAddress;
import com.ngo.ducquang.appspa.base.getAddress.District;
import com.ngo.ducquang.appspa.base.getAddress.Province;
import com.ngo.ducquang.appspa.base.view.AddingArrayDialog;
import com.ngo.ducquang.appspa.base.view.TextViewFont;
import com.ngo.ducquang.appspa.base.view.popupWindow.ItemPopupMenu;
import com.ngo.ducquang.appspa.base.view.popupWindow.ListPopupWindowAdapter;
import com.ngo.ducquang.appspa.login.modelRegister.ResponseRegister;
import com.ngo.ducquang.appspa.oder.CategoryOptionAdapter;
import com.ngo.ducquang.appspa.service.PriceServiceModel;
import com.ngo.ducquang.appspa.storageList.StoreActivity;
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
 * Created by ducqu on 9/21/2018.
 */

public class RegisterFragment extends BaseFragment implements View.OnClickListener
{
    public static final int TYPE_USER = 1;
    public static final int TYPE_STORE = 2;

    @BindView(R.id.register) TextViewFont register;

    @BindView(R.id.userNameEdt) CustomEditText userNameEdt;
    @BindView(R.id.fullNameEdt) CustomEditText fullNameEdt;
    @BindView(R.id.phoneEdt) CustomEditText phoneEdt;
    @BindView(R.id.describeEdt) CustomEditText describeEdt;
    @BindView(R.id.addressEdt) CustomEditText addressEdt;
    @BindView(R.id.emailEdt) CustomEditText emailEdt;
    @BindView(R.id.passwordEdt) CustomEditText passwordEdt;
    @BindView(R.id.repeatPasswordEdt) CustomEditText repeatPasswordEdt;

    @BindView(R.id.birthdayEdt) TextViewFont birthdayEdt;
    @BindView(R.id.llChooseDate) LinearLayout llChooseDate;

    @BindView(R.id.longitudeEdt) CustomEditText longitudeEdt;
    @BindView(R.id.latitudeEdt) CustomEditText latitudeEdt;

    @BindView(R.id.genderEdt) TextViewFont genderEdt;

    private int idProvince = -1, idDistrict = -1;

    List<District> districts = new ArrayList<>();
    List<Province> provinces = new ArrayList<>();

    private int valueGender = 2;

    @BindView(R.id.valueProvince) TextView valueProvince;
    @BindView(R.id.valueDistrict) TextView valueDistrict;
    private ListPopupWindow popupProvince;
    private List<ItemPopupMenu> listMenuProvince = new ArrayList<>();

    private ListPopupWindow popupDistrict;
    private List<ItemPopupMenu> listMenuDistrict = new ArrayList<>();
    private String codeProvince = "";

    private int type = -1;
    // của hàng:
    private SendUserStore sendUserStore;
    @BindView(R.id.categorylayout) LinearLayout categorylayout;
    private List<Category> categoryList = new ArrayList<>();
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @BindView(R.id.titleFullName) TextView titleFullName;

    final long MIN_TIME_BW_UPDATES = 1000;
    final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

    private LocationManager locationManager;
    String provider = "";
    @BindView(R.id.getCurrentLocation) LinearLayout getCurrentLocation;

    private GPSTracker gps;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StoreActivity) {
            sendUserStore = (SendUserStore) context;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.framgment_register;
    }

    @Override
    protected void initView(View view)
    {
        showBackPressToolBar();
        title.setText("Đăng kí");

        register.setOnClickListener(this);
        llChooseDate.setOnClickListener(this);
        genderEdt.setOnClickListener(this);
        valueProvince.setOnClickListener(this);
        valueDistrict.setOnClickListener(this);
        getCurrentLocation.setOnClickListener(this);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        provider = getEnabledLocationProvider();

        String dataAdress = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.DATA_GET_ADDRESS, "");
        DataGetAddress dataGetAddress = DataGetAddress.initialize(dataAdress);
        districts = dataGetAddress.getDistricts();
        provinces = dataGetAddress.getProvinces();

        setListPopupWindow();

        if (type == TYPE_USER) {
            categorylayout.setVisibility(View.GONE);

        } else if (type == TYPE_STORE)
        {
            categorylayout.setVisibility(View.VISIBLE);

            // list category
            categoryList = Share.getInstance().categoryList;

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            CategoryOptionAdapter adapter = new CategoryOptionAdapter(getActivity(), categoryList);
            adapter.setEnableEditPrice(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);

            fullNameEdt.setHint("Tên cửa hàng");
            titleFullName.setText("Tên cửa hàng");
        }

        gps = new GPSTracker(getContext());
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
//
//                if (StringUtilities.isEmpty(address))
//                {
//                    addressEdt.setError("Không được để trống!!!");
//                    addressEdt.requestFocus();
//                    return;
//                }

//                if (StringUtilities.isEmpty(birthday))
//                {
//                    showToast("Ngày sinh không được để trống", GlobalVariables.TOAST_ERRO);
//                    return;
//                }

//                if (StringUtilities.isEmpty(email))
//                {
//                    emailEdt.setError("Không được để trống!!!");
//                    emailEdt.requestFocus();
//                    return;
//                }

                if (!pass.equals(repeatPass))
                {
                    passwordEdt.setError("Mật khẩu không khớp, vui lòng thử lại");
                    passwordEdt.requestFocus();
                    return;
                }

                if (pass.length() < 6)
                {
                    passwordEdt.setError("Vui lòng nhập mật khẩu ít nhất 6 kí tự");
                    passwordEdt.requestFocus();
                    return;
                }

//                if (idDistrict == -1)
//                {
//                    showToast("Vui lòng chọn Quận/huyện", GlobalVariables.TOAST_ERRO);
//                    return;
//                }
//                if (idProvince == -1)
//                {
//                    showToast("Vui lòng chọn Tỉnh/Thành phố", GlobalVariables.TOAST_ERRO);
//                    return;
//                }

                HashMap<String, String> params = new HashMap<>();
                params.put("Username", userName);
                params.put("Name", fullName);
                params.put("Password", pass);
                params.put("Phone", phone);
                params.put("Address", address);
                params.put("Birthday", birthday);
                params.put("Email", email);
                params.put("Gender", valueGender + "");
                params.put("IDProvince", idProvince + "");
                params.put("IDDistrict", idDistrict + "");

                if (type == TYPE_USER)
                {
                    showLoadingDialog();
                    ApiService.Factory.getInstance().register(params).enqueue(new Callback<ResponseRegister>() {
                        @Override
                        public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response)
                        {
                            Message message = response.body().getMessages().get(0);
                            if (response.body().getStatus() == 1) // success
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
                                getActivity().onBackPressed();
                                EventBusManager.instance().post(new EventUserApp());
                            }
                            else
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_ERRO);
                            }

                            hideLoadingDialog();
                        }

                        @Override
                        public void onFailure(Call<ResponseRegister> call, Throwable t) {
                            LogManager.tagDefault().error(t.getMessage());
                        }
                    });
                }
                else if (type == TYPE_STORE)
                {
                    List<String> listIDCategory = new ArrayList<>();
                    List<PriceServiceModel> priceServiceModels = new ArrayList<>();
                    for (int i = 0; i < categoryList.size(); i++)
                    {
                        RecyclerView.ViewHolder viewMain = recyclerView.findViewHolderForAdapterPosition(i);
                        CheckBox checkBox = viewMain.itemView.findViewById(R.id.checkbox);
                        EditText priceEdit = viewMain.itemView.findViewById(R.id.priceEdit);
                        if (checkBox.isChecked())
                        {
                            Category category = categoryList.get(i);
                            listIDCategory.add(category.getiD() + "");

                            String key = "Price" + category.getiD();
                            String price = priceEdit.getText().toString();
                            priceServiceModels.add(new PriceServiceModel(key, price));
                        }
                    }

                    String iDCategory = TextUtils.join(",", listIDCategory);

                    String latitude = latitudeEdt.getText().toString();
                    String longitude = longitudeEdt.getText().toString();

//                    if (StringUtilities.isEmpty(latitude))
//                    {
//                        latitudeEdt.setError("Không được để trống!!!");
//                        latitudeEdt.requestFocus();
//                        return;
//                    }
//                    if (StringUtilities.isEmpty(longitude))
//                    {
//                        longitudeEdt.setError("Không được để trống!!!");
//                        longitudeEdt.requestFocus();
//                        return;
//                    }

                    params.put("IDCategory", iDCategory);
                    params.put("Token", PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, ""));
                    params.put("Describe", describe);
                    params.put("Latitude", latitude + "");
                    params.put("Longitude", longitude + "");
                    if (priceServiceModels.size() == 0)
                    {
                        showToast("Chọn ít nhất 1 dịch vụ", GlobalVariables.TOAST_ERRO);
                        return;
                    }

                    for (int i = 0; i < priceServiceModels.size(); i++)
                    {
                        PriceServiceModel model = priceServiceModels.get(i);
                        params.put(model.getKey(), model.getValuePrice());
                    }

                    showLoadingDialog();
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

                            hideLoadingDialog();
                        }

                        @Override
                        public void onFailure(Call<ResponseCreateStore> call, Throwable t) {
                            hideLoadingDialog();
                            LogManager.tagDefault().error(t.getMessage());
                        }
                    });
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
            case R.id.getCurrentLocation:
            {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

                getMyLocation();
                if(gps.canGetLocation())
                {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    longitudeEdt.setText(longitude + "");
                    latitudeEdt.setText(latitude + "");
                }
                else
                {
                    gps.showSettingsAlert();
                }
                break;
            }
        }
    }

    private LocationListener mLocationListener = new LocationListener()
    {
        @Override
        public void onLocationChanged(final Location location) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };

    private void getMyLocation()
    {
        Location myLocation = null;
        try
        {
            locationManager.requestLocationUpdates(provider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
            myLocation = locationManager.getLastKnownLocation(provider);
        }
        catch (SecurityException e)  // Với Android API >= 23 phải catch SecurityException.
        {
            Log.e("TAG", "Show My Location Error:" + e.getMessage());
            return;
        }

//        if (myLocation == null || StringUtilities.isEmpty(provider))
//        {
//            showToast("Yêu cầu bật dịch vụ vị trí trên điện thoại!!!", GlobalVariables.TOAST_INFO);
//            return;
//        }

        try
        {
            longitudeEdt.setText(myLocation.getLongitude() + "");
            latitudeEdt.setText(myLocation.getLatitude() + "");
        }
        catch (Exception e)
        {
            LogManager.tagDefault().error(e.toString());
        }

        hideLoadingDialog();
    }

    public String getEnabledLocationProvider()
    {
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        boolean enabled = locationManager.isProviderEnabled(bestProvider);

        if (!enabled)
        {
            Log.i("TAG", "No location provider enabled!");
            return null;
        }
        return bestProvider;
    }

    public void setType(int type) {
        this.type = type;
    }

    public interface SendUserStore
    {
        void sendUserStore(UserStore userStore);
    }
}
