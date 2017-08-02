
package asia.covisoft.miali.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import asia.covisoft.miali.mvp.model.order.refund.Up;

public class Links {

    @SerializedName("self")
    @Expose
    private List<Self> self;
    @SerializedName("collection")
    @Expose
    private List<Collection> collection;

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
}
