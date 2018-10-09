package com.ngo.ducquang.appspa.storageList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.EmptyViewHolder;
import com.ngo.ducquang.appspa.base.FooterViewHolder;
import com.ngo.ducquang.appspa.base.Manager;
import com.ngo.ducquang.appspa.storageList.createStore.CreateStoreFragment;
import com.ngo.ducquang.appspa.storageList.model.UserStore;
import com.ngo.ducquang.appspa.storageList.storeDetail.StoreDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 9/21/2018.
 */

public class StorageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final String ID_STORE = "idstore";

    private final int TYPE_EMPTY = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    private StoreActivity context;
    private List<UserStore> dataList;
    private FragmentManager fragmentManager;
    private boolean isAdmin;

    public StorageAdapter(StoreActivity context, List<UserStore> dataList, FragmentManager fragmentManager, boolean isAdmin) {
        this.context = context;
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;
        this.isAdmin = isAdmin;
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
            view = inflater.inflate(R.layout.item_store, parent, false);
            return new ItemHolder(view);
        }
        else if (viewType == TYPE_FOOTER)
        {
            view = inflater.inflate(R.layout.listview_footer, parent, false);
            return new FooterViewHolder(view);
        }

        throw new RuntimeException("k có type nào");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof EmptyViewHolder)
        {
            ((EmptyViewHolder) holder).setTextEmpty("Không có cửa hàng nào");
        }

        if (holder instanceof ItemHolder)
        {
            ((ItemHolder) holder).binding(dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() == 0 ? 1 : dataList.size() + 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (dataList.size() == 0)
        {
            return TYPE_EMPTY;
        }
        else if (dataList.size() != 0 && position >= dataList.size())
        {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.addressDetail) TextView addressDetail;
        @BindView(R.id.phone) TextView phone;
        @BindView(R.id.cvGroup) CardView cvGroup;
        @BindView(R.id.imgOption) ImageView imgOption;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cvGroup.setOnClickListener(this);
            imgOption.setOnClickListener(this);
        }

        public void binding(UserStore model)
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
                    int id = dataList.get(getAdapterPosition()).getiDUser();
                    Bundle bundle = new Bundle();
                    bundle.putInt(ID_STORE, id);
                    context.startActivity(StoreDetailActivity.class, bundle, false);
                    break;
                }
                case R.id.imgOption:
                {
                    BottomSheetStore bottomSheetStore = new BottomSheetStore();
                    bottomSheetStore.setPosition(getAdapterPosition());
                    bottomSheetStore.setUserStore(dataList.get(getAdapterPosition()));
                    bottomSheetStore.setAdmin(isAdmin);
                    bottomSheetStore.setStorageAdapter(StorageAdapter.this);
                    bottomSheetStore.show(fragmentManager, bottomSheetStore.getTag());
                    break;
                }
            }
        }
    }

    public void addStore(UserStore userStore)
    {
        dataList.add(userStore);
        notifyItemInserted(0);
        notifyDataSetChanged();
    }

    public void updateItem(UserStore userStore, int position)
    {
        dataList.set(position, userStore);
        notifyItemChanged(position);
    }

    public void removeAt(int position)
    {
        dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataList.size());
    }
}
