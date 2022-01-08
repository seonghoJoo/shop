package jpa.web.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @OneToOne
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;//READY COMP

}
