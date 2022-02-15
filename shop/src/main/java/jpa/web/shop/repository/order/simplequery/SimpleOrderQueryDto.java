package jpa.web.shop.repository.order.simplequery;

import jpa.web.shop.domain.Address;
import jpa.web.shop.domain.Order;
import jpa.web.shop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderQueryDto{
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderQueryDto(Long orderId, String username, LocalDateTime orderDate,
                               OrderStatus orderStatus, Address address){
        this.orderId = orderId;
        this.name = username;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
