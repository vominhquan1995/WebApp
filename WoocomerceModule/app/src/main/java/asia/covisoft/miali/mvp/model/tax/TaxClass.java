
package asia.covisoft.miali.mvp.model.tax;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import asia.covisoft.miali.mvp.model.Links;

public class TaxClass {

    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_links")
    @Expose
    private Links links;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
