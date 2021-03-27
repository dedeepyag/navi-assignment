package data;

import entity.Order;
import lombok.Data;

import java.util.*;

@Data
public class OrderRepository {

    private Map<String, TreeSet<Order>> buyOrders;

    private Map<String, TreeSet<Order>> sellOrders;

    private static OrderRepository orderRepository;

    private OrderRepository() {
        this.buyOrders = new HashMap<>();
        this.sellOrders = new HashMap<>();
    }

    public static OrderRepository getInstance(){
        if(orderRepository == null){
            synchronized (OrderRepository.class){
                if(orderRepository == null){
                    orderRepository = new OrderRepository();
                }
            }
        }
        return orderRepository;
    }

    public void reInitialize(){
        this.buyOrders = new HashMap<>();
        this.sellOrders = new HashMap<>();
    }

    public void addOrder(Order order){
        if(order != null){
            Map<String, TreeSet<Order>> ordersByType;
            switch (order.getOrderType()){
                case sell:
                    ordersByType = sellOrders;
                    break;
                case buy:
                    ordersByType = buyOrders;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid order type");
            }
            if(ordersByType.get(order.getStock()) == null){
                ordersByType.put(order.getStock(), new TreeSet<Order>(Comparator.comparing(Order::getTimestamp)));
            }
            ordersByType.get(order.getStock()).add(order);
        }

    }
}

