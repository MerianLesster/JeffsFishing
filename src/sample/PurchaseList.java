package sample;

public class PurchaseList {
    private String customerName;
    private String code;
    private String productName;
    private String size;
    private int quantity;
    private double cost;


    public PurchaseList(String customerName, String code, String productName, String size, int quantity, double cost) {
        this.customerName = customerName;
        this.code = code;
        this.productName = productName;
        this.size = size;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
