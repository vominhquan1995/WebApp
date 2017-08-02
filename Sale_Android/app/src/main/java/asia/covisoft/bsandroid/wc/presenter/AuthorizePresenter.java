package asia.covisoft.bsandroid.wc.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import asia.covisoft.bsandroid.wc.view.WCCallback;
import asia.covisoft.bsandroid.prefs.AuthorizeParams;
import asia.covisoft.bsandroid.prefs.PrefParams;

/**
 * Created by Leon on 2/17/2017.
 */

public class AuthorizePresenter {
    private static final String TAG = AuthorizePresenter.class.getSimpleName();

    public static AsyncTask authorize(Context context, final String api, final Callback callback) {

        String authCode = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PrefParams.AUTH_CODE, "");

        if (authCode.equals("")) {
            callback.onConnectionTimeOut();
        } else {
            String wcRequestURL = api + "?" +
                    AuthorizeParams.AUTH_CODE + "=" + authCode;

            return callback.onAuthentication(wcRequestURL, "", "", 0, "", "", "");
        }
        return null;
    }

    public interface Callback extends WCCallback {

        AsyncTask onAuthentication(String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature);
    }
}
