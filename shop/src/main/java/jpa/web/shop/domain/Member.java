package jpa.web.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String username;

    @Embedded
    private Address address;

    //order 테이블에 있는 member 필드에 의해 맵핑된거라고 알려줌
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
