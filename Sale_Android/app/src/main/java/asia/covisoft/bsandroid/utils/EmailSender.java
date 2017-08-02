package asia.covisoft.bsandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Leon on 4/14/2017.
 */

public class EmailSender {

    public static void send(Context context, String[] emails, String subject, String content ){

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , emails);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT   , content);
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
