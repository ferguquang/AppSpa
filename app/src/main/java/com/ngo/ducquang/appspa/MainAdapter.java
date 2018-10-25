package com.ngo.ducquang.appspa;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ngo.ducquang.appspa.base.FooterViewHolder;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.view.TextViewFont;
import com.ngo.ducquang.appspa.base.view.TransformerFadeViewPager;
import com.ngo.ducquang.appspa.modelImageSlide.File;
import com.ngo.ducquang.appspa.oder.OrderFragment;
import com.ngo.ducquang.appspa.notification.NotificationActivity;
import com.ngo.ducquang.appspa.oder.OrderListActivity;
import com.ngo.ducquang.appspa.report.ReportActivity;
import com.ngo.ducquang.appspa.service.ServiceAdminActivity;
import com.ngo.ducquang.appspa.storageList.StoreActivity;
import com.ngo.ducquang.appspa.userList.UserListActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducqu on 9/25/2018.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int TYPE_BANNER = 1;
    private final int TYPE_ITEM = 2;
    private final int TYPE_FOOTER = 3;

    private List<ModelItemMain> dataList;
    private FragmentManager fragmentManager;
    private MainActivity context;
    private List<File> bannerModels;

    public MainAdapter(List<ModelItemMain> dataList, FragmentManager fragmentManager, MainActivity context, List<File> bannerModels) {
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.bannerModels = bannerModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (viewType == TYPE_BANNER)
        {
            view = inflater.inflate(R.layout.item_main_banner, parent, false);
            return new BannerHolder(view);
        }
        else if (viewType == TYPE_ITEM)
        {
            view = inflater.inflate(R.layout.item_main_activity, parent, false);
            return new ItemHolder(view);
        }
        else if (viewType == TYPE_FOOTER)
        {
            view = inflater.inflate(R.layout.listview_footer, parent, false);
            return new FooterViewHolder(view);
        }

        throw new RuntimeException("k có type nào");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder)
        {
            ((ItemHolder) holder).bindDing(dataList.get(position -1));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
        {
            return TYPE_BANNER;
        }
        else if (dataList.size() != 0 && position > dataList.size())
        {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    public class BannerHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.viewPager) ViewPager viewPager;
        @BindView(R.id.sliderDot) LinearLayout sliderDot;

        private BannerAdapter adapter;
        private int dotCount;
        private ImageView[] dots;

        private int currentPage = 0;
        private Timer timer;
        private long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
        private long PERIOD_MS = 3500;

        public BannerHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

            adapter = new BannerAdapter(bannerModels, context);
            viewPager.setAdapter(adapter);
            viewPager.setPageTransformer(true,  new TransformerFadeViewPager());

            dotCount = adapter.getCount();

            if (dotCount != 0)
            {
                dots = new ImageView[dotCount];

                for (int i = 0; i < dotCount; i++)
                {
                    dots[i] = new ImageView(context);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);
                    sliderDot.addView(dots[i], params);
                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
            }

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
            {
                @Override
                public void onPageScrolled(int position, float v, int i1) {}

                @Override
                public void onPageSelected(int position)
                {
                    try
                    {
                        for(int i = 0; i < dotCount; i++)
                        {
                            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
                    }
                    catch (Exception e)
                    {
                        LogManager.tagDefault().error(e.toString());
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {}
            });

            try
            {
                Handler handler = new Handler();
                timer = new Timer(); // This will create a new Thread
                timer.schedule(new TimerTask() // task to be scheduled
                {
                    @Override
                    public void run()
                    {
                        handler.post(() ->
                        {
                            try
                            {
                                if (currentPage == bannerModels.size())
                                {
                                    currentPage = 0;
                                }

                                viewPager.setCurrentItem(currentPage++, true);
                            }
                            catch (Exception e)
                            {
                                LogManager.tagDefault().error(e.toString());
                            }
                        });
                    }
                }, DELAY_MS, PERIOD_MS);
            }
            catch (Exception e)
            {
                LogManager.tagDefault().error(e.toString());
            }
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.iconImage) ImageView iconImage;
        @BindView(R.id.name) TextViewFont name;
        @BindView(R.id.cvGroup) CardView cvGroup;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cvGroup.setOnClickListener(this);
        }

        public void bindDing(ModelItemMain model)
        {
            iconImage.setImageResource(model.getIdImage());
            name.setText(model.getName().toUpperCase());
            name.setTextBold();
            cvGroup.setCardBackgroundColor(context.getResources().getColor(R.color.colorButton));
        }

        @Override
        public void onClick(View v)
        {
            switch (dataList.get(getAdapterPosition() - 1).getId())
            {
                case MainActivity.BOOK:
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt(OrderFragment.TYPE, OrderFragment.ORDER_NORMAL);
                    context.addFragment(new OrderFragment(), bundle, true);
                    break;
                }
                case MainActivity.STORE_LIST:
                {
                    context.startActivity(StoreActivity.class, null, false);
                    break;
                }
                case MainActivity.NOTIFICATION:
                {
                    context.startActivity(NotificationActivity.class, null, false);
                    break;
                }
                case MainActivity.BOOK_AT_HOME:
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt(OrderFragment.TYPE, OrderFragment.ORDER_AT_HOME);
                    context.addFragment(new OrderFragment(), bundle, true);
                    break;
                }
                case MainActivity.LIST_USER:
                {
                    context.startActivity(UserListActivity.class, null, false);
                    break;
                }
                case MainActivity.LIST_SERVICE:
                {
                    context.startActivity(ServiceAdminActivity.class, null, false);
                    break;
                }
                case MainActivity.LIST_ORDER:
                {
                    context.startActivity(OrderListActivity.class, null, false);
                    break;
                }
                case MainActivity.REPORT:
                {
                    context.startActivity(ReportActivity.class, null, false);
                    break;
                }
            }
        }
    }

    public void updateImage(List<File> files)
    {
        bannerModels.clear();
        bannerModels.addAll(files);
        notifyItemChanged(0);
    }
}
