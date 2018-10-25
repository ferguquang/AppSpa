package com.ngo.ducquang.appspa.oder.pendingApprove;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.oder.LoadMoreInterface;
import com.ngo.ducquang.appspa.oder.OrderListActivity;
import com.ngo.ducquang.appspa.oder.OrderListAdapter;
import com.ngo.ducquang.appspa.oder.model.DataListOrder;

import butterknife.BindView;

/**
 * Created by ducqu on 10/6/2018.
 */

public class PendingApproveFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private OrderListAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_oder;
    }

    @Override
    protected void initView(View view) {}

    public void setDataListOrder(DataListOrder dataListOrder)
    {
        try
        {
            if (adapter == null)
            {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                adapter = new OrderListAdapter((OrderListActivity) getActivity(), dataListOrder.getOrders(), getFragmentManager());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }
            else
            {
                if (dataListOrder.getOrders().size() > 0)
                {
                    adapter.updateData(dataListOrder.getOrders());
                }
            }
        }
        catch (Exception e)
        {
            LogManager.tagDefault().error(e.toString());
        }
    }
}
