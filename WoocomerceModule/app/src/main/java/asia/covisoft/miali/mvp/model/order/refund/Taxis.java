
package asia.covisoft.miali.mvp.model.order.refund;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taxis {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

}
