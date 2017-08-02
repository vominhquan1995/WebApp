package asia.covisoft.bsandroid.wawidget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Leon on 4/5/2017.
 */

public class WATextView extends AppCompatTextView {

    public WATextView(Context context) {
        super(context);
    }

    public WATextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setTextColor(WAView.getOptionsColor(context, getCurrentTextColor()));
    }
}
