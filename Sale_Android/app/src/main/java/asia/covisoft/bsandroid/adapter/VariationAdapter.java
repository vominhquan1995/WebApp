package asia.covisoft.bsandroid.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

import asia.covisoft.bsandroid.wc.model.get.product.Variation;

/**
 * Created by Leon on 4/13/2017.
 */

public class VariationAdapter extends ArrayAdapter<Variation> {

    public VariationAdapter(Context context, List<Variation> variations){
        super(context, android.R.layout.simple_spinner_dropdown_item, variations);
    }
}
