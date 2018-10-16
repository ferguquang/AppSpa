package com.ngo.ducquang.appspa.report.byAddress;

import android.content.Context;
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
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.font.FontChangeCrawler;
import com.ngo.ducquang.appspa.report.byStore.ByStoreReportAdapter;
import com.ngo.ducquang.appspa.report.model.DataReportByAddress;
import com.ngo.ducquang.appspa.report.model.ReportByAddress;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 10/9/2018.
 */

public class ByAddressReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int TYPE_EMPTY = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    private Context context;
    private DataReportByAddress dataReportByAddress;

    public ByAddressReportAdapter(Context context, DataReportByAddress dataReportByAddress) {
        this.context = context;
        this.dataReportByAddress = dataReportByAddress;
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
            view = inflater.inflate(R.layout.item_report_by_address, parent, false);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof ItemHolder)
        {
            ((ItemHolder) holder).binding(dataReportByAddress.getReportByAddress().get(position));
        }

        if (holder instanceof EmptyViewHolder)
        {
            ((EmptyViewHolder) holder).setTextEmpty("Chưa có dữ liệu thống kê");
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if (dataReportByAddress.getReportByAddress().size() == 0)
        {
            return TYPE_EMPTY;
        }
        else if (dataReportByAddress.getReportByAddress().size() != 0 && position >= dataReportByAddress.getReportByAddress().size())
        {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return dataReportByAddress.getReportByAddress().size() == 0 ? 1 : dataReportByAddress.getReportByAddress().size() + 1;
    }

    public class ItemHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.totalStore) TextView totalStore;
        @BindView(R.id.totalUser) TextView totalUser;
        @BindView(R.id.total) TextView total;
        @BindView(R.id.itemReportStore) LinearLayout itemReportStore;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void binding(ReportByAddress model)
        {
            name.setText(model.getName());
            totalStore.setText(model.getTotalStore() + "");
            totalUser.setText(model.getTotalUser() + "");
            total.setText(model.getTotal() + "");

            if (getAdapterPosition() % 2 == 0)
                itemReportStore.setBackgroundColor(context.getResources().getColor(R.color.colorBackground));
            else
                itemReportStore.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }
}
