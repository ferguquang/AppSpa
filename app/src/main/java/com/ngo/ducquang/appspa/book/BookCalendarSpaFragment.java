package com.ngo.ducquang.appspa.book;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.BaseFragment;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.Share;
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.storageList.model.Category;

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

public class BookCalendarSpaFragment extends BaseFragment implements View.OnClickListener
{
    @BindView(R.id.book) Button book;
    @BindView(R.id.toolBar) Toolbar toolBar;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.llStartDate) LinearLayout llStartDate;
    @BindView(R.id.startDate) TextView startDate;

    private BookOptionAdapter adapter;
    private List<Category> dataList;

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

        dataList = Share.getInstance().categoryList;


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        adapter = new BookOptionAdapter(getActivity(), dataList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.book:
            {
                String date = startDate.getText().toString();

                if (StringUtilities.isEmpty(date))
                {

                }
                HashMap<String, String> params = new HashMap<>();

                ApiService.Factory.getInstance().bookCalendar(params).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
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
        }
    }
}
