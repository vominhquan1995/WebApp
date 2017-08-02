
package asia.covisoft.miali.mvp.model.product.attribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import asia.covisoft.miali.mvp.model.Links;

public class ProductAttribute {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("order_by")
    @Expose
    private String orderBy;
    @SerializedName("has_archives")
    @Expose
    private Boolean hasArchives;
    @SerializedName("_links")
    @Expose
    private Links links;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getHasArchives() {
        return hasArchives;
    }

    public void setHasArchives(Boolean hasArchives) {
        this.hasArchives = hasArchives;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}