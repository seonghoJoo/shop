package jpa.web.shop.repository.order.query;

import jpa.web.shop.domain.Address;
import jpa.web.shop.domain.OrderStatus;
import lombok.Data;

import java.util.*;
import java.time.LocalDateTime;

@Data
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
