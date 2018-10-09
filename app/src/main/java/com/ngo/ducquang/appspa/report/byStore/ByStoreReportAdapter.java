package com.ngo.ducquang.appspa.report.byStore;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.EmptyViewHolder;
import com.ngo.ducquang.appspa.base.FooterViewHolder;
import com.ngo.ducquang.appspa.report.model.DataReportByStore;
import com.ngo.ducquang.appspa.report.model.ReportByStore;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 10/9/2018.
 */

public class ByStoreReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int TYPE_EMPTY = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    private Context context;
    private DataReportByStore dataReportByStore;

    public ByStoreReportAdapter(Context context, DataReportByStore dataReportByStore) {
        this.dataReportByStore = dataReportByStore;
        this.context = context;
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
            view = inflater.inflate(R.layout.item_report_by_store, parent, false);
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
        if (holder instanceof ItemHolder)
        {
            ((ItemHolder) holder).binding(dataReportByStore.getReportByStores().get(position));
        }

        if (holder instanceof EmptyViewHolder)
        {
            ((EmptyViewHolder) holder).setTextEmpty("Chưa có dữ liệu thống kê");
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if (dataReportByStore.getReportByStores().size() == 0)
        {
            return TYPE_EMPTY;
        }
        else if (dataReportByStore.getReportByStores().size() != 0 && position >= dataReportByStore.getReportByStores().size())
        {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return dataReportByStore.getReportByStores().size() == 0 ? 1 : dataReportByStore.getReportByStores().size() + 1;
    }

    public class ItemHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.totalUser) TextView totalUser;
        @BindView(R.id.totalDone) TextView totalDone;
        @BindView(R.id.totalInProcess) TextView totalInProcess;
        @BindView(R.id.totalNotDone) TextView totalNotDone;
        @BindView(R.id.totalOrder) TextView totalOrder;
        @BindView(R.id.itemReportStore) LinearLayout itemReportStore;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void binding(ReportByStore model)
        {
            name.setText(model.getName());
            totalUser.setText(model.getTotalUser() + "");
            totalDone.setText(model.getTotalDone() + "");
            totalInProcess.setText(model.getTotalInProcess() + "");
            totalNotDone.setText(model.getTotalNotDone() + "");
            totalOrder.setText(model.getTotalOrder() + "");

            if (getAdapterPosition() % 2 == 0)
                itemReportStore.setBackgroundColor(context.getResources().getColor(R.color.colorBackground));
            else
                itemReportStore.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    public void updateData(DataReportByStore data)
    {
        this.dataReportByStore = data;
        notifyDataSetChanged();
    }
}
