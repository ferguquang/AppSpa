package com.ngo.ducquang.appspa.base.view.popupWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.font.FontChangeCrawler;

import java.util.List;

/**
 * Created by ducqu on 9/28/2018.
 */

public class ListPopupWindowAdapter  extends BaseAdapter
{
    LayoutInflater mLayoutInflater;
    List<ItemPopupMenu> mItemPopupMenuList;

    public ListPopupWindowAdapter(Context context, List<ItemPopupMenu> itemPopupMenuList)
    {
        mLayoutInflater = LayoutInflater.from(context);
        mItemPopupMenuList = itemPopupMenuList;
    }

    @Override
    public int getCount()
    {
        return mItemPopupMenuList.size();
    }

    @Override
    public ItemPopupMenu getItem(int i)
    {
        return mItemPopupMenuList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.item_popup_window, null);

            FontChangeCrawler fontChanger = new FontChangeCrawler(mLayoutInflater.getContext().getAssets(), GlobalVariables.FONT_BASE);
            fontChanger.replaceFonts((ViewGroup)convertView);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(getItem(position).getTitle());

        return convertView;
    }

    static class ViewHolder
    {
        TextView tvTitle;

        ViewHolder(View view)
        {
            tvTitle = view.findViewById(R.id.text);
        }
    }

    public void updateData(List<ItemPopupMenu> mItemPopupMenuList)
    {
        this.mItemPopupMenuList = mItemPopupMenuList;
        notifyDataSetChanged();
    }
}
