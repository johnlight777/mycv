package inspiro;

public class VendingMachineItem {
    private String itemCode;
    private Integer stock;
    private Integer price;

    public VendingMachineItem() {
    }

    public VendingMachineItem(String itemCode, Integer price) {
        this.itemCode = itemCode;
        this.price = price;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void addStock(Integer qty) {
        this.stock += qty;
    }

    public void substractStock(Integer qty) {
        this.stock -= stock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "VendingMachineItem{" +
                "itemCode='" + itemCode + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
