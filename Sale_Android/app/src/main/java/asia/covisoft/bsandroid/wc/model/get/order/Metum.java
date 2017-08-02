
package asia.covisoft.bsandroid.wc.model.get.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metum {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("value")
    @Expose
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
