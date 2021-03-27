package entity;

import lombok.Data;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                String[] inputParamsArray = input.split(" ");
                List<String> inputParams = Arrays.asList(inputParamsArray).stream()
                        .filter(inputParam -> (inputParam != null && inputParam.length() > 0))
                        .collect(Collectors.toList());

                if(inputParams != null){
                    this.id = inputParams.get(0);

                    LocalTime localTime = LocalTime.parse(inputParams.get(1));
                    LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
                    this.timestamp = localDateTime.atZone
                        (ZoneId.systemDefault()).toInstant().toEpochMilli();

                    this.stock = inputParams.get(2);

                    OrderType orderType = OrderType.valueOf(inputParams.get(3));
                    this.orderType = orderType;

                    Double price = Double.parseDouble(inputParams.get(4));
                    this.price = price;

                    Integer quantity = Integer.parseInt(inputParams.get(5));
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

