package controller;


import data.OrderRepository;
import entity.Order;
import entity.OrderMatching;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Data
public class OrderManager {

    private OrderRepository orderRepository;

    private List<OrderMatching> ordersMatched;

    public OrderManager() {
        this.orderRepository = OrderRepository.getInstance();
        this.ordersMatched = new ArrayList<>();
    }

    public List<OrderMatching> processOrders(List<String> orderInputData){
        if(orderInputData != null && orderInputData.size() > 0){
            List<Order> ordersFromInput = orderInputData.stream()
                    .map(orderInput -> new Order(orderInput))
                    .collect(Collectors.toList());
            ordersFromInput.forEach(order -> fulfillOrder(order));
        }
        return ordersMatched;
    }

    public void fulfillOrder(Order order){
        TreeSet<Order> buyOrders = orderRepository.getBuyOrders().get(order.getStock());
        TreeSet<Order> sellOrders = orderRepository.getSellOrders().get(order.getStock());

        switch (order.getOrderType()){
            case sell:
                if(buyOrders != null && buyOrders.size() > 0){
                    Iterator<Order> buyOrderIterator = buyOrders.iterator();
                    while(buyOrderIterator.hasNext()){
                        Order buyOrder = buyOrderIterator.next();
                        if(buyOrder.getPrice() >= order.getPrice()){
                            Integer quantity = Math.min(order.getQuantity(), buyOrder.getQuantity());

                            OrderMatching orderMatching = new OrderMatching(
                                    buyOrder.getId(), order.getPrice(), quantity, order.getId());
                            this.ordersMatched.add(orderMatching);

                            //Adjusting the quantity
                            Integer buyQuantityToFulFill = buyOrder.getQuantity() - quantity;
                            if(buyQuantityToFulFill > 0){
                                buyOrder.setQuantity(buyQuantityToFulFill);
                            }else{
                                buyOrderIterator.remove();
                            }

                            Integer sellQuantityToFulFill = order.getQuantity() - quantity;
                            if(sellQuantityToFulFill > 0){
                                order.setQuantity(sellQuantityToFulFill);
                                fulfillOrder(order);
                            }
                            return;
                        }
                    }
                    orderRepository.addOrder(order);
                    break;
                }
                orderRepository.addOrder(order);
                break;
            case buy:
                if(sellOrders != null && sellOrders.size() > 0){
                    Iterator<Order> sellOrderIterator = sellOrders.iterator();
                    while(sellOrderIterator.hasNext()){
                        Order sellOrder = sellOrderIterator.next();
                        if(sellOrder.getPrice() < order.getPrice()){
                            Integer quantity = Math.min(order.getQuantity(), sellOrder.getQuantity());

                            OrderMatching orderMatching = new OrderMatching(
                                    order.getId(), sellOrder.getPrice(), quantity, sellOrder.getId());
                            this.ordersMatched.add(orderMatching);

                            //Adjusting the quantity
                            Integer sellQuantityToFulFill = sellOrder.getQuantity() - quantity;
                            if(sellQuantityToFulFill > 0){
                                sellOrder.setQuantity(sellQuantityToFulFill);
                            }else{
                                sellOrderIterator.remove();
                            }

                            Integer buyQuantityToFulFill = order.getQuantity() - quantity;
                            if(buyQuantityToFulFill > 0){
                                order.setQuantity(buyQuantityToFulFill);
                                fulfillOrder(order);
                            }
                            return;

                        }
                    }
                    orderRepository.addOrder(order);
                    break;
                }
                orderRepository.addOrder(order);
                break;
            default:
                throw new IllegalArgumentException("Invalid order type");

        }
    }

}

