package com.ngo.ducquang.appspa.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.EmptyViewHolder;
import com.ngo.ducquang.appspa.base.FooterViewHolder;
import com.ngo.ducquang.appspa.storageList.model.Category;
import com.ngo.ducquang.appspa.storageList.model.UserStore;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 9/30/2018.
 */

public class ServiceAdminAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public int TYPE_EMPTY = 0;
    public int TYPE_ITEM = 1;
    public int TYPE_FOOTER = 2;

    private Context context;
    private List<Category> dataList;
    private FragmentManager fragmentManager;
    private boolean inDetail = false;

    public ServiceAdminAdapter(Context context, List<Category> dataList, FragmentManager fragmentManager, boolean inDetail) {
        this.context = context;
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;
        this.inDetail = inDetail;
    }

    public ServiceAdminAdapter(Context context, List<Category> dataList, FragmentManager fragmentManager) {
        this.context = context;
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;
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
            view = inflater.inflate(R.layout.item_service_admin, parent, false);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder)
        {
            ((EmptyViewHolder) holder).setTextEmpty("Không có dịch vụ nào");
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

    public class ItemHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.describe) TextView describe;
        @BindView(R.id.imgOption) ImageView imgOption;

        public ItemHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            imgOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomSheetServiceFragment bottomSheetServiceFragment = new BottomSheetServiceFragment();
                    bottomSheetServiceFragment.setAdapter(ServiceAdminAdapter.this);
                    bottomSheetServiceFragment.setCategory(dataList.get(getAdapterPosition()));
                    bottomSheetServiceFragment.setPosotion(getAdapterPosition());
                    bottomSheetServiceFragment.show(fragmentManager, bottomSheetServiceFragment.getTag());
                }
            });
        }

        public void binding(Category model)
        {
            name.setText(model.getName());
            describe.setText(model.getDescribe());
            if (inDetail)
            {
                imgOption.setVisibility(View.GONE);
            }
        }
    }

    public void addStore(Category category)
    {
        dataList.add(category);
        notifyItemInserted(0);
        notifyDataSetChanged();
    }

    public void updateItem(Category category, int position)
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
