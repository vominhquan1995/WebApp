package asia.covisoft.bsandroid.wawidget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import asia.covisoft.bsandroid.options.Options;

/**
 * Created by Leon on 4/13/2017.
 */

public class WAHeaderLayout extends FrameLayout {

    public WAHeaderLayout(Context context) {
        super(context);
    }

    public WAHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        Options options = Options.getOptions(context);

        setBackgroundColor(Color.parseColor(options.getPrimaryColor()));
    }
}
