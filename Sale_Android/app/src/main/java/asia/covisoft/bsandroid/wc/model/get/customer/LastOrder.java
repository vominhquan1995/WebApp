
package asia.covisoft.bsandroid.wc.model.get.customer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastOrder {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("date")
    @Expose
    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
