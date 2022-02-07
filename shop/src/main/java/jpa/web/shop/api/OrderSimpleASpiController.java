package jpa.web.shop.api;

import jpa.web.shop.domain.Order;
import jpa.web.shop.repository.OrderRepository;
import jpa.web.shop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
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


}
