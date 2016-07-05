package tomerbu.edu.shppinglistfirebase.models;


public class ShoppingListItem extends BaseModel{
    private String title;
    private String quantity;
    private String owner;

    public ShoppingListItem() {
    }

    public ShoppingListItem(String title, String quantity, String owner) {
        this.title = title;
        this.quantity = quantity;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
