package asia.covisoft.bsandroid.options;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import asia.covisoft.bsandroid.prefs.PrefParams;

/**
 * Created by Leon on 3/1/2017.
 */

public class Options {

    public static Options getOptions(Context context) {

        String options = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PrefParams.OPTIONS, "");
        return new Gson().fromJson(options, Options.class);
    }

    @SerializedName("dark_primary_color")
    @Expose
    private String darkPrimaryColor;
    @SerializedName("primary_color")
    @Expose
    private String primaryColor;
    @SerializedName("accent_color")
    @Expose
    private String accentColor;
    @SerializedName("primary_text_color")
    @Expose
    private String primaryTextColor;
    @SerializedName("secondary_text_color")
    @Expose
    private String secondaryTextColor;
    @SerializedName("header_background_color")
    @Expose
    private String headerBackgroundColor;
    @SerializedName("shop_logo")
    @Expose
    private String shopLogo;
    @SerializedName("shop_avatar")
    @Expose
    private String shopAvatar;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("shop_email")
    @Expose
    private String shopEmail;
    @SerializedName("shop_website")
    @Expose
    private String shopWebsite;
    @SerializedName("shop_phone")
    @Expose
    private String shopPhone;
    @SerializedName("shop_privacy_json_url")
    @Expose
    private String shopPrivacyJsonUrl;
    @SerializedName("shop_aboutus_json_url")
    @Expose
    private String shopAboutusJsonUrl;
    @SerializedName("lost_password_url")
    @Expose
    private String lostPasswordUrl;
    @SerializedName("shop_banners")
    @Expose
    private List<String> shopBanners;

    public String getDarkPrimaryColor() {
        return darkPrimaryColor;
    }

    public void setDarkPrimaryColor(String darkPrimaryColor) {
        this.darkPrimaryColor = darkPrimaryColor;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

    public String getPrimaryTextColor() {
        return primaryTextColor;
    }

    public void setPrimaryTextColor(String primaryTextColor) {
        this.primaryTextColor = primaryTextColor;
    }

    public String getSecondaryTextColor() {
        return secondaryTextColor;
    }

    public void setSecondaryTextColor(String secondaryTextColor) {
        this.secondaryTextColor = secondaryTextColor;
    }

    public String getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    public void setHeaderBackgroundColor(String headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopAvatar() {
        return shopAvatar;
    }

    public void setShopAvatar(String shopAvatar) {
        this.shopAvatar = shopAvatar;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopEmail() {
        return shopEmail;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }

    public String getShopWebsite() {
        return shopWebsite;
    }

    public void setShopWebsite(String shopWebsite) {
        this.shopWebsite = shopWebsite;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopPrivacyJsonUrl() {
        return shopPrivacyJsonUrl;
    }

    public void setShopPrivacyJsonUrl(String shopPrivacyJsonUrl) {
        this.shopPrivacyJsonUrl = shopPrivacyJsonUrl;
    }

    public String getShopAboutusJsonUrl() {
        return shopAboutusJsonUrl;
    }

    public void setShopAboutusJsonUrl(String shopAboutusJsonUrl) {
        this.shopAboutusJsonUrl = shopAboutusJsonUrl;
    }

    public String getLostPasswordUrl() {
        return lostPasswordUrl;
    }

    public void setLostPasswordUrl(String lostPasswordUrl) {
        this.lostPasswordUrl = lostPasswordUrl;
    }

    public List<String> getShopBanners() {
        return shopBanners;
    }

    public void setShopBanners(List<String> shopBanners) {
        this.shopBanners = shopBanners;
    }

}

