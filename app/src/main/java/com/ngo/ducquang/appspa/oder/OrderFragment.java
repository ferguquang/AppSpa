package com.ngo.ducquang.appspa.oder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.Share;
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.view.popupWindow.ItemPopupMenu;
import com.ngo.ducquang.appspa.base.view.popupWindow.ListPopupWindowAdapter;
import com.ngo.ducquang.appspa.modelStore.ResponseGetStore;
import com.ngo.ducquang.appspa.modelStore.Store;
import com.ngo.ducquang.appspa.oder.model.Order;
import com.ngo.ducquang.appspa.oder.model.ResponseOrder;
import com.ngo.ducquang.appspa.service.ServiceAdminAdapter;
import com.ngo.ducquang.appspa.service.model.ResponseServiceAdmin;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/21/2018.
 */

public class OrderFragment extends BaseFragment implements View.OnClickListener, CategoryOptionAdapter.SendListCheckedCheckBox
{
    public static final String IDSTORE = "idstore";
    public static final String TYPE = "type";
    public static final String WHERE = "where";
    public static final String NAME_STORE = "nameStore";

    public static final int ORDER_NORMAL = 1;
    public static final int ORDER_AT_HOME = 2;

    private int type;
    private boolean isInDetail = false;
    private String nameStoreInDetail;

    @BindView(R.id.book) Button book;
    @BindView(R.id.toolBar) Toolbar toolBar;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.llStartDate) LinearLayout llStartDate;
    @BindView(R.id.startDate) TextView startDate;
    @BindView(R.id.noteEdt) EditText noteEdt;
    @BindView(R.id.nameEdt) EditText nameEdt;

    @BindView(R.id.nameStore) TextView nameStore;
    @BindView(R.id.storeLayout) LinearLayout storeLayout;

    private CategoryOptionAdapter adapter;

    private String token = "";

    private int idStore;
    private String idCategories = "";

    private GridLayoutManager gridLayoutManager;

    private List<Category> categories = new ArrayList<>();
    private List<Store> stores = new ArrayList<>();

    private ListPopupWindow listPopupWindow;
    private List<ItemPopupMenu> listItemStore = new ArrayList<>();
    private ListAdapter adapterStoreMenu;

    @Override
    protected int getContentView() {
        return R.layout.fragment_book_calendar_spa;
    }

    @Override
    protected void initView(View view)
    {
        showBackPressToolBar();
        title.setText("Đặt lịch");

        book.setOnClickListener(this);
        llStartDate.setOnClickListener(this);
        nameStore.setOnClickListener(this);

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            type = bundle.getInt(TYPE);
            isInDetail = bundle.getBoolean(WHERE, false);
            nameStoreInDetail = bundle.getString(NAME_STORE, "Chọn cửa hàng");
            idStore = bundle.getInt(IDSTORE, 0);

            nameStore.setText(nameStoreInDetail);

            if (type == ORDER_NORMAL)
            {

            }
            else if (type == ORDER_AT_HOME)
            {
                storeLayout.setVisibility(View.GONE);
            }

            if (isInDetail)
            {
                nameStore.setOnClickListener(null);
            }
        }

        token = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, "");

        showLoadingDialog();
        ApiService.Factory.getInstance().getStore(token, idCategories).enqueue(callbackGetStore());
        showLoadingDialog();
        ApiService.Factory.getInstance().getListServiceAdmin(token, idStore).enqueue(callbackGetService());
    }

    private Callback<ResponseServiceAdmin> callbackGetService()
    {
        return new Callback<ResponseServiceAdmin>()
        {
            @Override
            public void onResponse(Call<ResponseServiceAdmin> call, Response<ResponseServiceAdmin> response)
            {
                if (response.body().getStatus() == 1)
                {
                    categories.clear();
                    categories = response.body().getData().getCategories();

                    if (adapter == null)
                    {
                        gridLayoutManager = new GridLayoutManager(getContext(), 3);
                        adapter = new CategoryOptionAdapter(getActivity(), categories, OrderFragment.this);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setNestedScrollingEnabled(false);
                    }
                    else
                    {
                        adapter.refreshData(categories);
                    }
                }

                hideLoadingDialog();
            }

            @Override
            public void onFailure(Call<ResponseServiceAdmin> call, Throwable t) {
                LogManager.tagDefault().error(t.getMessage());
                hideLoadingDialog();
            }
        };
    }

    private Callback<ResponseGetStore> callbackGetStore()
    {
        return new Callback<ResponseGetStore>()
        {
            @Override
            public void onResponse(Call<ResponseGetStore> call, Response<ResponseGetStore> response)
            {
                if (response.body().getStatus() == 1)
                {
                    stores.clear();
                    stores = response.body().getData().getStores();
                    hideLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetStore> call, Throwable t) {
                LogManager.tagDefault().error(t.getMessage());
            }
        };
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.book:
            {
                String name = nameEdt.getText().toString();
                String date = startDate.getText().toString();
                String describe = noteEdt.getText().toString();

                if (StringUtilities.isEmpty(date))
                {

                }

                List<String> listIDCategory = new ArrayList<>();
                for (int i = 0; i < categories.size(); i++)
                {
                    RecyclerView.ViewHolder viewMain = recyclerView.findViewHolderForAdapterPosition(i);
                    CheckBox checkBox = viewMain.itemView.findViewById(R.id.checkbox);
                    if (checkBox.isChecked())
                    {
                        Category category = categories.get(i);
                        listIDCategory.add(category.getiD() + "");
                    }
                }

                String iDCategory = TextUtils.join(",", listIDCategory);

                HashMap<String, String> params = new HashMap<>();
                params.put("Token", token);
                if (type == ORDER_NORMAL)
                {
                    params.put("IDStore", idStore + "");
                }
                params.put("Type", type + "");
                params.put("Name", name);
                params.put("Describe", describe);
                params.put("OnDate", date);
                params.put("IDCategory", iDCategory);

                ApiService.Factory.getInstance().orderCreate(params).enqueue(new Callback<ResponseOrder>()
                {
                    @Override
                    public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response)
                    {
                        Message message = response.body().getMessages().get(0);
                        if (response.body().getStatus() == 1)
                        {
                            showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);
                            Order order = response.body().getData().getOrder();

                            Bundle bundle = new Bundle();
                            bundle.putString(OrderDetailActivity.ORDER_MODEL, order.toJson());
                            startActivity(OrderDetailActivity.class, bundle, false);
                        }
                        else
                        {
                            showToast(message.getText(), GlobalVariables.TOAST_ERRO);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseOrder> call, Throwable t) {

                    }
                });
                break;
            }
            case R.id.llStartDate:
            {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                new SlideDateTimePicker.Builder(getFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {
                                startDate.setText(format.format(date));
                            }
                        })
                        .setInitialDate(new Date())
                        .setIs24HourTime(true)
                        .setIndicatorColor(Color.parseColor(GlobalVariables.COLOR_PRIMARY))
                        .build()
                        .show();
                break;
            }
            case R.id.nameStore:
            {
                listItemStore.clear();

                listPopupWindow = new ListPopupWindow(getContext());
                for (int i = 0; i < stores.size(); i++)
                {
                    int id = stores.get(i).getiD();
                    String name = stores.get(i).getName();
                    listItemStore.add(new ItemPopupMenu(id, name));
                }

                adapterStoreMenu = new ListPopupWindowAdapter(getContext(), listItemStore);
                listPopupWindow.setAnchorView(nameStore);
                listPopupWindow.setAdapter(adapterStoreMenu);

                listPopupWindow.setOnItemClickListener((parent, view, position, id) ->
                {
                    idStore = listItemStore.get(position).getId();
                    nameStore.setText(listItemStore.get(position).getTitle());

                    showLoadingDialog();
                    ApiService.Factory.getInstance().getListServiceAdmin(token, idStore).enqueue(callbackGetService());
                    listPopupWindow.dismiss();
                });

                listPopupWindow.show();
                break;
            }
        }
    }

    @Override
    public void sendList()
    {
        List<String> listIDCategory = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++)
        {
            RecyclerView.ViewHolder viewMain = recyclerView.findViewHolderForAdapterPosition(i);
            CheckBox checkBox = viewMain.itemView.findViewById(R.id.checkbox);
            if (checkBox.isChecked())
            {
                Category category = categories.get(i);
                listIDCategory.add(category.getiD() + "");
            }
        }

        idCategories = TextUtils.join(",", listIDCategory);

        showLoadingDialog();
        ApiService.Factory.getInstance().getStore(token, idCategories).enqueue(callbackGetStore());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (listPopupWindow != null)
        {
            listPopupWindow.dismiss();
        }
    }
}
