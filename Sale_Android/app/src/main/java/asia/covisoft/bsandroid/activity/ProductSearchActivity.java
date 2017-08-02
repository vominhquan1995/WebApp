package asia.covisoft.bsandroid.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.adapter.ProductRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseEndlessRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseToolbarActivity;
import asia.covisoft.bsandroid.dialog.ErrorDialog;
import asia.covisoft.bsandroid.mvp.presenter.ProductSearchPresenter;
import asia.covisoft.bsandroid.mvp.presenter.ProductSearchView;
import asia.covisoft.bsandroid.prefs.Extras;
import asia.covisoft.bsandroid.utils.SystemHelper;
import asia.covisoft.bsandroid.wc.WCDefaultValue;
import asia.covisoft.bsandroid.wc.model.get.product.Product;

public class ProductSearchActivity extends BaseToolbarActivity implements ProductSearchView {

    @Override
    public int getView() {
        return R.layout.activity_product_search;
    }

    private EditText edtSearch;
    private RecyclerView rvProducts;
    private ProgressBar pbSearch;
    private TextView tvNoResult;

    @Override
    public void initView() {
        super.initView();

        updateToolbar(getString(R.string.cap_productsearch), true);

        edtSearch = (EditText) findViewById(R.id.edtSearch);
        rvProducts = (RecyclerView) findViewById(R.id.rvProducts);
        pbSearch = (ProgressBar) findViewById(R.id.pbSearch);
        tvNoResult = (TextView) findViewById(R.id.tvNoResult);

        pbSearch.setVisibility(View.GONE);
        tvNoResult.setVisibility(View.GONE);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                pbSearch.setVisibility(View.GONE);
                tvNoResult.setVisibility(View.GONE);
                if (String.valueOf(charSequence).trim().length() > 0) {
                    keyWord = String.valueOf(charSequence).trim();
//                    keyWord = keyWord.replace(" ", "%25");
                    if (productAdapter != null) {
                        productAdapter.setEndlessLoadingEnable(false);
                        productAdapter.removeAllItems();
                    }
                    presenter.search(keyWord);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                SystemHelper.hideKeyboard(context);
                return true;
            }
        });
    }

    private ProductSearchPresenter presenter;
    private ProductRecyclerAdapter productAdapter;
    private String keyWord;

    @Override
    public void initValue() {

        presenter = new ProductSearchPresenter(this);
        productAdapter = new ProductRecyclerAdapter(context);
        productAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra(Extras.PRODUCT, productAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void initAction() {

        rvProducts.setLayoutManager(new GridLayoutManager(context, 2));
        productAdapter.setEndlessLoadingListener(rvProducts, new BaseEndlessRecyclerAdapter.OnEndlessLoadListener() {
            @Override
            public void onEndlessLoad(int page) {

                presenter.getProduct(keyWord, page);
            }
        });
        rvProducts.setAdapter(productAdapter);
    }

    @Override
    public void onPreSearch() {

        pbSearch.setVisibility(View.VISIBLE);
        productAdapter.resetPage();
        productAdapter.setEndlessLoadingEnable(true);
    }

    @Override
    public void onProductResult(List<Product> products) {

        if (products.size() > 0) {
            productAdapter.insertLoadmoreItems(products);
            if (products.size() < WCDefaultValue.MAX_ITEM_PER_PAGE) {
                productAdapter.setEndlessLoadingEnable(false);
            }
        } else {
            productAdapter.setEndlessLoadingEnable(false);
            if (pbSearch.getVisibility() == View.VISIBLE) {
                tvNoResult.setVisibility(View.VISIBLE);
            }
        }
        pbSearch.setVisibility(View.GONE);
    }

    @Override
    public void onConnectionTimeOut() {

        pbSearch.setVisibility(View.GONE);
        ErrorDialog.showConnectionTimeOutDialog(context);
    }
}
