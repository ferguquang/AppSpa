package com.ngo.ducquang.appspa.notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.EmptyViewHolder;
import com.ngo.ducquang.appspa.base.FooterViewHolder;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.base.font.FontChangeCrawler;
import com.ngo.ducquang.appspa.notification.model.Notification;
import com.ngo.ducquang.appspa.oder.OrderDetailActivity;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;
import com.ngo.ducquang.appspa.storageList.StoreActivity;
import com.ngo.ducquang.appspa.storageList.model.Category;
import com.ngo.ducquang.appspa.storageList.storeDetail.StoreDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 9/23/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int TYPE_EMPTY = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    private List<Notification> dataList;
    private NotificationActivity notificationActivity;

    public NotificationAdapter(NotificationActivity notificationActivity, List<Notification> dataList) {
        this.dataList = dataList;
        this.notificationActivity = notificationActivity;
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
            FontChangeCrawler fontChanger = new FontChangeCrawler(notificationActivity.getAssets(), GlobalVariables.FONT_BASE);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof EmptyViewHolder)
        {
            ((EmptyViewHolder) holder).setTextEmpty("Không có thông báo nào");
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
        @BindView(R.id.categories) TextView categoriesText;
        @BindView(R.id.dateTime) TextView dateTime;
        @BindView(R.id.phone) TextView phone;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.dateTimePromotion) TextView dateTimePromotion;

        @BindView(R.id.cvGroup) CardView cvGroup;
        @BindView(R.id.cardViewStatus) CardView cardViewStatus;
        @BindView(R.id.llAddress) LinearLayout llAddress;
        @BindView(R.id.llDateTimePromotion) LinearLayout llDateTimePromotion;
        @BindView(R.id.llDateCreate) LinearLayout llDateCreate;
        @BindView(R.id.llPhone) LinearLayout llPhone;
        @BindView(R.id.imgOption) ImageView imgOption;

        public ItemHolder(View itemView)
        {
            super(itemView);

            ButterKnife.bind(this, itemView);

            cvGroup.setOnClickListener(v ->
            {
                dataList.get(getAdapterPosition()).setView(true);
                notifyItemChanged(getAdapterPosition());
                int idOrder = dataList.get(getAdapterPosition()).getiDOrder();
                int idStore = dataList.get(getAdapterPosition()).getiDStore();

                Bundle bundle = new Bundle();
                if (idOrder != 0)
                {
                    bundle.putInt(OrderDetailActivity.ID_NOTI, dataList.get(getAdapterPosition()).getId());
                    bundle.putInt(OrderDetailActivity.ID_ORDER, idOrder);
                    bundle.putBoolean(OrderDetailActivity.FROM_NOTIFICATION_ACTIVITY, true);
                    notificationActivity.startActivity(OrderDetailActivity.class, bundle, false);
                }
                else if (idStore != 0)
                {
                    bundle.putInt(StorageAdapter.ID_STORE, idStore);
                    notificationActivity.startActivity(StoreDetailActivity.class, bundle, false);
                }
                else
                {
                    notificationActivity.startActivity(StoreActivity.class, null, false);
                }
            });
        }

        public void binding(Notification model)
        {
            cardViewStatus.setVisibility(View.GONE);
            llAddress.setVisibility(View.GONE);
            imgOption.setVisibility(View.GONE);
            llPhone.setVisibility(View.GONE);

            name.setText(model.getTitle());
            dateTime.setText(ManagerTime.convertToMonthDayYearHourMinuteFormat(model.getCreated()));
            List<String> categoriesString = new ArrayList<>();
            List<Category> categories = model.getCategories();
            for (int i = 0; i < categories.size(); i++)
            {
                categoriesString.add(categories.get(i).getName());
            }
            String category = TextUtils.join(", ", categoriesString);
            categoriesText.setText(category);

            if (model.isView())
                cvGroup.setCardBackgroundColor(name.getContext().getResources().getColor(R.color.white));
            else
                cvGroup.setCardBackgroundColor(name.getContext().getResources().getColor(R.color.noread));

            if (model.getiDOrder() == 0)
            {
                llDateCreate.setVisibility(View.GONE);
                llDateTimePromotion.setVisibility(View.VISIBLE);
                String startDate = ManagerTime.convertToMonthDayYearHourMinuteFormat(model.getStartDate());
                String endDate = ManagerTime.convertToMonthDayYearHourMinuteFormat(model.getEndDate());
                dateTimePromotion.setText("Từ " + startDate + " đến " + endDate);
                categoriesText.setText(model.getDescribe());
            }
            else
            {
                llDateTimePromotion.setVisibility(View.GONE);
                llDateCreate.setVisibility(View.VISIBLE);
            }
        }
    }

    public void addDataNotification(List<Notification> data)
    {
        dataList.addAll(data);
        notifyDataSetChanged();
    }
}
