package asia.covisoft.bsandroid.fragment;


import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseFragment;
import asia.covisoft.bsandroid.options.Options;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment {

    @Override
    public int getRootView() {
        return R.layout.fragment_contact;
    }

    private ImageView imgLogo;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvEmail;
    private TextView tvWebsite;

    @Override
    public void initView() {

        imgLogo = (ImageView) rootView.findViewById(R.id.imgLogo);
        tvName = (TextView) rootView.findViewById(R.id.tvName);
        tvPhone = (TextView) rootView.findViewById(R.id.tvPhone);
        tvAddress = (TextView) rootView.findViewById(R.id.tvAddress);
        tvEmail = (TextView) rootView.findViewById(R.id.tvEmail);
        tvWebsite = (TextView) rootView.findViewById(R.id.tvWebsite);
    }

    @Override
    public void initValue() {

    }

    @Override
    public void initAction() {

        Options options = Options.getOptions(context);
        Picasso.with(context)
                .load(options.getShopLogo())
                .into(imgLogo);
        tvName.setText(options.getShopName());
        tvPhone.setText(options.getShopPhone());
        tvAddress.setText(options.getShopAddress());
        tvEmail.setText(options.getShopEmail());
        tvWebsite.setText(options.getShopWebsite());
    }

}
