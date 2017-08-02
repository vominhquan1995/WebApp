package asia.covisoft.bsandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bvhloc.numpicker.widget.NumberPicker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseRecyclerAdapter;
import asia.covisoft.bsandroid.order.OrderHelper;
import asia.covisoft.bsandroid.order.OrderProduct;
import asia.covisoft.bsandroid.utils.NumberFormatter;
import asia.covisoft.bsandroid.wc.model.get.product.Variation;

/**
 * Created by An Pham on 06-Mar-17.
 * Last modifined on 06-Mar-17
 */

public class ProductCartRecyclerAdapter extends BaseRecyclerAdapter<OrderProduct> {

    private Context context;

    public ProductCartRecyclerAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    @Override
    protected int getItemLayout() {
        return R.layout.list_item_product_cart;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, int position, OrderProduct item) {

        final ItemViewHolder viewHolder = (ItemViewHolder) holder;

        String priceStr = item.getProduct().getPrice();

        Variation variation = item.getVariation();
        if (variation != null) {
            priceStr = variation.getPrice();
            viewHolder.tvVariation.setVisibility(View.VISIBLE);
            viewHolder.tvVariation.setText(variation.toString());
        } else {
            viewHolder.tvVariation.setVisibility(View.GONE);
        }

        String price = NumberFormatter.formatCurrency(Long.parseLong(priceStr)) + " " + context.getString(R.string.unit_vnd);
        String imgUrl = item.getProduct().getImages().get(0).getSrc();

        viewHolder.tvName.setText(item.getProduct().getName());
        viewHolder.tvPrice.setText(price);
        viewHolder.numberPicker.setCurrent(OrderHelper.orderProducts.get(item));
        Picasso.with(context)
                .load(imgUrl)
                .fit()
                .into(viewHolder.imgProduct, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvVariation;
        private ImageButton btnRemove;
        private TextView tvPrice;
        private NumberPicker numberPicker;

        private ItemViewHolder(View itemView) {
            super(itemView);

            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvVariation = (TextView) itemView.findViewById(R.id.tvVariation);
            btnRemove = (ImageButton) itemView.findViewById(R.id.btnRemove);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            numberPicker = (NumberPicker) itemView.findViewById(R.id.numberPicker);
            numberPicker.setOnPickListener(new NumberPicker.OnPickedListener() {
                @Override
                public void onPicked(int pickedValue) {

                    OrderHelper.orderProducts.put(getItem(getAdapterPosition()), pickedValue);
                    onQuantitiesChangeListener.onQuantitiesChanged();
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    OrderHelper.orderProducts.remove(getItem(getAdapterPosition()));
                    removeItem(getAdapterPosition());
                    onQuantitiesChangeListener.onQuantitiesChanged();
                }
            });
        }
    }

    private OnQuantitiesChangeListener onQuantitiesChangeListener;

    public void setOnQuantitiesChangeListener(OnQuantitiesChangeListener onQuantitiesChangeListener) {
        this.onQuantitiesChangeListener = onQuantitiesChangeListener;
    }

    public interface OnQuantitiesChangeListener {

        void onQuantitiesChanged();
    }
}
