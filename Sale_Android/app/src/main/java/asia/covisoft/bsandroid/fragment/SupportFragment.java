package asia.covisoft.bsandroid.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.activity.InfoActivity;
import asia.covisoft.bsandroid.base.BaseFragment;
import asia.covisoft.bsandroid.options.Options;
import asia.covisoft.bsandroid.utils.EmailSender;
import asia.covisoft.bsandroid.utils.PhoneHelper;
import asia.covisoft.bsandroid.wawidget.WATextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends BaseFragment {

    @Override
    public int getRootView() {
        return R.layout.fragment_support;
    }


    private CardView cvHotline;
    private WATextView tvHotline;
    private CardView cvSendmail;
    private CardView cardCustomerCard;
    private CardView cardShoppingGuide;
    private CardView cardPayments;
    private CardView cardProductReturn;
    private CardView cvPrivacy;
    private CardView cvAboutUs;

    @Override
    public void initView() {

        cvHotline = (CardView) rootView.findViewById(R.id.cvHotline);
        tvHotline = (WATextView) rootView.findViewById(R.id.tvHotline);
        cvSendmail = (CardView) rootView.findViewById(R.id.cvSendmail);
        cvPrivacy = (CardView) rootView.findViewById(R.id.cvPrivacy);
        cvAboutUs = (CardView) rootView.findViewById(R.id.cvAboutUs);

        cvHotline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new PhoneHelper(context).dial(phone);
            }
        });
        cvSendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EmailSender.send(context, new String[]{email}, "Yêu cầu hỗ trợ", "");
            }
        });
        cvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startInfoActivity(privacyApi);
            }
        });
        cvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startInfoActivity(aboutUsApi);
            }
        });
    }

    private String phone;
    private String email;
    private String privacyApi, aboutUsApi;

    @Override
    public void initValue() {

        Options options = Options.getOptions(context);

        phone = options.getShopPhone();
        email = options.getShopEmail();
        privacyApi = options.getShopPrivacyJsonUrl();
        aboutUsApi = options.getShopAboutusJsonUrl();
    }

    @Override
    public void initAction() {

        tvHotline.setText(phone);
    }

    private void startInfoActivity(String infoApi){

        Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra(InfoActivity.INFO_API, infoApi);
        startActivity(intent);
    }
}
