package com.ngo.ducquang.appspa.slideMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.alarmService.NotificationMessage;
import com.ngo.ducquang.appspa.alarmService.ServiceManager;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Manager;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.font.FontChangeCrawler;
import com.ngo.ducquang.appspa.base.view.ConfirmDialog;
import com.ngo.ducquang.appspa.base.view.OnConfirmDialogAction;
import com.ngo.ducquang.appspa.login.LoginActivity;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;
import com.ngo.ducquang.appspa.profile.ProfileFragment;
import com.ngo.ducquang.appspa.slideMenu.logout.ResponseLogout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/22/2018.
 */

public class SlideMenuAdapter extends RecyclerView.Adapter
{
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 2;
    private final int TYPE_LOGOUT = 3;

    private Context context;
    private SlideMenuFragment slideMenuFragment;
    private EventCloseNavigation eventCloseNavigation;

    public SlideMenuAdapter(Context context, SlideMenuFragment slideMenuFragment, EventCloseNavigation eventCloseNavigation) {
        this.context = context;
        this.slideMenuFragment = slideMenuFragment;
        this.eventCloseNavigation = eventCloseNavigation;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), GlobalVariables.FONT_BASE);

        View view = null;
        if (viewType == TYPE_HEADER)
        {
            view = inflater.inflate(R.layout.layout_header_menu, parent, false);
            fontChanger.replaceFonts((ViewGroup) view);
            return new HeaderViewHolder(view);
        }
        else if (viewType == TYPE_ITEM)
        {
            view = inflater.inflate(R.layout.item_slide_menu, parent, false);
            fontChanger.replaceFonts((ViewGroup) view);
            return new ItemMenuViewHolder(view);
        }
        else if (viewType == TYPE_LOGOUT)
        {
            view = inflater.inflate(R.layout.layout_logout_menu, parent, false);
            fontChanger.replaceFonts((ViewGroup) view);
            return new LogoutViewHolder(view);
        }

        throw new RuntimeException("k có type nào");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof HeaderViewHolder)
        {
            try {
                ((HeaderViewHolder) holder).binding();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
        {
            return TYPE_HEADER;
        }
        else
        {
            return TYPE_LOGOUT;
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.phone) TextView phone;
        @BindView(R.id.address) TextView address;

        public HeaderViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v ->
            {
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setType(ProfileFragment.TYPE_USER);
                slideMenuFragment.addFragment(profileFragment, null, true);
                eventCloseNavigation.closeNavigation();
            });
        }

        public void binding() throws Exception
        {
            UserApp userApp = UserApp.initialize(PreferenceUtil.getPreferences(context, PreferenceUtil.USER_APP, ""));
            name.setText(userApp.getName());
            phone.setText(userApp.getPhone());
            address.setText(userApp.getAddress());
        }
    }

    public class ItemMenuViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.name) TextView name;

        public ItemMenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class LogoutViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.name) TextView name;

        public LogoutViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

            icon.setImageResource(R.drawable.icon_logout);
            name.setText("Đăng xuất");
            itemView.setOnClickListener(v ->
            {
                ConfirmDialog.initialize("Bạn có muốn đăng xuất không?", new OnConfirmDialogAction()
                {
                    @Override
                    public void onCancel() {}

                    @Override
                    public void onAccept()
                    {
                        slideMenuFragment.showLoadingDialog();
                        ApiService.Factory.getInstance().logout(PreferenceUtil.getPreferences(context, PreferenceUtil.TOKEN, "")).enqueue(new Callback<ResponseLogout>()
                        {
                            @Override
                            public void onResponse(Call<ResponseLogout> call, Response<ResponseLogout> response)
                            {
                                if (response.body().getStatus() == 1)
                                {
                                    PreferenceUtil.clearPreference(context);
                                    NotificationMessage.removeAllNotification(context);

                                    ServiceManager.stopService(context);

                                    Manager.startActivity(context, LoginActivity.class, true);
                                    ((FragmentActivity) context).finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseLogout> call, Throwable t) {
                                LogManager.tagDefault().error(t.getMessage());
                                slideMenuFragment.hideLoadingDialog();
                            }
                        });
                    }
                }).show(slideMenuFragment.getFragmentManager(), "tag");
            });
        }
    }

    public void refreshHeader()
    {
        notifyItemChanged(0);
    }

    public interface EventCloseNavigation
    {
        void closeNavigation();
    }
}
