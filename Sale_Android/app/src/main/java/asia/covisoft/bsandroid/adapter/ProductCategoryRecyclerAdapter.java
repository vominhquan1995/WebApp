package asia.covisoft.bsandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.wc.model.get.product.category.ProductCategory;

/**
 * Created by thong on 2/24/2017.
 * PROJECT BS_ANDROID
 */

public class ProductCategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ProductCategory> categoryList;

    public ProductCategoryRecyclerAdapter(Context context, List<ProductCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_product_category, parent, false);
        return new ProductCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductCategory category = getItem(position);

        final ProductCategoryViewHolder viewHolder = (ProductCategoryViewHolder) holder;
        Picasso.with(context)
                .load(category.getImage() != null ? category.getImage().getSrc() : "https://cdn1.iconfinder.com/data/icons/school-supplies-3/64/folder_denied_stop_not_found_deleted_error-128.png")
                .fit()
                .into(viewHolder.imgCategory, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.pbLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        viewHolder.pbLoading.setVisibility(View.GONE);
                    }
                });
        viewHolder.tvCategory.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public ProductCategory getItem(int position) {
        return categoryList.get(position);
    }

    private class ProductCategoryViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar pbLoading;
        private ImageView imgCategory;
        private TextView tvCategory;

        ProductCategoryViewHolder(View itemView) {
            super(itemView);

            pbLoading = (ProgressBar) itemView.findViewById(R.id.pbLoading);
            imgCategory = (ImageView) itemView.findViewById(R.id.imgCategory);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
