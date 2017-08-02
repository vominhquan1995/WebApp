package asia.covisoft.bsandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseEndlessRecyclerAdapter;
import asia.covisoft.bsandroid.wa.model.Review;
import asia.covisoft.bsandroid.wawidget.WATextView;

/**
 * Created by Leon on 4/11/2017.
 */

public class ReviewRecyclerAdapter extends BaseEndlessRecyclerAdapter<Review> {

    public ReviewRecyclerAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    @Override
    protected int getItemLayout() {
        return R.layout.list_item_review;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private WATextView tvAuthor;
        private TextView tvDate;
        private RatingBar rbrRating;
        private WATextView tvContent;

        private ItemViewHolder(View view) {
            super(view);

            tvAuthor = (WATextView) view.findViewById(R.id.tvAuthor);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            rbrRating = (RatingBar) view.findViewById(R.id.rbrRating);
            tvContent = (WATextView) view.findViewById(R.id.tvContent);
        }
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position, Review item) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;

        viewHolder.tvAuthor.setText(item.getCommentAuthor());
        viewHolder.tvDate.setText(item.getCommentDate());
        viewHolder.rbrRating.setRating(item.getProductRating());
        viewHolder.tvContent.setText(item.getCommentContent());
    }
}
