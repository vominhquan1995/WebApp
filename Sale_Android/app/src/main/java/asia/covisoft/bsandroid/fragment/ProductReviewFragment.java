package asia.covisoft.bsandroid.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.activity.ProductDetailsActivity;
import asia.covisoft.bsandroid.adapter.ReviewRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseEndlessRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseFragment;
import asia.covisoft.bsandroid.dialog.ErrorDialog;
import asia.covisoft.bsandroid.wa.model.Review;
import asia.covisoft.bsandroid.wa.presenter.ReviewPresenter;
import asia.covisoft.bsandroid.wc.WCDefaultValue;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductReviewFragment extends BaseFragment {

    @Override
    public int getRootView() {
        return R.layout.fragment_product_review;
    }

    private RecyclerView rvReviews;
    private ProgressBar progressBar;
    private TextView tvNoReview;

    @Override
    public void initView() {

        rvReviews = (RecyclerView) rootView.findViewById(R.id.rvReviews);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        tvNoReview = (TextView) rootView.findViewById(R.id.tvNoReview);
        tvNoReview.setVisibility(View.GONE);
    }

    private long productId;
    private ReviewRecyclerAdapter reviewAdapter;

    @Override
    public void initValue() {

        productId = ((ProductDetailsActivity) activity).product.getId();

        rvReviews.setLayoutManager(new LinearLayoutManager(context));

        reviewAdapter = new ReviewRecyclerAdapter(context);
        reviewAdapter.setEndlessLoadingListener(rvReviews, new BaseEndlessRecyclerAdapter.OnEndlessLoadListener() {
            @Override
            public void onEndlessLoad(int page) {

                loadReviews(page + 1);
            }
        });

        rvReviews.setAdapter(reviewAdapter);
    }

    @Override
    public void initAction() {

        loadReviews(1);
    }

    private ReviewPresenter.ListAllCallback callback = new ReviewPresenter.ListAllCallback() {
        @Override
        public void onResponse(List<Review> reviews) {

            if (reviews.size() == 0 && progressBar.getVisibility() == View.VISIBLE) {
                tvNoReview.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);

            if (reviews.size() < WCDefaultValue.MAX_ITEM_PER_PAGE) {
                reviewAdapter.setEndlessLoadingEnable(false);
            }

            reviewAdapter.insertLoadmoreItems(reviews);
        }

        @Override
        public void onConnectionTimeOut() {

            ErrorDialog.showConnectionTimeOutDialog(context);
        }
    };

    private void loadReviews(int page) {

        ReviewPresenter.listAll(context, productId, page, callback);
    }
}
