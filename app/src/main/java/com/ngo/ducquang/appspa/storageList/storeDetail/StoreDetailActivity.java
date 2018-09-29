package com.ngo.ducquang.appspa.storageList.storeDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseActivity;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.view.TabPagerAdapter;
import com.ngo.ducquang.appspa.base.view.TransformerFadeViewPager;
import com.ngo.ducquang.appspa.login.modelRegister.ResponseRegister;
import com.ngo.ducquang.appspa.storageList.StorageAdapter;
import com.ngo.ducquang.appspa.storageList.model.UserStore;
import com.ngo.ducquang.appspa.storageList.storeDetail.categories.CategoriesFragment;
import com.ngo.ducquang.appspa.storageList.storeDetail.commentRating.CommentRatingFragment;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.ResponseStoreDetail;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 9/21/2018.
 */

public class StoreDetailActivity extends BaseActivity
{
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.address) TextView address;
    @BindView(R.id.phone) TextView phone;

    private TabPagerAdapter adapter;

    private CategoriesFragment categoriesFragment;
    private CommentRatingFragment commentRatingFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_store_detail;
    }

    @Override
    protected void initView() {
        showIconBack();
        title.setText("Chi tiết cửa hàng");

        Bundle bundle = getIntent().getExtras();
        int idStore = bundle.getInt(StorageAdapter.ID_STORE);
        HashMap<String, String> params = new HashMap<>();
        params.put("Token", PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, ""));
        params.put("ID", idStore + "");

        showLoadingDialog();
        ApiService.Factory.getInstance().storeDetail(params).enqueue(new Callback<ResponseStoreDetail>()
        {
            @Override
            public void onResponse(Call<ResponseStoreDetail> call, Response<ResponseStoreDetail> response)
            {
                if (response.body().getStatus() == 1)
                {
                    UserStore userStore = response.body().getData().getUserStore();
                    setView(userStore);
                    hideLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseStoreDetail> call, Throwable t) {
                LogManager.tagDefault().error();
            }
        });
    }

    private void setView(UserStore userStore)
    {
        name.setText(userStore.getName());
        address.setText("Địa chỉ: " + userStore.getAddress() + " - " + userStore.getDistrictName() + " - " + userStore.getProvinceName());
        phone.setText("Số điện thoại: " + userStore.getPhone());

        categoriesFragment = new CategoriesFragment();
        categoriesFragment.setCategoryList(userStore.getCategories());
        commentRatingFragment = new CommentRatingFragment();

        adapter = new TabPagerAdapter(getSupportFragmentManager());
        adapter.setContext(getApplicationContext());

        adapter.addFragment(categoriesFragment, "Thông tin dịch vụ");
        adapter.addFragment(commentRatingFragment, "Thảo luận và đánh giá");

        viewPager.setPageTransformer(false, new TransformerFadeViewPager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(adapter.getCount());
    }

    @Override
    protected void initMenu(Menu menu)
    {
    }
}
