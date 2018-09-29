package com.ngo.ducquang.appspa.login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ngo.ducquang.appspa.BuildConfig;
import com.ngo.ducquang.appspa.MainActivity;
import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.alarmService.AlarmSend;
import com.ngo.ducquang.appspa.alarmService.ServiceManager;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.EventBusManager;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Manager;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.Share;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.database.DatabaseRoom;
import com.ngo.ducquang.appspa.base.getAddress.ResponseGetAddress;
import com.ngo.ducquang.appspa.login.modelLogin.DataLogin;
import com.ngo.ducquang.appspa.login.modelLogin.ResponseLogin;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        disableServiceNotification();

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
                try
                {
                    Share.getInstance().provinces.clear();
                    Share.getInstance().districts.clear();
                    
                    Share.getInstance().provinces = response.body().getData().getProvinces();
                    Share.getInstance().districts = response.body().getData().getDistricts();
                }
                catch (Exception e)
                {
                    LogManager.tagDefault().error(e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseGetAddress> call, Throwable t) {
                LogManager.tagDefault().debug();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBusManager.instance().register(subcriberLogin);
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

//                            AlarmSend.enableBootReceiver(getApplicationContext());
//                            AlarmSend.setAlarm(getApplicationContext(), "tesst");

                              ServiceManager.startService(getBaseContext());
//                            startService(new Intent(getBaseContext(), ServiceManager.class));
//                            Intent intent = new Intent(getBaseContext(), ServiceManager.class);
//                            PendingIntent pintent = PendingIntent.getService(getBaseContext(), 0, intent, 0);
//
//                            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                            alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60, pintent);

                            PreferenceUtil.savePreferences(getApplicationContext(), PreferenceUtil.TOKEN, dataLogin.getToken());
                            PreferenceUtil.savePreferences(getApplicationContext(), PreferenceUtil.LOGIN_SUCCESS, true);
                            PreferenceUtil.savePreferences(getApplicationContext(), PreferenceUtil.USER_APP, dataLogin.getUserApp().toJson());
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
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        LogManager.tagDefault().error(t.getMessage());
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
