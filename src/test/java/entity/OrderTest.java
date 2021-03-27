package entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    public void orderIllegalOrderType(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("#1 09:45 BAC buy1 240.12 100");
        });
    }

    @Test
    public void orderIllegalTime(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("#1 255:45 BAC buy 240.12 100");
        });
    }

}