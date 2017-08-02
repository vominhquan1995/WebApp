package asia.covisoft.bsandroid.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseRecyclerAdapter;
import asia.covisoft.bsandroid.wc.model.get.product.Attribute;

/**
 * Created by Leon on 4/12/2017.
 */

public class ProductAttributeRecyclerAdapter extends BaseRecyclerAdapter<Attribute> {

    private Context context;

    public ProductAttributeRecyclerAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    @Override
    protected int getItemLayout() {
        return R.layout.list_item_product_attribute;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAttributeName;
        private AppCompatSpinner spnOptions;

        private ItemViewHolder(View view) {
            super(view);
            tvAttributeName = (TextView) view.findViewById(R.id.tvAttributeName);
            spnOptions = (AppCompatSpinner) view.findViewById(R.id.spnOptions);
        }
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Attribute item) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;

        viewHolder.tvAttributeName.setText(item.getName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, item.getOptions());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spnOptions.setAdapter(dataAdapter);
    }
}
