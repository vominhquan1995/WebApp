package asia.covisoft.bsandroid.mvp.presenter;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import asia.covisoft.bsandroid.mvp.view.InfoCallback;
import asia.covisoft.bsandroid.utils.HttpUtils;

/**
 * Created by Leon on 4/17/2017.
 */

public class InfoPresenter {

    public static void getInfo(final String infoApi, final InfoCallback callback){

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                return HttpUtils.requestHttpGET(infoApi);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(result != null && !result.equals("")){

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String title = jsonObject.optString("title");
                        String content = jsonObject.optString("content");
                        String contentUrl = jsonObject.optString("content_url");
                        callback.onInfoCallback(title, content, contentUrl);
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onConnectionTimeOut();
            }
        }.execute();
    }
}
