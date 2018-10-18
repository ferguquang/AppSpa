package com.ngo.ducquang.appspa.oder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.font.FontChangeCrawler;
import com.ngo.ducquang.appspa.modelStore.StoreOrder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 10/18/2018.
 */

public class ShowStoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private List<StoreOrder> dataList;
    private SendIDStore sendIDStore;


    public ShowStoreAdapter(Context context, List<StoreOrder> dataList, SendIDStore sendIDStore) {
        this.context = context;
        this.dataList = dataList;
        this.sendIDStore = sendIDStore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_show_store, parent, false);
        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), GlobalVariables.FONT_BASE);
        fontChanger.replaceFonts((ViewGroup) view);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof ItemHolder)
        {
            ((ItemHolder) holder).binding(dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.line) TextView line;
        @BindView(R.id.llStoreItem) LinearLayout llStoreItem;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v ->
            {
                int position = getAdapterPosition();
                for (int i = 0; i < dataList.size(); i++)
                {
                    if (i == position)
                    {
                        dataList.get(position).setSelected(true);
                    }
                    else
                    {
                        dataList.get(i).setSelected(false);
                    }
                }

                notifyDataSetChanged();
                sendIDStore.sendIDStore(dataList.get(position));
            });
        }

        @SuppressLint("SetTextI18n")
        public void binding(StoreOrder model)
        {
            name.setText(model.getNameStore());
            address.setText(model.getAddress() + " - " + model.getDistrictName() + " - " + model.getProvinceName());
            line.setText(model.getLine() + " km");

            if (model.isSelected())
                llStoreItem.setBackgroundColor(context.getResources().getColor(R.color.noread));
            else
                llStoreItem.setBackgroundColor(context.getResources().getColor(R.color.colorBackground));
        }
    }

    public interface SendIDStore
    {
        void sendIDStore(StoreOrder storeOrder);
    }
}
