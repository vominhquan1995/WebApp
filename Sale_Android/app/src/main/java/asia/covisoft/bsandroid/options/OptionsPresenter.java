package asia.covisoft.bsandroid.options;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import asia.covisoft.bsandroid.prefs.Host;
import asia.covisoft.bsandroid.prefs.PrefParams;
import asia.covisoft.bsandroid.utils.HttpUtils;
import asia.covisoft.bsandroid.utils.MD5;
import asia.covisoft.bsandroid.wc.view.WCCallback;

/**
 * Created by Leon on 3/1/2017.
 *
 */

public class OptionsPresenter {
    private static final String TAG = OptionsPresenter.class.getSimpleName();

    public static void getOptions(final Context context, final Callback callback) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                return HttpUtils.requestHttpGET(Host.GET_OPTIONS);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (result != null) {
                    Log.d(TAG, "onPostExecute: " + result);
                    try {
                        Options options = new Gson().fromJson(result, Options.class);
                        String authCode = MD5.encrypt(options.getDarkPrimaryColor() + options.getPrimaryColor() + options.getAccentColor() + options.getPrimaryTextColor() + options.getSecondaryTextColor() + options.getHeaderBackgroundColor() + Host.AUTH_TOKEN);
                        PreferenceManager.getDefaultSharedPreferences(context).edit()
                                .putString(PrefParams.OPTIONS, result)
                                .putString(PrefParams.AUTH_CODE, authCode)
                                .apply();
                        callback.onOptionsCallback(options);
                        return;
                    } catch (JsonSyntaxException | IllegalStateException ex) {
                        ex.printStackTrace();
                    }
                }
                callback.onConnectionTimeOut();
            }
        }.execute();
    }

    public interface Callback extends WCCallback {

        void onOptionsCallback(Options options);
    }
}
