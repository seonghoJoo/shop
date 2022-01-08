package jpa.web.shop.dto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "ORDERITEM_ID")
    private List<OrderItem> orderItems;

    @OneToOne

    private Delivery delivery;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

}
