package asia.covisoft.bsandroid.wa.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asia.covisoft.bsandroid.prefs.WAApi;
import asia.covisoft.bsandroid.utils.HttpUtils;
import asia.covisoft.bsandroid.wa.WAParams;
import asia.covisoft.bsandroid.wa.model.Review;
import asia.covisoft.bsandroid.wc.WCParams;
import asia.covisoft.bsandroid.wc.model.get.customer.Customer;
import asia.covisoft.bsandroid.wc.presenter.AuthorizePresenter;
import asia.covisoft.bsandroid.wc.view.WCCallback;

/**
 * Created by An Pham on 17-Feb-17.
 * Last modifined on 17-Feb-17
 */

public class ReviewPresenter {
    private static final String TAG = ReviewPresenter.class.getSimpleName();

    private static void listAll(Context context, final Map<String, Object> params, final ListAllCallback callback) {

        AuthorizePresenter.authorize(context, WAApi.LIST_PREVIEW, new AuthorizePresenter.Callback() {
            @Override
            public AsyncTask onAuthentication(final String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature) {

                return new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        return HttpUtils.requestHttpPOST(wcRequestURL, params);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        if (result != null) {
                            Log.d(TAG, "onPostExecute: " + result);
                            try {
                                Review[] reviewArray = new Gson().fromJson(result, Review[].class);
                                List<Review> reviews = new ArrayList<>(Arrays.asList(reviewArray));
                                callback.onResponse(reviews);
                                return;
                            } catch (JsonSyntaxException | IllegalStateException ex) {
                                ex.printStackTrace();
                            }
                        }
                        callback.onConnectionTimeOut();
                    }
                }.execute();
            }

            @Override
            public void onConnectionTimeOut() {
                callback.onConnectionTimeOut();
            }

        });
    }

    public static void listAll(Context context, long productId, final ListAllCallback callback) {

        listAll(context, productId, 1, callback);
    }

    public static void listAll(Context context, long productId, int page, final ListAllCallback callback) {

        Map<String, Object> params = new HashMap<>();
        params.put(WAParams.PRODUCT_ID, productId);
        params.put(WCParams.PAGE, page);
        listAll(context, params, callback);
    }

    public interface ListAllCallback extends WCCallback {

        void onResponse(List<Review> reviews);
    }

    private static void post(Context context, final Map<String, Object> params, final PostCallback callback) {

        AuthorizePresenter.authorize(context, WAApi.ADD_PREVIEW, new AuthorizePresenter.Callback() {
            @Override
            public AsyncTask onAuthentication(final String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature) {

                return new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        return HttpUtils.requestHttpPOST(wcRequestURL, params);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        if (result != null) {
                            Log.d(TAG, "onPostExecute: " + result);
                            try {
                                boolean success = Boolean.parseBoolean(result);
                                callback.onResponse(success);
                                return;
                            } catch (JsonSyntaxException | IllegalStateException ex) {
                                ex.printStackTrace();
                            }
                        }
                        callback.onConnectionTimeOut();
                    }
                }.execute();
            }

            @Override
            public void onConnectionTimeOut() {
                callback.onConnectionTimeOut();
            }

        });
    }

    public static void post(Context context, long productId, int rating, String content, Customer customer, PostCallback callback) {

        String name = customer.getFirstName() + " " + customer.getLastName();

        post(context, productId, rating, content, name, customer.getEmail(), customer.getId(), callback);
    }

    public static void post(Context context, long productId, int rating, String content, String authorName, String authorEmail, PostCallback callback) {

        post(context, productId, rating, content, authorName, authorEmail, 0, callback);
    }

    public static void post(Context context, long productId, int rating, String content, String authorName, String authorEmail, long userId, PostCallback callback) {

        Map<String, Object> params = new HashMap<>();
        params.put(WAParams.PRODUCT_ID, productId);
        params.put(WAParams.PRODUCT_RATING, rating);
        params.put(WAParams.COMMENT_CONTENT, content);
        params.put(WAParams.COMMENT_AUTHOR, authorName);
        params.put(WAParams.COMMENT_AUTHOR_EMAIL, authorEmail);
        if (userId != 0) {
            params.put(WAParams.USER_ID, userId);
        }
        post(context, params, callback);
    }

    public interface PostCallback extends WCCallback {

        void onResponse(boolean success);
    }
}
