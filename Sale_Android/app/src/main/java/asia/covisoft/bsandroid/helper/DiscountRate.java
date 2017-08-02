package asia.covisoft.bsandroid.helper;

/**
 * Created by thong on 2/24/2017.
 * PROJECT Sale_Android
 */

public class DiscountRate {
    public static long calculator(long salePrice, long regularPrice) {
        return (long) (100 - (((double)salePrice /(double) regularPrice) * 100));
    }
}
