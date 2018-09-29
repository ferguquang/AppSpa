package com.ngo.ducquang.appspa.storageList.storeDetail.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ducqu on 9/27/2018.
 */

public class CategoriesFragment extends BaseFragment
{
    @BindView(R.id.linearLayout) LinearLayout linearLayout;
    private List<Category> categoryList;

    @Override
    protected int getContentView() {
        return R.layout.fragment_categories_detail;
    }

    @Override
    protected void initView(View view)
    {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < categoryList.size(); i++)
        {
            View v =  inflater.inflate(R.layout.item_categories, null);
            TextView name = v.findViewById(R.id.name);
            Category category = categoryList.get(i);
            name.setText(category.getName());

            linearLayout.addView(v);
        }
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
