package jpa.web.shop.service;

import jpa.web.shop.domain.Address;
import jpa.web.shop.domain.Member;
import jpa.web.shop.domain.Order;
import jpa.web.shop.domain.OrderStatus;
import jpa.web.shop.domain.item.Book;
import jpa.web.shop.domain.item.Item;
import jpa.web.shop.exception.NotEnoughStockException;
import jpa.web.shop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        // given
        Member member = createMember();

        Book book = createBook("JPA1", 10000, 10);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        Assertions.assertThat(1).isEqualTo(getOrder.getOrderItems().size());
        Assertions.assertThat(10000*orderCount).isEqualTo(getOrder.getTotalPrice());
        Assertions.assertThat(8).isEqualTo(book.getStockQuantity());
    }

    @Test
    public void 주문취소() throws Exception{
        // given
        Member member = createMember();
        Book item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order order = orderRepository.findOne(orderId);
        Assertions.assertThat(10).isEqualTo(item.getStockQuantity());
        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(order.getStatus());
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);
        int orderCount = 11;

        // when
        Assertions.assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount));

        // then

    }

    private Book createBook(String name, int price, int stockQuntity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuntity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);
        return member;
    }


}
