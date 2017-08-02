package asia.covisoft.bsandroid.activity;

import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bvhloc.numpicker.widget.NumberPicker;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;
import java.util.List;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.adapter.ProductAttributeRecyclerAdapter;
import asia.covisoft.bsandroid.adapter.VariationAdapter;
import asia.covisoft.bsandroid.base.BaseLoadingActivity;
import asia.covisoft.bsandroid.fragment.ProductReviewFragment;
import asia.covisoft.bsandroid.helper.DiscountRate;
import asia.covisoft.bsandroid.order.OrderHelper;
import asia.covisoft.bsandroid.order.OrderProduct;
import asia.covisoft.bsandroid.prefs.Constant;
import asia.covisoft.bsandroid.prefs.Extras;
import asia.covisoft.bsandroid.prefs.WAApi;
import asia.covisoft.bsandroid.utils.Html;
import asia.covisoft.bsandroid.utils.NumberFormatter;
import asia.covisoft.bsandroid.utils.SystemHelper;
import asia.covisoft.bsandroid.wa.presenter.ReviewPresenter;
import asia.covisoft.bsandroid.wc.model.get.product.Attribute;
import asia.covisoft.bsandroid.wc.model.get.product.Image;
import asia.covisoft.bsandroid.wc.model.get.product.Product;
import asia.covisoft.bsandroid.wc.model.get.product.Variation;

public class ProductDetailsActivity extends BaseLoadingActivity {

    private TextView tvProductName;
    private TextView tvPrice;
    private TextView tvDiscountRate;
    private TextView tvRegularPrice;
    private SliderLayout sliderLayout;
    private RatingBar ratingBar, rbrReview;
    private NumberPicker numberPicker;
    private TextView tvDescription;
    private View vDiscount, lnlReview, lnlAuthor;
    private EditText edtReview, edtAuthorName, edtAuthorEmail;
    private RecyclerView rvAttributes;
    private Spinner spnVariations;
    private WebView wvDescription;

    @Override
    public int getView() {
        return R.layout.activity_product_details;
    }

    @Override
    public void initView() {
        super.initView();

        updateToolbar(getString(R.string.cap_productinfo), true);

        tvProductName = (TextView) findViewById(R.id.tvName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDiscountRate = (TextView) findViewById(R.id.tvDiscountRate);
        tvRegularPrice = (TextView) findViewById(R.id.tvRegularPrice);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        vDiscount = findViewById(R.id.vDiscount);
        lnlReview = findViewById(R.id.lnlReview);
        lnlAuthor = findViewById(R.id.lnlAuthor);
        edtReview = (EditText) findViewById(R.id.edtReview);
        edtAuthorName = (EditText) findViewById(R.id.edtAuthorName);
        edtAuthorEmail = (EditText) findViewById(R.id.edtAuthorEmail);
        rbrReview = (RatingBar) findViewById(R.id.rbrReview);
        rvAttributes = (RecyclerView) findViewById(R.id.rvAttributes);
        spnVariations = (Spinner) findViewById(R.id.spnVariations);
        wvDescription = (WebView) findViewById(R.id.wvDescription);

        findViewById(R.id.btnAddCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addCart();
            }
        });

        findViewById(R.id.btnReview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postReview();
            }
        });

        findViewById(R.id.tvShowReviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateToolbar(getString(R.string.cap_product_preview), true);
                fragmentNavigator.goTo(new ProductReviewFragment());
            }
        });
    }

    public Product product;

    @Override
    public void initValue() {

        product = (Product) getIntent().getSerializableExtra(Extras.PRODUCT);
        if (product != null) {

            initSlider(product.getImages());
            tvProductName.setText(product.getName());
            String price = product.getPrice();
            String regularPrice = product.getRegularPrice();
            if (regularPrice.equals("") || price.equals(regularPrice)) {
                vDiscount.setVisibility(View.GONE);
            } else {
                String discountRate = DiscountRate.calculator(Long.parseLong(price), Long.parseLong(regularPrice)) + "%";
                tvDiscountRate.setText(discountRate);
                regularPrice = NumberFormatter.formatCurrency(Long.parseLong(regularPrice)) + " " + context.getString(R.string.unit_vnd);
                tvRegularPrice.setText(regularPrice);
                tvRegularPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
//                        String price = !product.getRegularPrice().equals("") ? product.getRegularPrice() : product.getPrice();

            price = NumberFormatter.formatCurrency(Long.parseLong(price)) + " " + context.getString(R.string.unit_vnd);
            tvPrice.setText(price);
            tvDescription.setText(Html.fromHtml(product.getDescription()));
            ratingBar.setRating(product.getRatingCount());

            initVariationList(product.getVariations());
//            initAttributeList(product.getAttributes());
            wvDescription.loadUrl(WAApi.NEWS_PRODUCT_CONTENT + product.getId());
        }


//        ProductVariationPresenter.listAll(context, product.getId(), new ProductVariationPresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<Product> products) {
//
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });


    }

    private void initVariationList(final List<Variation> variations) {

        if (variations.size() > 0) {
            VariationAdapter adapter = new VariationAdapter(context, variations);
            spnVariations.setAdapter(adapter);
            spnVariations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                    updateUIonVariationSelected(variations.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            updateUIonVariationSelected(variations.get(0));
        } else {
            spnVariations.setVisibility(View.GONE);
        }
    }

    private void updateUIonVariationSelected(Variation variation) {

        String price = variation.getPrice();
        String regularPrice = variation.getRegularPrice();
        if (regularPrice.equals("") || price.equals(regularPrice)) {
            vDiscount.setVisibility(View.GONE);
        } else {
            vDiscount.setVisibility(View.VISIBLE);
            String discountRate = DiscountRate.calculator(Long.parseLong(price), Long.parseLong(regularPrice)) + "%";
            tvDiscountRate.setText(discountRate);
        }

        price = NumberFormatter.formatCurrency(Long.parseLong(price)) + " " + context.getString(R.string.unit_vnd);
        tvPrice.setText(price);
    }

    public void initSlider(final List<Image> images) {
        HashMap<String, String> urlMaps = new HashMap<>();

        if (images != null) {
            for (int i = 0; i < images.size(); i++) {
                urlMaps.put(images.get(i).getName(), images.get(i).getSrc());
            }

            for (String name : urlMaps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .image(urlMaps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                sliderLayout.addSlider(textSliderView);
            }
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setDuration(Constant.SLIDER_DURATION);
    }

    private void initAttributeList(List<Attribute> attributes) {

        rvAttributes.setLayoutManager(new LinearLayoutManager(context));

        ProductAttributeRecyclerAdapter adapter = new ProductAttributeRecyclerAdapter(context);
        adapter.insertItems(attributes);

        rvAttributes.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        // prevent a memory leak on rotation
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void initAction() {
//        numberPicker.setOnPickListener(new NumberPicker.OnPickedListener() {
//            @Override
//            public void onPicked(int pickedValue) {
//
//            }
//        });

        if (OrderHelper.customer != null) {

            lnlAuthor.setVisibility(View.GONE);
        }
    }

    private void postReview() {

        ReviewPresenter.PostCallback postCallback = new ReviewPresenter.PostCallback() {
            @Override
            public void onResponse(boolean success) {

                if (success) {

                    dismissLoading();
                    Toast.makeText(context, R.string.title_activity_product_detail_reviewpostsuccess, Toast.LENGTH_LONG)
                            .show();
                    lnlReview.setVisibility(View.GONE);

                } else {

                    onConnectionTimeOut();
                }
            }

            @Override
            public void onConnectionTimeOut() {

                dismissLoading();
                Toast.makeText(context, R.string.title_activity_product_detail_reviewpostfail, Toast.LENGTH_LONG)
                        .show();
            }
        };

        int rating =(int) rbrReview.getRating();
        final String content = edtReview.getText().toString().trim();
        String authorName = edtAuthorName.getText().toString().trim();
        String authorEmail = edtAuthorEmail.getText().toString().trim();

        if (content.equals("")) {
            edtReview.requestFocus();
            return;
        }

        if (OrderHelper.customer != null) {

            showLoading();
            SystemHelper.hideKeyboard(context);
            ReviewPresenter.post(context, product.getId(), rating, content, OrderHelper.customer, postCallback);
            return;
        }

        if (authorName.equals("")) {
            edtAuthorName.requestFocus();
            return;
        }

        if (authorEmail.equals("")) {
            edtAuthorEmail.requestFocus();
            return;
        }

        showLoading();
        SystemHelper.hideKeyboard(context);
        ReviewPresenter.post(context, product.getId(), rating, content, authorName, authorEmail, postCallback);
    }

    private void addCart() {

        if (numberPicker.getCurrent() > 0) {

            OrderProduct orderProduct = new OrderProduct(product, null);
            if (product.getVariations().size() > 0) {
                orderProduct.setVariation((Variation) spnVariations.getSelectedItem());
            }
            OrderHelper.addCart(orderProduct, numberPicker.getCurrent());
            numberPicker.setCurrent(0);
            Toast.makeText(context, R.string.title_activity_product_detail_addcartsuccess, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentNavigator.getSize() >= 1) {
            updateToolbar(getString(R.string.cap_productinfo), true);
            fragmentNavigator.goOneBack();
        } else {
            super.onBackPressed();
        }
    }
}
