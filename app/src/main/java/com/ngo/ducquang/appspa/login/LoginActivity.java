package com.ngo.ducquang.appspa.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ngo.ducquang.appspa.BuildConfig;
import com.ngo.ducquang.appspa.MainActivity;
import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.alarmService.AlarmSend;
import com.ngo.ducquang.appspa.alarmService.ServiceManager;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.DrawableHelper;
import com.ngo.ducquang.appspa.base.EventBusManager;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Manager;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.Share;
import com.ngo.ducquang.appspa.base.api.ApiService;
//import com.ngo.ducquang.appspa.base.database.DatabaseRoom;
import com.ngo.ducquang.appspa.base.getAddress.DataGetAddress;
import com.ngo.ducquang.appspa.base.getAddress.ResponseGetAddress;
import com.ngo.ducquang.appspa.login.modelLogin.DataLogin;
import com.ngo.ducquang.appspa.login.modelLogin.ResponseLogin;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

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

    @BindView(R.id.longitude) TextView txtLongitude;
    @BindView(R.id.latitude) TextView txtLatitude;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    protected LocationManager locationManager;

    private double latitude;
    private double longitude;

    private SubcriberLogin subcriberLogin = new SubcriberLogin()
    {
        @Override
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onUserApp(EventUserApp event) {
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

        if (BuildConfig.DEBUG)
        {
            userName.setText("admin_cloud"); // todo only dev
            password.setText("123123");
        }

        ApiService.Factory.getInstance().getAddress().enqueue(new Callback<ResponseGetAddress>() {
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

        DrawableHelper.withContext(getBaseContext()).withColor(R.color.white).withDrawable(R.drawable.icon_account).tint().applyTo(imgAccount);
        DrawableHelper.withContext(getBaseContext()).withColor(R.color.white).withDrawable(R.drawable.icon_lock).tint().applyTo(lock);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Dexter.withActivity(this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener()
                {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report)
                    {
                        if (report.areAllPermissionsGranted())
                        {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                            showToast("Đang tìm kiếm vị trí của bạn", GlobalVariables.TOAST_INFO);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token)
                    {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private final LocationListener mLocationListener = new LocationListener()
    {
        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationChanged(Location location)
        {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            txtLongitude.setText(longitude + "");
            txtLatitude.setText(latitude + "");
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };



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

    @Override
    protected void initMenu(Menu menu) {}

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
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
                            try {
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

    private void handleUserApp(EventUserApp event) {
        userName.setText("");
        password.setText("");
    }
}
