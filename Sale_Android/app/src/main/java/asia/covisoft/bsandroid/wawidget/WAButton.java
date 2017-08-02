package asia.covisoft.bsandroid.wawidget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * Created by Leon on 4/5/2017.
 */

public class WAButton extends AppCompatButton {

    public WAButton(Context context) {
        super(context);
    }

    public WAButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        try{
            ColorDrawable buttonColor = (ColorDrawable) getBackground();
            int color = buttonColor.getColor();
            setBackgroundColor(WAView.getOptionsColor(context, color));

        }catch (ClassCastException ex){
            ex.printStackTrace();
        }
    }
}
