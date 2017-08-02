package asia.covisoft.bsandroid.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Project GOXEOM_ANDROID
 * Created by thongph on 10/21/16.
 */

public class NumberFormatter {

    public static String formatCurrency(int number) {
        return formatCurrency((long) number);
    }

    public static String formatCurrency(long number) {
        if (number < 1000) {
            return String.valueOf(number);
        }

        try {
            NumberFormat formatter = new DecimalFormat("###,###");
            String resp = formatter.format(number);
            resp = resp.replace(".", ",");

            return resp;
        } catch (Exception e) {
            return "";
        }
    }

    public static double round(double value, int afterDot) {

        String format = "#";
        if (afterDot > 0) {
            format += ".";
            for (int i = 0; i < afterDot; i++) {
                format += "#";
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat(format);

        return Double.valueOf(decimalFormat.format(value));
    }

    public static String roundOneDigitsAfterDot(double value) {
        NumberFormat formatter = new DecimalFormat("##.#");
        //  String resp = formatter.format(value);
        //resp = resp.replaceAll(",", ".");
        //if the number has two digits after
        // the decimal point.
        return formatter.format(value);
    }
}
