package asia.covisoft.bsandroid.utils;

import android.util.Patterns;
import java.util.regex.Pattern;

/**
 * Created by thong on 2/21/2017. PROJECT BS_ANDROID
 */

public class ValidateUtils {

    private static final String FULLNAME_PATTERN = "^[a-zA-Z\\s]+";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9\\s]+";
    private static final String EMAIL_PATTERN
        = "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@([^web])([a-zA-Z0-9-])+(\\.[a-zA-Z0-9-]+)*\\.(([0-9]{1,3})|([a-zA-Z]{2,5})|(aero|coop|info|museum|name))$";

    /**
     * Valid fullName
     */
    public static boolean isFullNameValid(String fullName) {
        return Pattern.matches(FULLNAME_PATTERN, fullName);
    }

    /**
     * Valid email
     */
    public static boolean isEmailValid(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    /**
     * Valid phone number
     */
    public static boolean isPhoneValid(String phoneNumber) {
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    /**
     * Valid password
     */
    public static boolean isPasswordValid(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }
}
