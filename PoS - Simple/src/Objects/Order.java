package Objects;

import java.sql.Time;

public class Order {
    private String orderNum;
    private String name;
    private float total;
    private String status;
    private Time time;

    public Order() {
        this.name = "-NoName-";
        this.total = 0;
        this.orderNum = null;
        this.time = null;
    }

    public Order(String name, float total, String orderNum, String status, Time time) {
        this.name = name;
        this.total = total;
        this.orderNum = orderNum;
        this.status = status;
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


}
