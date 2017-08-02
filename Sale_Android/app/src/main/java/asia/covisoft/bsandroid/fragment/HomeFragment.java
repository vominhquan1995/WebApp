package asia.covisoft.bsandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import java.util.List;

import asia.covisoft.bsandroid.MainActivity;
import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.adapter.ProductCategoryRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseLoadingFragment;
import asia.covisoft.bsandroid.dialog.ErrorDialog;
import asia.covisoft.bsandroid.prefs.Constant;
import asia.covisoft.bsandroid.utils.ColumnCalculator;
import asia.covisoft.bsandroid.wc.model.get.product.Category;
import asia.covisoft.bsandroid.wc.model.get.product.category.ProductCategory;
import asia.covisoft.bsandroid.wc.presenter.product.ProductCategoryPresenter;
import asia.covisoft.bsandroid.widget.CustomSliderView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseLoadingFragment {

    // Slider
    private SliderLayout sliderLayout;

    // Section list
    private RecyclerView rvProductCategory;

    @Override
    public int getRootView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        sliderLayout = (SliderLayout) rootView.findViewById(R.id.slider);
        rvProductCategory = (RecyclerView) rootView.findViewById(R.id.rvProductCategory);
    }

    @Override
    public void initValue() {
        showLoading();

        loadCategory();
    }

    @Override
    public void initAction() {

    }

    public void initSlider(final int[] imgList) {

        for (int resId : imgList) {
            CustomSliderView customSliderView = new CustomSliderView(context);
            // initialize a SliderLayout
            customSliderView
                    .image(resId)
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            sliderLayout.addSlider(customSliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setDuration(Constant.SLIDER_DURATION);
    }

    private void loadCategory() {
        ProductCategoryPresenter.listAll(context, new ProductCategoryPresenter.ListAllCallback() {
            @Override
            public void onResponse(List<ProductCategory> productCategories) {
                dismissLoading();

                final ProductCategoryRecyclerAdapter categoryAdapter = new ProductCategoryRecyclerAdapter(context, productCategories);
                rvProductCategory.setHasFixedSize(true);
                rvProductCategory.setLayoutManager(new GridLayoutManager(context, ColumnCalculator.calculate(context, 180)));
                rvProductCategory.setNestedScrollingEnabled(false);
                categoryAdapter.setOnItemClickListener(new ProductCategoryRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        ProductCategory category = categoryAdapter.getItem(position);

                        Fragment productFragment = new ProductsFragment();
                        Bundle args = new Bundle();
                        args.putLong(ProductsFragment.CATEGORY_ID, category.getId());
                        args.putString(ProductsFragment.CATEGORY_NAME, category.getName());
                        productFragment.setArguments(args);

                        ((MainActivity) getActivity()).
                                switchFragment(productFragment);
                    }
                });
                rvProductCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onConnectionTimeOut() {
                dismissLoading();
                ErrorDialog.showConnectionTimeOutDialog(context);
            }
        });
    }
}
