package controller;

import entity.OrderMatching;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Driver {

    public static void main(String[] args){
        try{
            File file = new File(args[0]);
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(file));
            List<String> orderInputData = bufferedReader.lines().collect(Collectors.toList());

            OrderManager orderManager = new OrderManager();
            List<OrderMatching> ordersMatched = orderManager.processOrders(orderInputData);

            System.out.println("Output after matching the orders in the below format");
            System.out.println("<buy-order-id> <sell-price> <qty> <sell-order-id>");
            System.out.println("--------------------------------------------");
            ordersMatched.forEach(orderMatched -> System.out.println(orderMatched.toString()));
            System.out.println("--------------------------------------------");

        }catch (Exception e){
            System.out.println("Stock Exchange System - Error occurred: " + e.getMessage());
        }

    }
}

