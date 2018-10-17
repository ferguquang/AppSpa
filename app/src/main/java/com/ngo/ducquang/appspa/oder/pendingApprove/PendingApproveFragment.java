package com.ngo.ducquang.appspa.oder.pendingApprove;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
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
    protected void initView(View view) {
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
//        {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
//            {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (!recyclerView.canScrollVertically(1))
//                {
//                    loadMoreInterface.loadMore(skip, take);
//                }
//            }
//        });
    }

    public void setDataListOrder(DataListOrder dataListOrder)
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
}
