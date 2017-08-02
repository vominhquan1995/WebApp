package asia.covisoft.bsandroid.wa.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import asia.covisoft.bsandroid.wa.view.WACallback;
import asia.covisoft.bsandroid.prefs.WAApi;
import asia.covisoft.bsandroid.prefs.PrefParams;
import asia.covisoft.bsandroid.utils.HttpUtils;
import asia.covisoft.bsandroid.wc.model.get.customer.Customer;
import asia.covisoft.bsandroid.wc.presenter.CustomerPresenter;

/**
 * Created by Leon on 3/31/2017.
 */

public class LoginPresenter {
    private static final String TAG = LoginPresenter.class.getSimpleName();

    public static void login(final Context context, String email, String password, final LoginCallback callback) {

        final String url = WAApi.LOGIN +
                "?" + PrefParams.EMAIL + "=" + email +
                "&" + PrefParams.PASSWORD + "=" + password;

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return HttpUtils.requestHttpGET(url);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    Log.d(TAG, "onPostExecute: " + result);
                    long customerId = Integer.parseInt(result);
                    if (customerId != 0) {

                        CustomerPresenter.retrieve(context, customerId, new CustomerPresenter.RetrieveCallback() {
                            @Override
                            public void onResponse(Customer customer) {

                                if (customer != null) {

                                    callback.onLoginSuccess(customer);
                                }
                            }

                            @Override
                            public void onConnectionTimeOut() {

                                callback.onConnectionTimeOut();
                            }
                        });
                        return;
                    }
                    callback.onLoginFail();
                    return;
                }
                callback.onConnectionTimeOut();
            }
        }.execute();
    }

    public interface LoginCallback extends WACallback {

        void onLoginSuccess(Customer customer);

        void onLoginFail();
    }
}
