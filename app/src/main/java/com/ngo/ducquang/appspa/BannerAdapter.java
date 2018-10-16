package com.ngo.ducquang.appspa;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.base.SetImageWithGlide;

import java.util.List;

/**
 * Created by ducqu on 10/13/2018.
 */

public class BannerAdapter extends PagerAdapter
{
    private List<BannerModel> dataList;
    private Context context;

    public BannerAdapter(List<BannerModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.banner_pager_item, null);
        ImageView imageBanner = viewLayout.findViewById(R.id.imageBanner);

        BannerModel model = dataList.get(position);

        SetImageWithGlide.setImageUrlGlide(model.getUrl(), imageBanner);

        ViewPager vp = (ViewPager) container;
        vp.addView(viewLayout, 0);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
