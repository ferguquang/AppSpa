package com.ngo.ducquang.appspa.oder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.EmptyViewHolder;
import com.ngo.ducquang.appspa.base.FooterViewHolder;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.oder.model.Order;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 10/4/2018.
 */

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int TYPE_EMPTY = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    private Context context;
    private List<Order> dataList;

    public OrderListAdapter(Context context, List<Order> dataList) {
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
            view = inflater.inflate(R.layout.item_notification, parent, false);
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
            ((EmptyViewHolder) holder).setTextEmpty("Không có lịch đặt nào");
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
        @BindView(R.id.categories) TextView categories;
        @BindView(R.id.dateTime) TextView dateTime;
        @BindView(R.id.phone) TextView phone;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.cvGroup) CardView cvGroup;

        public ItemHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void binding(Order model)
        {
            name.setText(model.getUser().getName());
            phone.setText(model.getUser().getPhone());
            address.setText(model.getUser().getAddress());
            dateTime.setText(ManagerTime.convertToMonthDayYearHourMinuteFormat(model.getCreated()));

            List<Category> categoryList = model.getCategories();
            List<String> listIDCategory = new ArrayList<>();
            for (int i = 0; i < categoryList.size(); i++)
            {
                Category category = categoryList.get(i);
                listIDCategory.add(category.getName() + "");
            }

            categories.setText(TextUtils.join(",", listIDCategory));
        }
    }

    public void updateData(List<Order> dataList)
    {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
