package com.ngo.ducquang.appspa.login;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.BuildConfig;
import com.ngo.ducquang.appspa.MainActivity;
import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.alarmService.ServiceManager;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.DrawableHelper;
import com.ngo.ducquang.appspa.base.EventBusManager;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Manager;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.Permission;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.getAddress.DataGetAddress;
import com.ngo.ducquang.appspa.base.getAddress.ResponseGetAddress;
import com.ngo.ducquang.appspa.login.modelLogin.DataLogin;
import com.ngo.ducquang.appspa.login.modelLogin.ResponseLogin;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/21/2018.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener
{
    @BindView(R.id.userName) EditText userName;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.login) CardView login;
    @BindView(R.id.register) CardView register;
    @BindView(R.id.imgAccount) ImageView imgAccount;
    @BindView(R.id.lock) ImageView lock;

    @BindView(R.id.copyLongitude) ImageView copyLongitude;
    @BindView(R.id.copyLatitude) ImageView copyLatitude;
    @BindView(R.id.imgLocation) ImageView imgLocation;

    @BindView(R.id.longitude) TextView txtLongitude;
    @BindView(R.id.latitude) TextView txtLatitude;
    @BindView(R.id.getCurrentLocation) RelativeLayout getCurrentLocation;

    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    String provider = "";

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private static final int REQUEST_PERMISSIONS = 96;

    private ArrayList<Permission> listPermission = new ArrayList<>();
    private static final int ACCESS_FINE_LOCATION = 0;
    private static final int ACCESS_COARSE_LOCATION = ACCESS_FINE_LOCATION + 1;

    final long MIN_TIME_BW_UPDATES = 1000;
    final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

    private SubcriberLogin subcriberLogin = new SubcriberLogin()
    {
        @Override
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onUserApp(EventUserApp event)
        {
            handleUserApp(event);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView()
    {
        hideToolBar();

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        copyLatitude.setOnClickListener(this);
        copyLongitude.setOnClickListener(this);
        getCurrentLocation.setOnClickListener(this);

        listPermission.add(new Permission(ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION));
        listPermission.add(new Permission(ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION));
        requestPermission();
//        checkLocationPermission();

        if (BuildConfig.DEBUG) {
            userName.setText("admin_cloud"); // todo only dev
            password.setText("123123");
        }

        ApiService.Factory.getInstance().getAddress().enqueue(new Callback<ResponseGetAddress>()
        {
            @Override
            public void onResponse(Call<ResponseGetAddress> call, Response<ResponseGetAddress> response)
            {
                if (response.body().getStatus() == 1)
                {
                    DataGetAddress dataGetAddress = response.body().getData();
                    PreferenceUtil.savePreferences(getApplicationContext(), PreferenceUtil.DATA_GET_ADDRESS, dataGetAddress.toJson());
                }
            }

            @Override
            public void onFailure(Call<ResponseGetAddress> call, Throwable t) {
                LogManager.tagDefault().debug();
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        DrawableHelper.withContext(getBaseContext()).withColor(R.color.white).withDrawable(R.drawable.icon_account).tint().applyTo(imgAccount);
        DrawableHelper.withContext(getBaseContext()).withColor(R.color.white).withDrawable(R.drawable.icon_lock).tint().applyTo(lock);
        DrawableHelper.withContext(getBaseContext()).withColor(R.color.white).withDrawable(R.drawable.icon_asset_my_location).tint().applyTo(imgLocation);

        try
        {
            provider = getEnabledLocationProvider();
        }
        catch (Exception e)
        {
            showToast(e.toString(), 3);
        }
    }

    private void getMyLocation()
    {
        if (StringUtilities.isEmpty(provider))
        {
            requestPermission();
        }

        provider = getEnabledLocationProvider();
        Location myLocation = null;
        try
        {
            locationManager.requestLocationUpdates(provider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
            myLocation = locationManager.getLastKnownLocation(provider);
        }
        catch (SecurityException e)  // Với Android API >= 23 phải catch SecurityException.
        {
            Log.e("TAG", "Show My Location Error:" + e.getMessage());
        }

        try
        {
            txtLongitude.setText(myLocation.getLongitude() + "");
            txtLatitude.setText(myLocation.getLatitude() + "");
            hideLoadingDialog();
        }
        catch (Exception e)
        {
            showToast("Lỗi vị trí GPS!!!", GlobalVariables.TOAST_INFO);
        }
    }

    private void requestPermission()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return;
        }

        String[] permission = new String[listPermission.size()];
        for (int i = 0; i < listPermission.size(); i++) {
            permission[i] = listPermission.get(i).getValue();
        }

        requestPermissions(permission, REQUEST_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
//                        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                        locationManager.requestLocationUpdates(provider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                        provider = getEnabledLocationProvider();
                    }
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBusManager.instance().register(subcriberLogin);
        disableServiceNotification();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBusManager.instance().unregister(subcriberLogin);
    }

    public void checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    protected void initMenu(Menu menu) {}

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.getCurrentLocation:
            {
                showToast("Đang tìm kiếm vị trí của bạn", GlobalVariables.TOAST_INFO);
                getMyLocation();
                break;
            }
            case R.id.copyLatitude:
            {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Đã sao chép vào bộ nhớ tạm", txtLatitude.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                showToast("Đã sao chép vào bộ nhớ tạm", GlobalVariables.TOAST_SUCCESS);
                break;
            }
            case R.id.copyLongitude:
            {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Đã sao chép vào bộ nhớ tạm", txtLongitude.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                showToast("Đã sao chép vào bộ nhớ tạm", GlobalVariables.TOAST_SUCCESS);
                break;
            }
            case R.id.login:
            {
                String name = userName.getText().toString();
                String pass = password.getText().toString();

                HashMap<String, String> params = new HashMap<>();
                params.put("Username", name);
                params.put("Password", pass);

                showLoadingDialog();
                ApiService.Factory.getInstance().login(params).enqueue(new Callback<ResponseLogin>()
                {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response)
                    {
                        if (response.body().getStatus() == 1) // login success
                        {
                            DataLogin dataLogin = response.body().getData();
                            ServiceManager.startService(getBaseContext());

                            PreferenceUtil.savePreferences(getApplicationContext(), PreferenceUtil.TOKEN, dataLogin.getToken());
                            PreferenceUtil.savePreferences(getApplicationContext(), PreferenceUtil.LOGIN_SUCCESS, true);
                            PreferenceUtil.savePreferences(getApplicationContext(), PreferenceUtil.USER_APP, dataLogin.getUserApp().toJson());
                            PreferenceUtil.savePreferences(getApplicationContext(), PreferenceUtil.POSITION_ID, dataLogin.getUserApp().getPositionID());
                            startActivity(MainActivity.class, null, true);
                            finish();
                        }
                        else
                        {
                            try
                            {
                                Message message = response.body().getMessages().get(0);
                                showToast(message.getText(), 0);
                            }
                            catch (Exception e)
                            {
                                LogManager.tagDefault().error(e.toString());
                            }
                        }

                        hideLoadingDialog();
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t)
                    {
                        LogManager.tagDefault().error(t.getMessage());
                        if (!Manager.isNetworkAvailable(getBaseContext()))
                        {
                            showToast("Xem lại kết nối mạng trên điện thoại của bạn", GlobalVariables.TOAST_ERRO);
                        }

                        hideLoadingDialog();
                    }
                });

                break;
            }
            case R.id.register:
            {
                RegisterFragment registerFragment = new RegisterFragment();
                registerFragment.setType(RegisterFragment.TYPE_USER);
                addFragment(registerFragment, null, true);
                break;
            }
        }
    }

    private LocationListener locationListener = new LocationListener()
    {
        @Override
        public void onLocationChanged(Location location)
        {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            txtLongitude.setText(longitude + "");
            txtLatitude.setText(latitude + "");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };

    private void handleUserApp(EventUserApp event) {
        userName.setText("");
        password.setText("");
    }

    public String getEnabledLocationProvider()
    {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
}
