package asia.covisoft.bsandroid.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.activity.ShippingInfoActivity;
import asia.covisoft.bsandroid.adapter.ProductCartRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseFragment;
import asia.covisoft.bsandroid.order.OrderProduct;
import asia.covisoft.bsandroid.wc.model.get.product.Product;
import asia.covisoft.bsandroid.order.OrderHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {


    @Override
    public int getRootView() {
        return R.layout.fragment_cart;
    }

    private Button btnContinue;
    private RecyclerView rvProducts;
    private TextView tvTotal;

    @Override
    public void initView() {

        btnContinue = (Button) rootView.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
        rvProducts = (RecyclerView) rootView.findViewById(R.id.rvProducts);
        tvTotal = (TextView) rootView.findViewById(R.id.tvTotal);
    }

    @Override
    public void initValue() {


    }

    @Override
    public void initAction() {

        rvProducts.setLayoutManager(new LinearLayoutManager(context));

        if(OrderHelper.orderProducts != null){

            ProductCartRecyclerAdapter adapter = new ProductCartRecyclerAdapter(context);
            List<OrderProduct> orderProducts = new ArrayList<>();
            orderProducts.addAll(OrderHelper.orderProducts.keySet());
            adapter.insertItems(orderProducts);
            adapter.setOnQuantitiesChangeListener(new ProductCartRecyclerAdapter.OnQuantitiesChangeListener() {
                @Override
                public void onQuantitiesChanged() {

                    tvTotal.setText(OrderHelper.getTotalCost(context));
                }
            });
            rvProducts.setAdapter(adapter);

            tvTotal.setText(OrderHelper.getTotalCost(context));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinue:

                if(OrderHelper.getTotalCost()>0){
                    gotoActivity(ShippingInfoActivity.class);
                }

                break;
        }
    }
}
