package com.ngo.ducquang.appspa.base.view.spinner;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.Manager;

import java.util.List;

/**
 * Created by ducqu on 9/23/2018.
 */

public class AdapterSpinner extends ArrayAdapter<SpinnerModel>
{
    private Activity context;
    private int resource;

    @NonNull
    private List<SpinnerModel> objects;
    private int layout = R.layout.item_dialog_spinner;

    public AdapterSpinner(@NonNull Activity context, int resource, @NonNull List<SpinnerModel> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(layout,null);

        TextView txtName = row.findViewById(R.id.txt_item_dialog_name);
        ImageView img = row.findViewById(R.id.img);

        row.setPadding(18, 8, 14, 8);
        img.setVisibility(View.VISIBLE);
        FrameLayout diviner = row.findViewById(R.id.diviner);
        diviner.setVisibility(View.GONE);
        SpinnerModel modelIdName = this.objects.get(position);
        txtName.setText(modelIdName.getName());

        return row;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(layout,null);

        TextView txtName = row.findViewById(R.id.txt_item_dialog_name);
        LinearLayout llBackgroundSpinner = row.findViewById(R.id.llBackgroundSpinner);
        ImageView img = row.findViewById(R.id.img);

        img.setVisibility(View.GONE);
        llBackgroundSpinner.setBackground(null);
        SpinnerModel modelIdName = this.objects.get(position);
        txtName.setText(modelIdName.getName());

        Manager.setPaddingView(context, txtName, 16, 8, 16, 8);
        return row;
    }
}
