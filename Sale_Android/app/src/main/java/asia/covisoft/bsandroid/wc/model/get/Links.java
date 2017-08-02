
package asia.covisoft.bsandroid.wc.model.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Links implements Serializable{

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
