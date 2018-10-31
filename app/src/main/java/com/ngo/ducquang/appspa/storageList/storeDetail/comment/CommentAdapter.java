package com.ngo.ducquang.appspa.storageList.storeDetail.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.EmptyViewHolder;
import com.ngo.ducquang.appspa.base.FooterViewHolder;
import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.StringUtilities;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.base.font.FontChangeCrawler;
import com.ngo.ducquang.appspa.base.view.TextViewFont;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.Discuss;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.ResponseComment;
import com.ngo.ducquang.appspa.storageList.storeDetail.rate.RateAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducqu on 10/3/2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int TYPE_DISCUSS = 1;
    private final int TYPE_COMMENT = 2;

    private Context context;
    private List<Discuss> discusses;
    private int idStore;

    public CommentAdapter(Context context, List<Discuss> discusses, int idStore) {
        this.context = context;
        this.discusses = discusses;
        this.idStore = idStore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), GlobalVariables.FONT_BASE);

        View view = null;

        if (viewType == TYPE_DISCUSS)
        {
            view = inflater.inflate(R.layout.item_discuss, parent, false);
            fontChanger.replaceFonts((ViewGroup)view);
            return new DiscussViewHolder(view);
        }
        else if (viewType == TYPE_COMMENT)
        {
            view = inflater.inflate(R.layout.item_comment, parent, false);
            fontChanger.replaceFonts((ViewGroup)view);
            return new CommentViewHolder(view);
        }

        throw new RuntimeException("k có type nào");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof DiscussViewHolder)
        {
            ((DiscussViewHolder) holder).binding(discusses.get(position));
        }

        if (holder instanceof CommentViewHolder)
        {
            ((CommentViewHolder) holder).binding();
        }
    }

    @Override
    public int getItemCount() {
        if (discusses.size() == 0)
        {
            return 1;
        }
        else
        {
            return discusses.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (discusses.size() == 0 || position == discusses.size())
        {
            return TYPE_COMMENT;
        }
        else
        {
            return TYPE_DISCUSS;
        }
    }

    public class DiscussViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.body) TextViewFont body;
        @BindView(R.id.dateTime) TextView dateTime;

        public DiscussViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void binding(Discuss model)
        {
            name.setText(model.getCreatedByName());
            body.setText(model.getBody());
            body.setTextBold();
            dateTime.setText(ManagerTime.convertToMonthDayYearHourMinuteFormat(model.getCreated()));
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.commentEdt) EditText commentEdt;
        @BindView(R.id.sendComment) ImageView sendComment;
        @BindView(R.id.emptyDiscuss) TextView emptyDiscuss;

        public CommentViewHolder(View itemView)
        {
            super(itemView);

            ButterKnife.bind(this, itemView);

            sendComment.setOnClickListener(v ->
            {
                String body = commentEdt.getText().toString();

                if (StringUtilities.isEmpty(body))
                {
                    Toasty.error(context, "Yêu cầu nhập bình luận", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("Token", PreferenceUtil.getPreferences(context, PreferenceUtil.TOKEN, ""));
                params.put("ID", idStore + "");
                params.put("Body", body);
                ApiService.Factory.getInstance().commentStore(params).enqueue(new Callback<ResponseComment>()
                {
                    @Override
                    public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            discusses.clear();
                            discusses = response.body().getData().getDiscusses();
                            notifyDataSetChanged();

                            commentEdt.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseComment> call, Throwable t)
                    {
                        LogManager.tagDefault().error(t.getMessage());
                    }
                });
            });
        }

        public void binding()
        {
            if (discusses.size() == 0) emptyDiscuss.setVisibility(View.VISIBLE);
            else emptyDiscuss.setVisibility(View.GONE);
        }
    }
}
