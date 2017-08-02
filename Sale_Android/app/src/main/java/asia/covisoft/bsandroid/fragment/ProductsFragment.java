package asia.covisoft.bsandroid.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.List;

import asia.covisoft.bsandroid.MainActivity;
import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.activity.ProductDetailsActivity;
import asia.covisoft.bsandroid.adapter.ProductRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseEndlessRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseLoadingFragment;
import asia.covisoft.bsandroid.dialog.ErrorDialog;
import asia.covisoft.bsandroid.prefs.Extras;
import asia.covisoft.bsandroid.utils.ColumnCalculator;
import asia.covisoft.bsandroid.wc.WCDefaultValue;
import asia.covisoft.bsandroid.wc.model.get.product.Product;
import asia.covisoft.bsandroid.wc.presenter.product.ProductPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends BaseLoadingFragment {
    private final String TAG = getClass().getSimpleName();

    public static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String CATEGORY_NAME = "CATEGORY_NAME";

    // Declare UI elements
    private Spinner spnCategory;
    private Spinner spnSort;
    private RecyclerView rvProducts;

    @Override
    public int getRootView() {
        return R.layout.fragment_products;
    }

    @Override
    public void initView() {
        spnCategory = (Spinner) rootView.findViewById(R.id.spnCategory);
        spnSort = (Spinner) rootView.findViewById(R.id.spnSort);
        rvProducts = (RecyclerView) rootView.findViewById(R.id.rvProducts);
    }

    private long categoryId;
    private String categoryName;

    private ProductRecyclerAdapter productAdapter;

    @Override
    public void initValue() {

        if (arguments != null) {
            categoryId = arguments.getLong(CATEGORY_ID);
            categoryName = arguments.getString(CATEGORY_NAME);
        }

        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(new GridLayoutManager(context, ColumnCalculator.calculate(context, 180)));

        productAdapter = new ProductRecyclerAdapter(context);
        productAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long productId) {

                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra(Extras.PRODUCT, productAdapter.getItem(position));
                startActivity(intent);
            }
        });
        productAdapter.setEndlessLoadingListener(rvProducts, new BaseEndlessRecyclerAdapter.OnEndlessLoadListener() {
            @Override
            public void onEndlessLoad(int page) {

                loadProducts(page + 1);
            }
        });

        rvProducts.setAdapter(productAdapter);
    }

    @Override
    public void initAction() {

        if (categoryName != null) {
            ((MainActivity) activity).updateToolbar(categoryName, false);
        }

        showLoading();
        loadProducts(1);
    }

    private ProductPresenter.ListAllCallback callback = new ProductPresenter.ListAllCallback() {
        @Override
        public void onResponse(final List<Product> products) {
            dismissLoading();

            if (products.size() < WCDefaultValue.MAX_ITEM_PER_PAGE) {
                productAdapter.setEndlessLoadingEnable(false);
            }

            productAdapter.insertLoadmoreItems(products);
        }

        @Override
        public void onConnectionTimeOut() {
            dismissLoading();
            ErrorDialog.showConnectionTimeOutDialog(context);
        }

    };

    private void loadProducts(int page) {

        ProductPresenter.listAll(context, page, categoryId, callback);
    }
}
