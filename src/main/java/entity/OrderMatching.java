package entity;

import lombok.Data;

@Data
public class OrderMatching {

    private String buyOrderId;

    private Double price;

    private Integer quantity;

    private String sellOrderId;

    public OrderMatching(String buyOrderId, Double price, Integer quantity, String sellOrderId) {
        this.buyOrderId = buyOrderId;
        this.price = price;
        this.quantity = quantity;
        this.sellOrderId = sellOrderId;
    }

    @Override
    public String toString() {
        return String.format("{0:0.00}", buyOrderId) + " " + price + " " + quantity + " " +  String.format("{0:0.00}", buyOrderId) ;
    }
}

