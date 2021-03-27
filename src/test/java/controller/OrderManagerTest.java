package controller;

import entity.Order;
import entity.OrderType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OrderManagerTest {

    OrderManager orderManager;

    @BeforeEach
    void setUp() {
        orderManager = new OrderManager();
        orderManager.getOrderRepository().reInitialize();
    }

    @Test
    public void testOrderFulfilling1(){
        Order sellOrder1 = new Order("123", 12345L, "ABC", OrderType.sell, 10.00, 10);
        Order buyOrder1 = new Order("124", 12345L, "ABC", OrderType.buy, 10.00, 10);
        List<Order> orders = new ArrayList<>();
        orders.add(buyOrder1);
        orders.add(sellOrder1);
        for(Order order: orders){
            orderManager.fulfillOrder(order);
        }
        Assertions.assertEquals(1, orderManager.getOrdersMatched().size());
    }

    @Test
    public void testOrderFulfilling2(){
        Order sellOrder1 = new Order("123", 12345L, "ABC", OrderType.sell, 10.00, 10);
        Order buyOrder1 = new Order("124", 12345L, "ABC", OrderType.buy, 11.00, 10);
        List<Order> orders = new ArrayList<>();
        orders.add(buyOrder1);
        orders.add(sellOrder1);
        for(Order order: orders){
            orderManager.fulfillOrder(order);
        }
        Assertions.assertEquals(new Double(10.00), orderManager.getOrdersMatched().get(0).getPrice());
    }

    @Test
    public void testOrderFulfilling3(){
        Order sellOrder1 = new Order("123", 12345L, "ABC", OrderType.sell, 10.00, 10);
        Order buyOrder1 = new Order("124", 12345L, "ABC", OrderType.buy, 11.00, 10);
        Order buyOrder2 = new Order("125", 12344L, "ABC", OrderType.buy, 11.00, 10);
        List<Order> orders = new ArrayList<>();
        orders.add(buyOrder1);
        orders.add(buyOrder2);
        orders.add(sellOrder1);
        for(Order order: orders){
            orderManager.fulfillOrder(order);
        }
        Assertions.assertEquals("125", orderManager.getOrdersMatched().get(0).getBuyOrderId());
    }

    @Test
    public void testOrderFulfilling4(){
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("#1", 12345L, "ABC", OrderType.sell, 240.12, 100));
        orders.add(new Order("#2", 12346L, "ABC", OrderType.sell, 237.45, 90));
        orders.add(new Order("#3", 12347L, "ABC", OrderType.buy, 238.10, 110));
        orders.add(new Order("#4", 12348L, "ABC", OrderType.buy, 237.80, 10));
        orders.add(new Order("#5", 12349L, "ABC", OrderType.buy, 237.80, 40));
        orders.add(new Order("#6", 12350L, "ABC", OrderType.sell, 236.00, 50));


        for(Order order: orders){
            orderManager.fulfillOrder(order);
        }
        Assertions.assertEquals(4, orderManager.getOrdersMatched().size());
    }

    @Test
    public void testOrderFulfilling5(){
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("1L", 12345L, "ABC", OrderType.sell, 240.12, 100));
        orders.add(new Order("2L", 12346L, "ABC", OrderType.sell, 237.45, 90));
        orders.add(new Order("3L", 12347L, "ABC", OrderType.buy, 238.10, 110));
        orders.add(new Order("4L", 12348L, "ABC", OrderType.buy, 237.80, 10));
        orders.add(new Order("5L", 12349L, "ABC", OrderType.buy, 237.80, 40));
        orders.add(new Order("6L", 12350L, "BAC", OrderType.sell, 236.00, 50));


        for(Order order: orders){
            orderManager.fulfillOrder(order);
        }
        Assertions.assertEquals(1, orderManager.getOrdersMatched().size());
    }

    @Test
    public void processOrdersFromStringInput(){
        List<String> orderInputList = new ArrayList<>();
        orderInputList.add("#1 09:45 BAC sell 240.12 100");
        orderInputList.add("#2 09:46 BAC sell 237.45 90");
        orderInputList.add("#3 09:47 BAC buy 238.10 110");
        orderInputList.add("#4 09:48 BAC buy 237.80 10");
        orderInputList.add("#5 09:49 BAC buy 237.80 40");
        orderInputList.add("#6 09:50 BAC sell 236.00 50");
        orderManager.processOrders(orderInputList);
        Assertions.assertEquals(4, orderManager.getOrdersMatched().size());
    }

}