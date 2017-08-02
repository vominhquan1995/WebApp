package asia.covisoft.bsandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseEndlessRecyclerAdapter;
import asia.covisoft.bsandroid.wa.model.News;
import asia.covisoft.bsandroid.utils.Html;

/**
 * Created by Leon on 4/10/2017.
 */

public class NewsRecyclerAdapter extends BaseEndlessRecyclerAdapter<News> {

    public NewsRecyclerAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    @Override
    protected int getItemLayout() {
        return R.layout.list_item_news;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgNews;
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvContent;

        private ItemViewHolder(View view) {
            super(view);
            imgNews = (ImageView) view.findViewById(R.id.imgNews);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClickListener.onItemClick(null, view, getAdapterPosition(), 0);
                }
            });
        }
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position, News item) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;

        viewHolder.tvTitle.setText(item.getPostTitle());
        viewHolder.tvDate.setText(item.getPostDate());
        viewHolder.tvContent.setText(Html.fromHtml(item.getPostContent()));
        if (item.getImage() != null) {
            Picasso.with(context)
                    .load(item.getImage())
                    .into(viewHolder.imgNews);
        }
    }
}
