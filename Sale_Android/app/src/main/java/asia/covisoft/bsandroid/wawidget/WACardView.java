package asia.covisoft.bsandroid.wawidget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import asia.covisoft.bsandroid.options.Options;

/**
 * Created by Leon on 4/14/2017.
 */

public class WACardView extends CardView{


    public WACardView(Context context) {
        super(context);
    }

    public WACardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Options options = Options.getOptions(context);
        int accentColor = Color.parseColor(options.getAccentColor());

        setBackgroundColor(accentColor);
    }
}
