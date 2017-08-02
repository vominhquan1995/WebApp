package asia.covisoft.bsandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseEndlessRecyclerAdapter;
import asia.covisoft.bsandroid.helper.DiscountRate;
import asia.covisoft.bsandroid.utils.NumberFormatter;
import asia.covisoft.bsandroid.wc.model.get.product.Product;

/**
 * Created by thong on 2/23/2017.
 * PROJECT BS_ANDROID
 */

public class ProductRecyclerAdapter extends BaseEndlessRecyclerAdapter<Product> {

    public ProductRecyclerAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    @Override
    protected int getItemLayout() {
        return R.layout.list_item_product;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(View view) {
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position, Product item) {

        final ProductsViewHolder viewHolder = (ProductsViewHolder) holder;

        Picasso.with(context)
                .load(item.getImages().get(0).getSrc())
                .fit()
                .into(viewHolder.imgProduct, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.pbLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        viewHolder.tvName.setText(item.getName());

        String price = item.getPrice();
        String regularPrice = item.getRegularPrice();

        if (regularPrice.equals("") || price.equals(regularPrice)) {
            viewHolder.tvDiscountRate.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tvDiscountRate.setVisibility(View.VISIBLE);
            long discountRate = DiscountRate.calculator(Long.parseLong(price), Long.parseLong(regularPrice));
            String discountText = "-" + discountRate + "%";
            viewHolder.tvDiscountRate.setText(discountText);
        }

        price = NumberFormatter.formatCurrency(Long.parseLong(price)) + " " + context.getString(R.string.unit_vnd);

        viewHolder.tvPrice.setText(price);

        if (item.getVariations().size() > 0) {
            viewHolder.tvFrom.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvFrom.setVisibility(View.GONE);
        }

        viewHolder.ratingBar.setRating(item.getRatingCount());
    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder {

        private ImageButton btnFavorite;
        //        private CardView cvDiscountRate;
        private TextView tvDiscountRate;
        private ProgressBar pbLoading;
        private ImageView imgProduct;
        private TextView tvName;
        private RatingBar ratingBar;
        private TextView tvPrice;
        private TextView tvFrom;

        ProductsViewHolder(final View itemView) {
            super(itemView);

            btnFavorite = (ImageButton) itemView.findViewById(R.id.btnFavorite);
            btnFavorite.setVisibility(View.GONE);
//            cvDiscountRate = (CardView) itemView.findViewById(R.id.cvDiscountRate);
            tvDiscountRate = (TextView) itemView.findViewById(R.id.tvDiscountRate);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pbLoading);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvFrom = (TextView) itemView.findViewById(R.id.tvFrom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(null, itemView, getAdapterPosition(), getItem(getAdapterPosition()).getId());
                    }
                }
            });
        }
    }
}
