package asia.covisoft.bsandroid.wawidget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.options.Options;

/**
 * Created by Leon on 4/5/2017.
 */

public class WAView {

    public static int getOptionsColor(Context context, int color) {

        Options options = Options.getOptions(context);
        String colorString = null;

        if (color == ContextCompat.getColor(context, R.color.colorPrimary)) {
            colorString = options.getPrimaryColor();
        } else if (color == ContextCompat.getColor(context, R.color.colorPrimaryDark)) {
            colorString = options.getDarkPrimaryColor();
        } else if (color == ContextCompat.getColor(context, R.color.colorAccent)) {
            colorString = options.getAccentColor();
        } else if (color == ContextCompat.getColor(context, R.color.colorTextPrimary)) {
            colorString = options.getPrimaryTextColor();
        } else if (color == ContextCompat.getColor(context, R.color.colorTextSecondary)) {
            colorString = options.getSecondaryTextColor();
        } else if (color == ContextCompat.getColor(context, R.color.colorBackground)) {
            colorString = options.getHeaderBackgroundColor();
        }

        if (colorString != null) {
            return Color.parseColor(colorString);
        }
        return color;
    }
}
