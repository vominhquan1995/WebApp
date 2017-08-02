package asia.covisoft.bsandroid.mvp.view;

/**
 * Created by Leon on 4/17/2017.
 */

public interface InfoCallback extends ConnectionCallback{

    void onInfoCallback(String title, String content, String contentUrl);
}
