package com.ngo.ducquang.appspa.oder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 9/22/2018.
 */

public class CategoryOptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private List<Category> dataList;
    private SendListCheckedCheckBox sendListCheckedCheckBox;

    public CategoryOptionAdapter(Context context, List<Category> dataList, SendListCheckedCheckBox sendListCheckedCheckBox) {
        this.context = context;
        this.dataList = dataList;
        this.sendListCheckedCheckBox = sendListCheckedCheckBox;
    }

    public CategoryOptionAdapter(Context context, List<Category> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_book_option, parent, false);
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
        @BindView(R.id.checkbox) CheckBox checkBox;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sendListCheckedCheckBox.sendList();
                }
            });
        }

        public void binding(Category model)
        {
            checkBox.setText(model.getName());
            checkBox.setChecked(model.isChecked());
        }
    }

    public void refreshData(List<Category> dataList)
    {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public interface SendListCheckedCheckBox
    {
        void sendList();
    }
}