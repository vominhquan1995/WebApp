package asia.covisoft.bsandroid.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Leon on 5/15/2017.
 */

public class ColumnCalculator {

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static int calculate(Context context, int columnWidth) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / columnWidth);
        return noOfColumns;
    }
}
