package asia.covisoft.bsandroid.wawidget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;

import asia.covisoft.bsandroid.options.Options;

/**
 * Created by Leon on 4/13/2017.
 */

public class WANavigationView extends NavigationView {

    public WANavigationView(Context context) {
        super(context);
    }

    public WANavigationView(Context context, AttributeSet attrs) {
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


        // FOR NAVIGATION VIEW ITEM ICON COLOR
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_checked}, // checked
                new int[]{android.R.attr.state_pressed}, // pressed
                new int[]{android.R.attr.state_selected}
        };

        int[] colors = new int[]{
                uncheckColor,
                checkColor,
                checkColor,
                checkColor
        };

        ColorStateList iconColorStateList = new ColorStateList(states, colors);

        setItemTextColor(textColorStateList);
        setItemIconTintList(iconColorStateList);
    }
}
