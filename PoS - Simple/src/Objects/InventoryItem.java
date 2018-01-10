package Objects;

import java.io.Serializable;

public class InventoryItem implements Serializable {
    private String itemName;
    private float itemPrice;
    private int quantity;

    public InventoryItem() {
        this.itemName = null;
        this.itemPrice = 0;
        this.quantity = 0;
    }
    public InventoryItem(String itemName, float itemPrice, int quantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
