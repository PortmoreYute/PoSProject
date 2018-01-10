package Objects;


public class ServingItem {
    private String name;
    private Float price;

    public ServingItem() {
        this.name = null;
        this.price = null;
    }

    public ServingItem(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
