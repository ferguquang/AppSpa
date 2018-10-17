package com.ngo.ducquang.appspa.oder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.EmptyViewHolder;
import com.ngo.ducquang.appspa.base.FooterViewHolder;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.base.font.FontChangeCrawler;
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

    private OrderListActivity context;
    private List<Order> dataList;
    private FragmentManager fragmentManager;

    public OrderListAdapter(OrderListActivity context, List<Order> dataList, FragmentManager fragmentManager) {
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
            view = inflater.inflate(R.layout.item_notification, parent, false);
            FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), GlobalVariables.FONT_BASE);
            fontChanger.replaceFonts((ViewGroup) view);
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

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.categories) TextView categories;
        @BindView(R.id.dateTime) TextView dateTime;
        @BindView(R.id.phone) TextView phone;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.cvGroup) CardView cvGroup;
        @BindView(R.id.cardViewStatus) CardView cardViewStatus;
        @BindView(R.id.status) TextView status;
        @BindView(R.id.imgOption) ImageView imgOption;

        public ItemHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imgOption.setOnClickListener(this);
            cvGroup.setOnClickListener(this);
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

            cardViewStatus.setCardBackgroundColor(Color.parseColor(model.getStatusColor()));
            status.setText(model.getStatusName());

            if (model.getView())
                cvGroup.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            else
                cvGroup.setCardBackgroundColor(context.getResources().getColor(R.color.noread));
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.cvGroup:
                {
                    dataList.get(getAdapterPosition()).setView(true);
                    notifyItemChanged(getAdapterPosition());
                    int idOrder = dataList.get(getAdapterPosition()).getiD();
                    Bundle bundle  = new Bundle();
                    bundle.putInt(OrderDetailActivity.ID_ORDER, idOrder);
                    context.startActivity(OrderDetailActivity.class, bundle, false);
                    break;
                }
                case R.id.imgOption:
                {
                    BottomSheetOrder bottomSheetOrder = new BottomSheetOrder();
                    bottomSheetOrder.setOrder(dataList.get(getAdapterPosition()));
                    bottomSheetOrder.show(fragmentManager, bottomSheetOrder.getTag());
                    break;
                }
            }
        }
    }

    public void updateData(List<Order> dataList)
    {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
