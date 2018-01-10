package Objects;

public class OrderItem {
    private String orderNum;
    private String itemName;
    private int itemQuantity;
    private String serving;
    private String instructions;
    private float subCost;

    public OrderItem() {
        this.orderNum = null;
        this.itemName = "-NoItemSelected-";
        this.itemQuantity = 0;
        this.subCost = 0;
        this.serving = "-NoServingSelected-";
        this.instructions = "-NoInstruction-";
    }

    public OrderItem(String orderNum, String itemName, int itemQuantity, String serving, String instructions, float subCost) {
        this.orderNum = orderNum;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.serving = serving;
        this.instructions = instructions;
        this.subCost = subCost;
    }


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public float getSubCost() {
        return subCost;
    }

    public void setSubCost(float subCost) {
        this.subCost = subCost;
    }

    public String display(){
        return "Item: " + itemName+ " " + "Serving: " +serving+ " " + "Quantity: " +itemQuantity +" " +"SubCost: " +subCost+" " +"Instructions: " +instructions;
    }
}
