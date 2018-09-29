package com.ngo.ducquang.appspa.userList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.EmptyViewHolder;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 9/28/2018.
 */

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final String ID_USER = "iduser";

    public int TYPE_EMPTY = 0;
    public int TYPE_ITEM = 1;

    private UserListActivity context;
    private List<UserApp> dataList;

    public UserListAdapter(UserListActivity context, List<UserApp> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;

        if (viewType == TYPE_EMPTY)
        {
            view = inflater.inflate(R.layout.layout_empty, parent, false);
            return new EmptyViewHolder(view);
        }
        else if (viewType == TYPE_ITEM)
        {
            view = inflater.inflate(R.layout.item_user_list, parent, false);
            return new ItemHolder(view);
        }

        throw new RuntimeException("k có type nào");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder)
        {
            ((EmptyViewHolder) holder).setTextEmpty("Không có khách hàng nào");
        }

        if (holder instanceof ItemHolder)
        {
            ((ItemHolder) holder).binding(dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() == 0 ? 1 : dataList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (dataList.size() == 0 || dataList == null)
        {
            return TYPE_EMPTY;
        }
        return TYPE_ITEM;
    }


    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.addressDetail) TextView addressDetail;
        @BindView(R.id.phone) TextView phone;
        @BindView(R.id.cvGroup) CardView cvGroup;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cvGroup.setOnClickListener(this);
        }

        public void binding(UserApp model)
        {
            name.setText(model.getName());
            address.setText(model.getDistrictName() + " - " + model.getProvinceName());
            phone.setText(model.getPhone());
            addressDetail.setText(model.getAddress());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.cvGroup:
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ID_USER, dataList.get(getAdapterPosition()).getiDUser());
                    context.addFragment(new UserDetailFragment(), bundle, true);
                    break;
                }
            }
        }
    }
}
