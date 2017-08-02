
package asia.covisoft.miali.mvp.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxLine {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("rate_code")
    @Expose
    private String rateCode;
    @SerializedName("rate_id")
    @Expose
    private String rateId;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("compound")
    @Expose
    private Boolean compound;
    @SerializedName("tax_total")
    @Expose
    private String taxTotal;
    @SerializedName("shipping_tax_total")
    @Expose
    private String shippingTaxTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getCompound() {
        return compound;
    }

    public void setCompound(Boolean compound) {
        this.compound = compound;
    }

    public String getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(String taxTotal) {
        this.taxTotal = taxTotal;
    }

    public String getShippingTaxTotal() {
        return shippingTaxTotal;
    }

    public void setShippingTaxTotal(String shippingTaxTotal) {
        this.shippingTaxTotal = shippingTaxTotal;
    }

}
