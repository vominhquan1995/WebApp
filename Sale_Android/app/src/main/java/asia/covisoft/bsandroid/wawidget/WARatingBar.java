package asia.covisoft.bsandroid.wawidget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.AttributeSet;

import asia.covisoft.bsandroid.options.Options;

/**
 * Created by Leon on 4/14/2017.
 */

public class WARatingBar extends AppCompatRatingBar {

    public WARatingBar(Context context) {
        super(context);
    }

    public WARatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        Options options = Options.getOptions(context);
        int accentColor = Color.parseColor(options.getAccentColor());

        LayerDrawable stars = (LayerDrawable) getProgressDrawable();
        stars.getDrawable(2).setColorFilter(accentColor, PorterDuff.Mode.SRC_ATOP);
    }
}
