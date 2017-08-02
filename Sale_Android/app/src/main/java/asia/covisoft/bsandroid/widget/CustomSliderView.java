package asia.covisoft.bsandroid.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import asia.covisoft.bsandroid.R;

/**
 * Project BS_ANDROID
 * Created by thongph on 5/25/16.
 */
public class CustomSliderView extends BaseSliderView {

    private Context mContext;

    public CustomSliderView(Context context) {
        super(context);

        mContext = context;
    }

    @Override
    public View getView() {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(mContext).inflate(R.layout.custom_slider_view, null);
        ImageView sliderImage = (ImageView) v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(getDescription());
        bindEventAndShow(v, sliderImage);

        return v;
    }
}
