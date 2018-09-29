package com.ngo.ducquang.appspa.slideMenu;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by ducqu on 9/22/2018.
 */

public class SlideMenuFragment extends BaseFragment implements SlideMenuAdapter.EventCloseNavigation {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private SlideMenuAdapter adapter;
    private EventCloseNavigationInActivity eventCloseNavigationInActivity;

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
