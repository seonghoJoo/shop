package jpa.web.shop.api;

import jpa.web.shop.domain.Address;
import jpa.web.shop.domain.Order;
import jpa.web.shop.domain.OrderStatus;
import jpa.web.shop.repository.OrderRepository;
import jpa.web.shop.repository.OrderSearch;
import jpa.web.shop.repository.SimpleOrderQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 *      ManyToOne , OneToOne
 *
 *      Order
 *      Order N -> 1 Member
 *      Order 1 -> 1 Delivery
 *
 * */

@RestController
@RequiredArgsConstructor
public class OrderSimpleASpiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        // 무한루프로 빠짐
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for(Order order : all){
                order.getMember().getUsername(); // Lazy 강제 초기화
                order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // Order 2개
        // N+1 -> 1 + 회원N + 배송N
        List<SimpleOrderDto> result = orderRepository.findAllByCriteria(new OrderSearch())
                .stream().map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){
        List<SimpleOrderDto> result = orderRepository.findAllWithMemberDelivery()
                .stream().map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public  List<SimpleOrderQueryDto> orderV4(){
        return orderRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getUsername();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
