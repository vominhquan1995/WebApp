
package asia.covisoft.miali.mvp.model.order.refund;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import asia.covisoft.miali.mvp.model.Collection;
import asia.covisoft.miali.mvp.model.Self;

public class Links {

    @SerializedName("self")
    @Expose
    private List<Self> self;
    @SerializedName("collection")
    @Expose
    private List<Collection> collection;
    @SerializedName("up")
    @Expose
    private List<Up> up;

    public List<Self> getSelf() {
        return self;
    }

    public void setSelf(List<Self> self) {
        this.self = self;
    }

    public List<Collection> getCollection() {
        return collection;
    }

    public void setCollection(List<Collection> collection) {
        this.collection = collection;
    }

    public List<Up> getUp() {
        return up;
    }

    public void setUp(List<Up> up) {
        this.up = up;
    }
}
