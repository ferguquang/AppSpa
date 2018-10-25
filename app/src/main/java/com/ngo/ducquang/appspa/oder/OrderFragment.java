package com.ngo.ducquang.appspa.oder;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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
import com.ngo.ducquang.appspa.base.view.MaxHeightRecyclerView;
import com.ngo.ducquang.appspa.base.view.popupWindow.ItemPopupMenu;
import com.ngo.ducquang.appspa.base.view.popupWindow.ListPopupWindowAdapter;
import com.ngo.ducquang.appspa.modelStore.ResponseGetStoreToOrder;
import com.ngo.ducquang.appspa.modelStore.StoreOrder;
import com.ngo.ducquang.appspa.modelStore.StoresToOrrder;
import com.ngo.ducquang.appspa.oder.model.Order;
import com.ngo.ducquang.appspa.oder.model.ResponseOrder;
import com.ngo.ducquang.appspa.service.model.ResponseServiceAdmin;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by ducqu on 9/21/2018.
 */

public class OrderFragment extends BaseFragment implements View.OnClickListener, CategoryOptionAdapter.SendListCheckedCheckBox, ShowStoreAdapter.SendIDStore
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

    @BindView(R.id.layoutAddress) LinearLayout layoutAddress;
    @BindView(R.id.nameAddress) TextView nameAddress;

    @BindView(R.id.llOrderStore) LinearLayout llOrderStore;

    private CategoryOptionAdapter adapter;

    private String token = "";

    private int idStore;
    private String idCategories = "";

    private boolean isUpdate = false;
    private String orderModelString = "";
    private Order order;

    private List<Category> categories = new ArrayList<>();

    private List<StoreOrder> storeOrderList = new ArrayList<>();

    private List<StoresToOrrder> storesToOrrders = new ArrayList<>();
    private ListPopupWindow listPopupAddress;
    private List<ItemPopupMenu> listItemAddress = new ArrayList<>();
    private ListAdapter adapterAddress;

    @BindView(R.id.recyclerViewStore) MaxHeightRecyclerView recyclerViewStore;

    private LocationManager locationManager;
    private double latitude = 0;
    private double longitude = 0;
    final long MIN_TIME_BW_UPDATES = 1000;
    final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    String provider = "";

    @Override
    protected int getContentView() {
        return R.layout.fragment_book_calendar_spa;
    }

    @Override
    protected void initView(View view)
    {
        token = PreferenceUtil.getPreferences(getContext(), PreferenceUtil.TOKEN, "");
        showBackPressToolBar();
        title.setText("Đặt lịch");

        book.setOnClickListener(this);
        llStartDate.setOnClickListener(this);
        nameAddress.setOnClickListener(this);
        layoutAddress.setOnClickListener(this);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        provider = getEnabledLocationProvider();

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            type = bundle.getInt(TYPE);
            isInDetail = bundle.getBoolean(WHERE, false);
            nameStoreInDetail = bundle.getString(NAME_STORE, "Chọn cửa hàng");
            idStore = bundle.getInt(IDSTORE, 0);
            orderModelString = bundle.getString(ORDER_MODEL_STRING, "");
            isUpdate = bundle.getBoolean(UPDATE, false);

            if (type == ORDER_NORMAL)
            {

            } else if (type == ORDER_AT_HOME)
            {
                llOrderStore.setVisibility(View.GONE);
                title.setText("Đặt lịch tại nhà");
                ApiService.Factory.getInstance().getListServiceAdmin(token, 0).enqueue(callbackGetService());
            }

            if (isInDetail)
            {
                ApiService.Factory.getInstance().getStoreToOrder(token, idStore).enqueue(new Callback<ResponseGetStoreToOrder>()
                {
                    @Override
                    public void onResponse(Call<ResponseGetStoreToOrder> call, Response<ResponseGetStoreToOrder> response)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            storesToOrrders = response.body().getData().getStoresToOrrder();
                            hideLoadingDialog();

                            if (storesToOrrders.size() > 0)
                            {
                                nameAddress.setText(storesToOrrders.get(0).getName());

                                storeOrderList = storesToOrrders.get(0).getStores();
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                ShowStoreAdapter showStoreAdapter = new ShowStoreAdapter(getActivity(), storeOrderList, OrderFragment.this);
                                recyclerViewStore.setLayoutManager(linearLayoutManager);
                                recyclerViewStore.setAdapter(showStoreAdapter);
                            }

                            showLoadingDialog();
                            ApiService.Factory.getInstance().getListServiceAdmin(token, idStore).enqueue(callbackGetService());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseGetStoreToOrder> call, Throwable t) {
                        showToast(t.getMessage(), GlobalVariables.TOAST_ERRO);
                    }
                });
            }
            else
            {
                getMyLocation();
            }

            if (!StringUtilities.isEmpty(orderModelString)) {
                order = Order.initialize(orderModelString);
            }
        }

        if (order != null)
        {
            idStore = order.getStoreID();
        }

        if (isUpdate)
        {
            startDate.setText(ManagerTime.convertToMonthDayYearHourMinuteFormat(order.getOnDate()));
            noteEdt.setText(order.getDescribe());

            title.setText("Chỉnh sửa lịch đặt");
        }
    }

    private LocationListener mLocationListener = new LocationListener()
    {
        @Override
        public void onLocationChanged(final Location location) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };

    private Callback<ResponseGetStoreToOrder> callbackGetStoreToOrder()
    {
        return new Callback<ResponseGetStoreToOrder>()
        {
            @Override
            public void onResponse(@NonNull Call<ResponseGetStoreToOrder> call, @NonNull Response<ResponseGetStoreToOrder> response)
            {
                if (response.body().getStatus() == 1)
                {
                    storesToOrrders = response.body().getData().getStoresToOrrder();
                    hideLoadingDialog();

                    if (storesToOrrders.size() > 0)
                    {
                        nameAddress.setText(storesToOrrders.get(0).getName());

                        storeOrderList = storesToOrrders.get(0).getStores();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        ShowStoreAdapter showStoreAdapter = new ShowStoreAdapter(getActivity(), storeOrderList, OrderFragment.this);
                        recyclerViewStore.setLayoutManager(linearLayoutManager);
                        recyclerViewStore.setAdapter(showStoreAdapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGetStoreToOrder> call, @NonNull Throwable t)
            {
                showToast(t.getMessage(), GlobalVariables.TOAST_ERRO);
            }
        };
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
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        adapter = new CategoryOptionAdapter(getActivity(), categories, OrderFragment.this);
                        adapter.setShowTime(true);
                        recyclerView.setLayoutManager(layoutManager);
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
            public void onFailure(@NonNull Call<ResponseServiceAdmin> call, @NonNull Throwable t) {
                LogManager.tagDefault().error(t.getMessage());
                hideLoadingDialog();
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
            case R.id.nameAddress:
            {
                listItemAddress.clear();
                listPopupAddress = new ListPopupWindow(getContext());
                for (int i = 0; i < storesToOrrders.size(); i++)
                {
                    int id = storesToOrrders.get(i).getiD();
                    String name = storesToOrrders.get(i).getName();
                    listItemAddress.add(new ItemPopupMenu(id, name));
                }

                adapterAddress = new ListPopupWindowAdapter(getContext(), listItemAddress);
                listPopupAddress.setAnchorView(nameAddress);
                listPopupAddress.setAdapter(adapterAddress);
                listPopupAddress.setOnItemClickListener((parent, view, position, id) ->
                {
                    showLoadingDialog();
//                    storeOrderList.clear();
                    int idProvince = listItemAddress.get(position).getId();
                    if (idProvince == 1)
                    {
                        storeOrderList = storesToOrrders.get(0).getStores();
                    }
                    else if (idProvince == 2)
                    {
                        storeOrderList = storesToOrrders.get(1).getStores();
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    ShowStoreAdapter showStoreAdapter = new ShowStoreAdapter(getActivity(), storeOrderList, OrderFragment.this);
                    recyclerViewStore.setLayoutManager(linearLayoutManager);
                    recyclerViewStore.setAdapter(showStoreAdapter);

                    hideLoadingDialog();

                    nameAddress.setText(listItemAddress.get(position).getTitle());
                    listPopupAddress.dismiss();
                });

                listPopupAddress.show();
                break;
            }
            case R.id.layoutAddress:
            {
                getMyLocation();
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
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (listPopupAddress != null)
        {
            listPopupAddress.dismiss();
        }
    }

    @Override
    public void sendIDStore(StoreOrder storeOrder) {
        idStore = storeOrder.getiDStore();
        ApiService.Factory.getInstance().getListServiceAdmin(token, idStore).enqueue(callbackGetService());
    }

    private void getMyLocation()
    {
        Location myLocation = null;
        try
        {
            locationManager.requestLocationUpdates(provider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
            myLocation = locationManager.getLastKnownLocation(provider);
        }
        catch (SecurityException e)  // Với Android API >= 23 phải catch SecurityException.
        {
            Log.e("TAG", "Show My Location Error:" + e.getMessage());
            return;
        }

        if (myLocation == null)
        {
            showToast("Yêu cầu bật dịch vụ vị trí trên điện thoại!!!", GlobalVariables.TOAST_INFO);
            return;
        }

        latitude = myLocation.getLatitude();
        longitude = myLocation.getLongitude();

        ApiService.Factory.getInstance().getStoreToOrder(token, latitude, longitude).enqueue(callbackGetStoreToOrder());
        hideLoadingDialog();
    }

    public String getEnabledLocationProvider()
    {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        boolean enabled = locationManager.isProviderEnabled(bestProvider);

        if (!enabled)
        {
            Log.i("TAG", "No location provider enabled!");
            return null;
        }
        return bestProvider;
    }
}
