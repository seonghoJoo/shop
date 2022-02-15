package jpa.web.shop.api;


import jpa.web.shop.domain.Address;
import jpa.web.shop.domain.Order;
import jpa.web.shop.domain.OrderItem;
import jpa.web.shop.domain.OrderStatus;
import jpa.web.shop.repository.OrderRepository;
import jpa.web.shop.repository.OrderSearch;
import jpa.web.shop.repository.order.query.OrderQueryDto;
import jpa.web.shop.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for(Order order : all){
            order.getMember().getUsername();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o-> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2(){
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<OrderDto> collectors = orders.stream().map(o-> new OrderDto(o))
                .collect(Collectors.toList());
        return collectors;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream().map(o-> new OrderDto(o)).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value="limit" , defaultValue = "100") int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);
        List<OrderDto> result = orders.stream().map(o-> new OrderDto(o)).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> orderV4(){
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> orderV5(){
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> orderV6(){
        return orderQueryRepository.findAllByDto_flat();
    }

    @Data
    static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order o){
            orderId = o.getId();
            name = o.getMember().getUsername();
            orderDate = o.getOrderDate();
            orderStatus = o.getStatus();
            address = o.getDelivery().getAddress();

            orderItems = o.getOrderItems()
                    .stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    static class OrderItemDto{

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem){
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
