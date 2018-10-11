package com.ngo.ducquang.appspa.oder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
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
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.base.Message;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.view.popupWindow.ItemPopupMenu;
import com.ngo.ducquang.appspa.base.view.popupWindow.ListPopupWindowAdapter;
import com.ngo.ducquang.appspa.modelStore.ResponseGetStore;
import com.ngo.ducquang.appspa.modelStore.Store;
import com.ngo.ducquang.appspa.oder.model.Order;
import com.ngo.ducquang.appspa.oder.model.ResponseOrder;
import com.ngo.ducquang.appspa.service.model.ResponseServiceAdmin;
import com.ngo.ducquang.appspa.storageList.model.Category;
import com.ngo.ducquang.appspa.storageList.model.UserStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
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
    public static final String ORDER_MODEL_STRING = "ordermodelstring";
    public static final String UPDATE = "update";

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

    @BindView(R.id.nameStore) TextView nameStore;
    @BindView(R.id.layoutStore) LinearLayout layoutStore;

    private CategoryOptionAdapter adapter;

    private String token = "";

    private int idStore;
    private String idCategories = "";

    private boolean isUpdate = false;
    private String orderModelString = "";
    private Order order;

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
            orderModelString = bundle.getString(ORDER_MODEL_STRING, "");
            isUpdate = bundle.getBoolean(UPDATE, false);

            nameStore.setText(nameStoreInDetail);

            if (type == ORDER_NORMAL)
            {

            }
            else if (type == ORDER_AT_HOME)
            {
                layoutStore.setVisibility(View.GONE);
                title.setText("Đặt lịch tại nhà");
            }

            if (isInDetail)
            {
                nameStore.setOnClickListener(null);
            }

            if (!StringUtilities.isEmpty(orderModelString))
            {
                order = Order.initialize(orderModelString);
            }
        }

        token = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, "");

        showLoadingDialog();
        ApiService.Factory.getInstance().getStore(token, idCategories).enqueue(callbackGetStore());
        showLoadingDialog();
        if (order != null)
        {
            idStore = order.getStoreID();
        }
        ApiService.Factory.getInstance().getListServiceAdmin(token, idStore).enqueue(callbackGetService());

        if (isUpdate)
        {
            nameStore.setText(order.getStoreName());
            startDate.setText(ManagerTime.convertToMonthDayYearHourMinuteFormat(order.getOnDate()));
            noteEdt.setText(order.getDescribe());

            title.setText("Chỉnh sửa lịch đặt");
        }
    }

    private Callback<ResponseServiceAdmin> callbackGetService()
    {
        return new Callback<ResponseServiceAdmin>()
        {
            @Override
            public void onResponse(@NonNull Call<ResponseServiceAdmin> call, @NonNull Response<ResponseServiceAdmin> response)
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

                    // setCheck:
                    if (isUpdate)
                    {
                        for (int i = 0; i < order.getCategories().size(); i++)
                        {
                            Category category = order.getCategories().get(i);
                            if (category.getiD() == order.getCategories().get(i).getiD())
                            {
                                categories.get(i).setChecked(true);
                            }
                        }

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
                    if (stores.size() > 0)
                    {
                        Store userStore = stores.get(0);
                        idStore = userStore.getiD();
                        nameStore.setText(userStore.getName());
                    }
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
                String date = startDate.getText().toString();
                String describe = noteEdt.getText().toString();

                if (StringUtilities.isEmpty(date))
                {
                    showToast("Mời bạn chọn ngày đặt lịch", GlobalVariables.TOAST_ERRO);
                    return;
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

                if (listIDCategory.size() == 0)
                {
                    showToast("Phải chọn ít nhất 1 dịch vụ", GlobalVariables.TOAST_ERRO);
                    return;
                }

                String iDCategory = TextUtils.join(",", listIDCategory);

                HashMap<String, String> params = new HashMap<>();
                params.put("Token", token);
                if (type == ORDER_NORMAL)
                {
                    params.put("IDStore", idStore + "");
                }
                params.put("Type", type + "");
                params.put("Describe", describe);
                params.put("OnDate", date);
                params.put("IDCategory", iDCategory);

                showLoadingDialog();

                book.setEnabled(false);

                if (isUpdate)
                {
                    params.put("ID", order.getiD() + "");
                    ApiService.Factory.getInstance().orderUpdate(params).enqueue(new Callback<ResponseOrder>()
                    {
                        @Override
                        public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response)
                        {
                            Message message = response.body().getMessages().get(0);
                            if (response.body().getStatus() == 1)
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);

                                getActivity().onBackPressed();
                                Order order = response.body().getData().getOrder();
                                Bundle bundle = new Bundle();
                                bundle.putString(OrderDetailActivity.ORDER_MODEL, order.toJson());
                                startActivity(OrderDetailActivity.class, bundle, false);
                            }
                            else
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_ERRO);
                                book.setEnabled(true);
                            }

                            hideLoadingDialog();
                        }

                        @Override
                        public void onFailure(Call<ResponseOrder> call, Throwable t) {
                            book.setEnabled(true);
                            hideLoadingDialog();
                        }
                    });
                }
                else
                {
                    ApiService.Factory.getInstance().orderCreate(params).enqueue(new Callback<ResponseOrder>()
                    {
                        @Override
                        public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response)
                        {
                            Message message = response.body().getMessages().get(0);
                            if (response.body().getStatus() == 1)
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_SUCCESS);

                                getActivity().onBackPressed();
                                Order order = response.body().getData().getOrder();
                                if (order != null)
                                {
                                    Bundle bundle = new Bundle();

                                    bundle.putString(OrderDetailActivity.ORDER_MODEL, order.toJson());
                                    startActivity(OrderDetailActivity.class, bundle, false);
                                }
                            }
                            else
                            {
                                showToast(message.getText(), GlobalVariables.TOAST_ERRO);
                                book.setEnabled(true);
                            }

                            hideLoadingDialog();
                        }

                        @Override
                        public void onFailure(Call<ResponseOrder> call, Throwable t) {
                            book.setEnabled(true);
                            hideLoadingDialog();
                        }
                    });
                }

                break;
            }
            case R.id.llStartDate:
            {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
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
            if (viewMain == null)
            {
                return;
            }
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
    public void onStop()
    {
        super.onStop();
        if (listPopupWindow != null)
        {
            listPopupWindow.dismiss();
        }
    }
}
