package com.ngo.ducquang.appspa.report.byStore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.EmptyViewHolder;
import com.ngo.ducquang.appspa.modelStore.Store;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 10/9/2018.
 */

public class DialogFilterStoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private List<Store> dataList = new ArrayList<>();
    private SendDataCurrent sendDataCurrent;

    private List<Store> dataCurrent = new ArrayList<>();

    public DialogFilterStoreAdapter(Context context, List<Store> dataList, SendDataCurrent sendDataCurrent) {
        this.context = context;
        this.dataList = dataList;
        this.sendDataCurrent = sendDataCurrent;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.item_filter_store, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
        @BindView(R.id.reMain) RelativeLayout reMain;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.selected) ImageView selected;

        public ItemHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

            reMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int position = getAdapterPosition();
                    if (dataList.get(position).isSelected())
                    {
                        selected.setImageResource(R.drawable.disable);
                        dataList.get(position).setSelected(false);
                        dataCurrent.remove(dataList.get(getAdapterPosition()));
                    }
                    else
                    {
                        selected.setImageResource(R.drawable.selected);
                        dataList.get(position).setSelected(true);
                        dataCurrent.add(dataList.get(getAdapterPosition()));
                    }

                    sendDataCurrent.sendList(dataCurrent);
                }
            });
        }

        public void binding(Store model)
        {
            name.setText(model.getName());
            if (model.isSelected())
                selected.setImageResource(R.drawable.selected);
            else
                selected.setImageResource(R.drawable.disable);
        }
    }

    public interface SendDataCurrent
    {
        void sendList(List<Store> dataCurrent);
    }
}
