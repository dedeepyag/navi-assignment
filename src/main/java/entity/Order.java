package entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

@Data
public class Order {

    private String id;

    private Long timestamp;

    private String stock;

    private OrderType orderType;

    private Double price;

    private Integer quantity;

    public Order(String id, Long timestamp, String stock, OrderType orderType, Double price, Integer quantity) {
        this.id = id;
        this.timestamp = timestamp;
        this.stock = stock;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
    }

    public Order(String input){
        if(input != null){
            try{
                String[] inputParams = input.split(" ");
                if(inputParams != null){
                    this.id = inputParams[0];

                    LocalTime localTime = LocalTime.parse(inputParams[1]);
                    LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
                    this.timestamp = localDateTime.atZone
                        (ZoneId.systemDefault()).toInstant().toEpochMilli();

                    this.stock = inputParams[2];

                    OrderType orderType = OrderType.valueOf(inputParams[3]);
                    this.orderType = orderType;

                    Double price = Double.parseDouble(inputParams[4]);
                    this.price = price;

                    Integer quantity = Integer.parseInt(inputParams[5]);
                    this.quantity = quantity;
                }
            }catch ( DateTimeParseException | IndexOutOfBoundsException | IllegalArgumentException  e){
                throw new IllegalArgumentException("Input not provided in the valid format");
            }

        }else{
            throw new IllegalArgumentException("Invalid input obtained");
        }
    }


}

