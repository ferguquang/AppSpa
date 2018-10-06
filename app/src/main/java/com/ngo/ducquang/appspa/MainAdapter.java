package com.ngo.ducquang.appspa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngo.ducquang.appspa.oder.OrderFragment;
import com.ngo.ducquang.appspa.notification.NotificationActivity;
import com.ngo.ducquang.appspa.oder.OrderListActivity;
import com.ngo.ducquang.appspa.service.ServiceAdminActivity;
import com.ngo.ducquang.appspa.storageList.StoreActivity;
import com.ngo.ducquang.appspa.userList.UserListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 9/25/2018.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<ModelItemMain> dataList;
    private FragmentManager fragmentManager;
    private MainActivity context;

    public MainAdapter(List<ModelItemMain> dataList, FragmentManager fragmentManager, MainActivity context) {
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_main_activity, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder)
        {
            ((ItemHolder) holder).bindDing(dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.iconImage) ImageView iconImage;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.content) TextView content;
        @BindView(R.id.cvGroup) CardView cvGroup;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cvGroup.setOnClickListener(this);
        }

        public void bindDing(ModelItemMain model)
        {
            iconImage.setImageResource(model.getIdImage());
            name.setText(model.getName().toUpperCase());
            content.setText(model.getContent());
        }

        @Override
        public void onClick(View v)
        {
            switch (dataList.get(getAdapterPosition()).getId())
            {
                case MainActivity.BOOK:
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt(OrderFragment.TYPE, OrderFragment.ORDER_NORMAL);
                    context.addFragment(new OrderFragment(), bundle, true);
                    break;
                }
                case MainActivity.STORE_LIST:
                {
                    context.startActivity(StoreActivity.class, null, false);
                    break;
                }
                case MainActivity.NOTIFICATION:
                {
                    context.startActivity(NotificationActivity.class, null, false);
                    break;
                }
                case MainActivity.BOOK_AT_HOME:
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt(OrderFragment.TYPE, OrderFragment.ORDER_AT_HOME);

                    context.addFragment(new OrderFragment(), bundle, true);
                    break;
                }
                case MainActivity.LIST_USER:
                {
                    context.startActivity(UserListActivity.class, null, false);
                    break;
                }
                case MainActivity.LIST_SERVICE:
                {
                    context.startActivity(ServiceAdminActivity.class, null, false);
                    break;
                }
                case MainActivity.LIST_ORDER:
                {
                    context.startActivity(OrderListActivity.class, null, false);
                    break;
                }
            }
        }
    }
}
