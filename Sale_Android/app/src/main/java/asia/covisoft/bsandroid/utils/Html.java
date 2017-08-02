package asia.covisoft.bsandroid.utils;

import android.os.Build;
import android.text.Spanned;

/**
 * Created by Leon on 4/7/2017.
 */

public class Html {

    public static Spanned fromHtml(String source) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return android.text.Html.fromHtml(source, android.text.Html.FROM_HTML_MODE_COMPACT);
        }
        return android.text.Html.fromHtml(source);
    }
}
