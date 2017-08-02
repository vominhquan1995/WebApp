
package asia.covisoft.bsandroid.wc.model.get.tax;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import asia.covisoft.bsandroid.wc.model.get.Links;

public class TaxRate {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("priority")
    @Expose
    private Long priority;
    @SerializedName("compound")
    @Expose
    private Boolean compound;
    @SerializedName("shipping")
    @Expose
    private Boolean shipping;
    @SerializedName("order")
    @Expose
    private Long order;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("_links")
    @Expose
    private Links links;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Boolean getCompound() {
        return compound;
    }

    public void setCompound(Boolean compound) {
        this.compound = compound;
    }

    public Boolean getShipping() {
        return shipping;
    }

    public void setShipping(Boolean shipping) {
        this.shipping = shipping;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
