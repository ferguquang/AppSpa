package com.ngo.ducquang.appspa.userList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.EmptyViewHolder;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.font.FontChangeCrawler;
import com.ngo.ducquang.appspa.base.view.TextViewFont;
import com.ngo.ducquang.appspa.listDauTu.BottomSheetNewUserFragment;
import com.ngo.ducquang.appspa.listVanHanh.VanHanhActivity;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;
import com.ngo.ducquang.appspa.storageList.StoreActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 9/28/2018.
 */

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int TYPE_DAU_TU = 9;
    public static final int TYPE_VAN_HANH = 10;

    public static final int FROM_ADMIN = 28;
    public static final int FROM_DAU_TU = 31;
    public static final int FROM_VAN_HANH = 94;

    public static final String ID_USER = "iduser";

    public int TYPE_EMPTY = 0;
    public int TYPE_ITEM = 1;

    private Context context;
    private FragmentManager fragmentManager;
    private List<UserApp> dataList;
    private boolean isNew = false;
    private int type;
    private int from;

    public UserListAdapter(Context context, FragmentManager fragmentManager, List<UserApp> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
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
            FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), GlobalVariables.FONT_BASE);
            fontChanger.replaceFonts((ViewGroup) view);
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
        if (dataList.size() == 0)
        {
            return TYPE_EMPTY;
        }
        return TYPE_ITEM;
    }


    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.name) TextViewFont name;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.addressDetail) TextView addressDetail;
        @BindView(R.id.phone) TextView phone;
        @BindView(R.id.cvGroup) CardView cvGroup;
        @BindView(R.id.llDiaChi) LinearLayout llDiaChi;
        @BindView(R.id.imgOption) ImageView imgOption;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cvGroup.setOnClickListener(this);
            imgOption.setOnClickListener(this);
        }

        public void binding(UserApp model)
        {
            name.setText(model.getName());
            address.setText(model.getDistrictName() + " - " + model.getProvinceName());
            phone.setText(model.getPhone());
            addressDetail.setText(model.getAddress());

            name.setTextBold();
            if (isNew)
            {
                llDiaChi.setVisibility(View.GONE);
                imgOption.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.cvGroup:
                {
                    switch (from)
                    {
                        case FROM_ADMIN:
                        {
                            int idUser = dataList.get(getAdapterPosition()).getiDUser();
                            Intent intent = new Intent(context, VanHanhActivity.class);
                            intent.putExtra(VanHanhActivity.IDINVEST, idUser);
                            context.startActivity(intent);
                            break;
                        }
                        case FROM_DAU_TU:
                        {
                            Intent intent = new Intent(context, StoreActivity.class);
                            context.startActivity(intent);
                            break;
                        }
                        case FROM_VAN_HANH:
                        {

                            break;
                        }
                        default:
                        {
                            Bundle bundle = new Bundle();
                            bundle.putInt(ID_USER, dataList.get(getAdapterPosition()).getiDUser());
                            UserDetailFragment userDetailFragment = new UserDetailFragment();
                            userDetailFragment.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_right);
                            fragmentTransaction.add(android.R.id.content, userDetailFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        }
                    }

                    break;
                }
                case R.id.imgOption:
                {
                    BottomSheetNewUserFragment bottomSheetNewUserFragment = new BottomSheetNewUserFragment();
                    bottomSheetNewUserFragment.setAdapter(UserListAdapter.this);
                    bottomSheetNewUserFragment.setUserApp(dataList.get(getAdapterPosition()));
                    bottomSheetNewUserFragment.setPosotion(getAdapterPosition());
                    bottomSheetNewUserFragment.setType(type);
                    bottomSheetNewUserFragment.show(fragmentManager, bottomSheetNewUserFragment.getTag());

                    break;
                }
            }
        }
    }

    public void updateData(List<UserApp> list)
    {
        this.dataList = list;
        notifyDataSetChanged();
    }

    public void addItem(UserApp category)
    {
        dataList.add(category);
        notifyItemInserted(0);
        notifyDataSetChanged();
    }

    public void updateItem(UserApp category, int position)
    {
        dataList.set(position, category);
        notifyItemChanged(position);
    }

    public void removeAt(int position)
    {
        dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataList.size());
    }
}
