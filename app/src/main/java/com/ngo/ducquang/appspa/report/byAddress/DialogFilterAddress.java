package com.ngo.ducquang.appspa.report.byAddress;

import android.content.Context;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseDialog;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.Share;
import com.ngo.ducquang.appspa.base.getAddress.District;
import com.ngo.ducquang.appspa.base.getAddress.Province;
import com.ngo.ducquang.appspa.base.view.popupWindow.ItemPopupMenu;
import com.ngo.ducquang.appspa.base.view.popupWindow.ListPopupWindowAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ducqu on 10/9/2018.
 */

public class DialogFilterAddress extends BaseDialog implements View.OnClickListener
{
    @BindView(R.id.valueProvince) TextView valueProvince;
    @BindView(R.id.valueDistrict) TextView valueDistrict;
    @BindView(R.id.btnOK) TextView btnOK;

    private int idProvince = -1, idDistrict = -1;
    private String codeProvince = "";

    private List<District> districts = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();

    private ListPopupWindow popupProvince;
    private List<ItemPopupMenu> listMenuProvince = new ArrayList<>();

    private ListPopupWindow popupDistrict;
    private List<ItemPopupMenu> listMenuDistrict = new ArrayList<>();

    private SendIDAddress sendIDAddress;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendIDAddress = (SendIDAddress) context;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_filter_address;
    }

    @Override
    protected void initView(View view) {
        setListPopupWindow();
        valueProvince.setOnClickListener(this);
        valueDistrict.setOnClickListener(this);
        btnOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.valueDistrict:
            {
                popupDistrict.show();
                if (listMenuDistrict.size() == 0)
                {
                    showToast("Chọn Tỉnh/Thành phố trước", GlobalVariables.TOAST_INFO);
                }
                break;
            }
            case R.id.valueProvince:
            {
                popupProvince.show();
                break;
            }
            case R.id.btnOK:
            {
                sendIDAddress.sendAddress(idProvince, idDistrict);
                dismiss();
                break;
            }
        }
    }

    private void setListPopupWindow()
    {
        popupProvince = new ListPopupWindow(getContext());
        for (int i = 0; i < provinces.size(); i++)
        {
            int id = provinces.get(i).getiD();
            String name = provinces.get(i).getName();
            String code = provinces.get(i).getCode();
            listMenuProvince.add(new ItemPopupMenu(id, name, code));
        }

        ListAdapter adapterMenuProvince = new ListPopupWindowAdapter(getContext(), listMenuProvince);
        popupProvince.setAnchorView(valueProvince);
        popupProvince.setAdapter(adapterMenuProvince);
        popupProvince.setOnItemClickListener((parent, view1, position, id) ->
        {
            idProvince = listMenuProvince.get(position).getId();
            valueProvince.setText(listMenuProvince.get(position).getTitle());
            codeProvince = listMenuProvince.get(position).getCode();
            popupProvince.dismiss();

            valueDistrict.setText("Chọn Quận/huyện");
            idDistrict = -1;
            listMenuDistrict.clear();

            for (int i = 0; i < districts.size(); i++)
            {
                int idDistrict = districts.get(i).getiD();
                String name = districts.get(i).getName();
                String code = districts.get(i).getProvinceCode();
                if (codeProvince.equals(code))
                {
                    listMenuDistrict.add(new ItemPopupMenu(idDistrict, name, code));
                }
            }
        });

        popupDistrict = new ListPopupWindow(getContext());
        ListAdapter adapterDistrict = new ListPopupWindowAdapter(getContext(), listMenuDistrict);
        popupDistrict.setAnchorView(valueDistrict);
        popupDistrict.setAdapter(adapterDistrict);
        popupDistrict.setOnItemClickListener((parent, view1, position, id) ->
        {
            idDistrict = listMenuDistrict.get(position).getId();
            valueDistrict.setText(listMenuDistrict.get(position).getTitle());
            popupDistrict.dismiss();
        });
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public interface SendIDAddress
    {
        void sendAddress(int idProvince, int idDistrict);
    }
}
