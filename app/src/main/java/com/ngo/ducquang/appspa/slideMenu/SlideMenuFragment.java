package com.ngo.ducquang.appspa.slideMenu;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.EventBusManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by ducqu on 9/22/2018.
 */

public class SlideMenuFragment extends BaseFragment implements SlideMenuAdapter.EventCloseNavigation {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private SlideMenuAdapter adapter;
    private EventCloseNavigationInActivity eventCloseNavigationInActivity;

    private SubcriberSlideMenu subcriberSlideMenu = new SubcriberSlideMenu()
    {
        @Override
        @Subscribe(threadMode = ThreadMode.MAIN)
        void onEventUpdateHeader(EventUpdateHeader event) {
            adapter.refreshHeader();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        EventBusManager.instance().register(subcriberSlideMenu);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusManager.instance().unregister(subcriberSlideMenu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        eventCloseNavigationInActivity = (EventCloseNavigationInActivity) context;
    }

    @Override
    protected int getContentView() {
        return R.layout.slide_menu_fragment;
    }

    @Override
    protected void initView(View view)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new SlideMenuAdapter(getActivity(), this, this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void closeNavigation() {
        eventCloseNavigationInActivity.closeNavigationInActivity();
    }

    public interface EventCloseNavigationInActivity
    {
        void closeNavigationInActivity();
    }
}
