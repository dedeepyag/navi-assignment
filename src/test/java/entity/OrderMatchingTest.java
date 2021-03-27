package entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderMatchingTest {

    @Test
    public void printOrderMatching(){
        OrderMatching orderMatching = new OrderMatching("#1", 23.9D, 10, "#2");
        System.out.println(orderMatching.toString());
        Assertions.assertTrue(orderMatching.toString().contains("23.90"));
    }

}