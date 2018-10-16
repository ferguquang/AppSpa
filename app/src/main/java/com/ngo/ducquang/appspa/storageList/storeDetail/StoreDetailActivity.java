package com.ngo.ducquang.appspa.storageList.storeDetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.view.TabPagerAdapter;
import com.ngo.ducquang.appspa.base.view.TransformerFadeViewPager;
import com.ngo.ducquang.appspa.oder.OrderFragment;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;
import com.ngo.ducquang.appspa.storageList.model.UserStore;
import com.ngo.ducquang.appspa.storageList.storeDetail.categories.CategoriesFragment;
import com.ngo.ducquang.appspa.storageList.storeDetail.comment.CommentFragment;
import com.ngo.ducquang.appspa.storageList.storeDetail.rate.RateFragment;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.DataRate;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.DataStoreDetail;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.ResponseStoreDetail;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/21/2018.
 */

public class StoreDetailActivity extends BaseActivity implements View.OnClickListener, DialogConfirmRate.SendListRating, DialogConfirmRate.SendOldRate
{
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.address) TextView address;
    @BindView(R.id.phone) TextView phone;
    @BindView(R.id.textMyRate) TextView textMyRate;
    @BindView(R.id.textRateAverage) TextView textRateAverage;

    @BindView(R.id.llContent) LinearLayout llContent;
    @BindView(R.id.myRateLayout) LinearLayout myRateLayout;
    @BindView(R.id.cvBook) CardView cvBook;

    @BindView(R.id.bookCalendar) TextView bookCalendar;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.ratingMe) RatingBar ratingMe;

    private TabPagerAdapter adapter;

    private CategoriesFragment categoriesFragment;
    private CommentFragment commentFragment;
    private RateFragment rateFragment;

    private int iDStore = -1;
    private DataStoreDetail storeDetail;

    @Override
    protected int getContentView() {
        return R.layout.activity_store_detail;
    }

    @Override
    protected void initView()
    {
        showIconBack();
        title.setText("Chi tiết cửa hàng");

        int positionID = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.POSITION_ID, 0);
        if (positionID == GlobalVariables.IS_USER)
        {
            myRateLayout.setVisibility(View.VISIBLE);
            cvBook.setVisibility(View.VISIBLE);
        }
        else
        {
            myRateLayout.setVisibility(View.GONE);
            cvBook.setVisibility(View.GONE);
        }

        Bundle bundle = getIntent().getExtras();
        iDStore = bundle.getInt(StorageAdapter.ID_STORE);
        HashMap<String, String> params = new HashMap<>();
        params.put("Token", PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, ""));
        params.put("ID", iDStore + "");

        showLoadingDialog();
        llContent.setVisibility(View.GONE);
        ApiService.Factory.getInstance().storeDetail(params).enqueue(new Callback<ResponseStoreDetail>()
        {
            @Override
            public void onResponse(Call<ResponseStoreDetail> call, Response<ResponseStoreDetail> response)
            {
                if (response.body().getStatus() == 1)
                {
                    DataStoreDetail dataStoreDetail = response.body().getData();
                    setView(dataStoreDetail);
                }

                hideLoadingDialog();
                llContent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseStoreDetail> call, Throwable t) {
                LogManager.tagDefault().error();
            }
        });

        bookCalendar.setOnClickListener(this);
    }

    private void setView(DataStoreDetail dataStoreDetail)
    {
        this.storeDetail = dataStoreDetail;
        UserStore userStore = dataStoreDetail.getUserStore();
        name.setText(userStore.getName());
        address.setText("Địa chỉ: " + userStore.getAddress() + " - " + userStore.getDistrictName() + " - " + userStore.getProvinceName());
        phone.setText("Số điện thoại: " + userStore.getPhone());

        textMyRate.setText("Đánh giá của tôi: " + dataStoreDetail.getRating() + "/5");
        textRateAverage.setText("Đánh giá: " + dataStoreDetail.getRatingAverage() + "/5");
        ratingBar.setRating(dataStoreDetail.getRatingAverage());

        categoriesFragment = new CategoriesFragment();
        categoriesFragment.setCategoryList(userStore.getCategories());

        commentFragment = new CommentFragment();
        commentFragment.setDiscusses(dataStoreDetail.getDiscusses());
        commentFragment.setIdStore(dataStoreDetail.getUserStore().getiDUser());

        rateFragment = new RateFragment();
        rateFragment.setRating(dataStoreDetail.isRating());
        rateFragment.setDataList(dataStoreDetail.getRatings());

        adapter = new TabPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(categoriesFragment, "Dịch vụ");
        adapter.addFragment(commentFragment, "Thảo luận");
        adapter.addFragment(rateFragment, "Đánh giá");

        viewPager.setPageTransformer(false, new TransformerFadeViewPager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(adapter.getCount());

        ratingMe.setIsIndicator(!dataStoreDetail.isRating());
        ratingBar.setIsIndicator(true);

        ratingMe.setOnRatingBarChangeListener((ratingBar, rating, fromUser) ->
        {
            if (fromUser)
            {
                DialogConfirmRate dialogConfirmRate = new DialogConfirmRate();
                dialogConfirmRate.setRatingSelect(rating);
                dialogConfirmRate.setIdStore(iDStore);
                dialogConfirmRate.show(getSupportFragmentManager(), dialogConfirmRate.getTag());
            }
        });

        ratingMe.setRating(dataStoreDetail.getRating());
    }

    @Override
    protected void initMenu(Menu menu) {}

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bookCalendar:
            {
                Bundle bundle = new Bundle();
                bundle.putInt(OrderFragment.IDSTORE, storeDetail.getUserStore().getiDUser());
                bundle.putString(OrderFragment.NAME_STORE, storeDetail.getUserStore().getName());
                bundle.putBoolean(OrderFragment.WHERE, true);
                bundle.putInt(OrderFragment.TYPE, OrderFragment.ORDER_NORMAL);
                addFragment(new OrderFragment(), bundle, true);
                break;
            }
        }
    }

    @Override
    public void sendList(DataRate dataRate)
    {
        viewPager.setCurrentItem(2);
        rateFragment.updateData(dataRate.getRatings());

        storeDetail.setRating(dataRate.getRating());
        storeDetail.setRatingAverage(dataRate.getRatingAverage());

        ratingMe.setRating(dataRate.getRating());
        ratingBar.setRating(dataRate.getRatingAverage());
    }

    @Override
    public void sendOldRate(float ratingOld)
    {
        ratingMe.setRating(storeDetail.getRating());
        ratingBar.setRating(storeDetail.getRatingAverage());
    }
}
