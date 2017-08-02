package asia.covisoft.bsandroid.wawidget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import asia.covisoft.bsandroid.options.Options;

/**
 * Created by Leon on 4/5/2017.
 */

public class WAToolbar extends Toolbar {

    public WAToolbar(Context context) {
        super(context);
    }

    public WAToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(Color.parseColor(Options.getOptions(context).getPrimaryColor()));
    }
}
