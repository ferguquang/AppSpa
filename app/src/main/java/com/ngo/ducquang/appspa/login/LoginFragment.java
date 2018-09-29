package com.ngo.ducquang.appspa.login;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ngo.ducquang.appspa.BuildConfig;
import com.ngo.ducquang.appspa.MainActivity;
import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.alarmService.AlarmSend;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.EventBusManager;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Manager;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.database.DatabaseRoom;
import com.ngo.ducquang.appspa.login.modelLogin.DataLogin;
import com.ngo.ducquang.appspa.login.modelLogin.ResponseLogin;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;

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

public class LoginFragment extends BaseFragment implements View.OnClickListener
{
    @BindView(R.id.userName) EditText userName;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.login) CardView login;
    @BindView(R.id.register) CardView register;
    @BindView(R.id.relativeLayout) RelativeLayout relativeLayout;

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
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View view) {
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        if (BuildConfig.DEBUG)
        {
            userName.setText("admin_cloud"); // todo only dev
            password.setText("123123");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusManager.instance().register(subcriberLogin);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusManager.instance().unregister(subcriberLogin);
    }

    @Override
    public void onClick(View v) {
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
                            PreferenceUtil.savePreferences(getContext(), PreferenceUtil.TOKEN, dataLogin.getToken());

                            PreferenceUtil.savePreferences(getContext(), PreferenceUtil.LOGIN_SUCCESS, true);
                            getActivity().finish();
//                            Manager.startActivity(getContext(), MainActivity.class);
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
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        hideLoadingDialog();
                    }
                });

                break;
            }
            case R.id.register:
            {
                addFragment(new RegisterFragment(), null, true);
                break;
            }
        }
    }

    private void handleUserApp(EventUserApp event) {
        userName.setText("");
        password.setText("");
    }
}
