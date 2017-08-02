
package asia.covisoft.miali.mvp.model.order.note;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import asia.covisoft.miali.mvp.model.Links;

public class OrderNote {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("customer_note")
    @Expose
    private Boolean customerNote;
    @SerializedName("_links")
    @Expose
    private Links links;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(Boolean customerNote) {
        this.customerNote = customerNote;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
