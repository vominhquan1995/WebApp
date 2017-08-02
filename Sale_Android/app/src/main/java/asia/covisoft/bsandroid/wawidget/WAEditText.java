package asia.covisoft.bsandroid.wawidget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import asia.covisoft.bsandroid.options.Options;

/**
 * Created by Leon on 4/14/2017.
 */

public class WAEditText extends AppCompatEditText {

    public WAEditText(Context context) {
        super(context);
    }

    public WAEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        Options options = Options.getOptions(context);
        int checkColor = Color.parseColor(options.getPrimaryColor());
        int uncheckColor = Color.parseColor(options.getPrimaryTextColor());

        // FOR NAVIGATION VIEW ITEM TEXT COLOR
        int[][] state = new int[][]{
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_checked}, // checked
                new int[]{android.R.attr.state_pressed},  // pressed
                new int[]{android.R.attr.state_selected},
        };

        int[] color = new int[]{
                uncheckColor,
                checkColor,
                checkColor,
                checkColor
        };

        ColorStateList textColorStateList = new ColorStateList(state, color);
    }
}
