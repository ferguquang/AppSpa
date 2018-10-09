package com.ngo.ducquang.appspa.oder;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.view.TabPagerAdapter;
import com.ngo.ducquang.appspa.base.view.TransformerFadeViewPager;
import com.ngo.ducquang.appspa.oder.approved.ApprovedFragment;
import com.ngo.ducquang.appspa.oder.done.DoneFragment;
import com.ngo.ducquang.appspa.oder.model.DataListOrder;
import com.ngo.ducquang.appspa.oder.model.ResponseListOder;
import com.ngo.ducquang.appspa.oder.pendingApprove.PendingApproveFragment;
import com.ngo.ducquang.appspa.oder.reject.RejectFragment;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 10/4/2018.
 */

public class OrderListActivity extends BaseActivity implements BottomSheetOrder.CallBackActionOrder
{
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private TabPagerAdapter adapter;

    private int status = 1;

//    @BindView(R.id.recyclerView) RecyclerView recyclerView;
//    private OrderListAdapter adapter;

    private HashMap<String, String> params = new HashMap<>();

    private PendingApproveFragment pendingApproveFragment;
    private ApprovedFragment approvedFragment;
    private DoneFragment doneFragment;
    private RejectFragment rejectFragment;

    private String token = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initView()
    {
        hideMenu();
        showIconBack();
        title.setText("Danh sách lịch đặt");

        token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");

        adapter = new TabPagerAdapter(getSupportFragmentManager());

        pendingApproveFragment = new PendingApproveFragment();
        approvedFragment = new ApprovedFragment();
        doneFragment = new DoneFragment();
        rejectFragment = new RejectFragment();

        adapter.addFragment(pendingApproveFragment, "Chờ xác nhận");
        adapter.addFragment(approvedFragment, "Đã xác nhận");
        adapter.addFragment(doneFragment, "Hoàn thành");
        adapter.addFragment(rejectFragment, "Từ chối");

        viewPager.setPageTransformer(false, new TransformerFadeViewPager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(adapter.getCount());

        params.put("Token", token);
        params.put("Status", 1 + "");
        showLoadingDialog();
        ApiService.Factory.getInstance().getListOrder(params).enqueue(callbackListOrder());

        params.put("Token", PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, ""));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position)
            {
                params.clear();
                status = position + 1;
                params.put("Token", token);
                params.put("Status", status + "");
                showLoadingDialog();
                ApiService.Factory.getInstance().getListOrder(params).enqueue(callbackListOrder());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private Callback<ResponseListOder> callbackListOrder()
    {
        return new Callback<ResponseListOder>()
        {
            @Override
            public void onResponse(Call<ResponseListOder> call, Response<ResponseListOder> response)
            {
                if (response.body().getStatus() == 1)
                {
                    setData(response.body().getData());
                }

                hideLoadingDialog();
            }

            @Override
            public void onFailure(Call<ResponseListOder> call, Throwable t)
            {
                showToast(t.getMessage(), GlobalVariables.TOAST_ERRO);
                hideLoadingDialog();
            }
        };
    }

    private void setData(DataListOrder data)
    {
        switch (status)
        {
            case 1:
            {
                pendingApproveFragment.setDataListOrder(data);
                break;
            }
            case 2:
            {
                approvedFragment.setDataListOrder(data);
                break;
            }
            case 3:
            {
                doneFragment.setDataListOrder(data);
                break;
            }
            case 4:
            {
                rejectFragment.setDataListOrder(data);
                break;
            }
        }

        hideLoadingDialog();
    }

    @Override
    protected void initMenu(Menu menu) {}

    @Override
    public void refreshOrder()
    {
        params.clear();
        params.put("Token", token);
        params.put("Status", status + "");
        showLoadingDialog();
        ApiService.Factory.getInstance().getListOrder(params).enqueue(callbackListOrder());
    }

    public OrderListActivity getActivityMain()
    {
        return OrderListActivity.this;
    }
}
