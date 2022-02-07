package jpa.web.shop;

import jpa.web.shop.domain.Address;
import jpa.web.shop.domain.Member;
import jpa.web.shop.domain.Order;
import jpa.web.shop.domain.OrderItem;
import jpa.web.shop.domain.Delivery;
import jpa.web.shop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
/**
 *      총 주문 2개
 *      userA
 *      JPA1 BOOK
 *      JPA2 BOOK
 *
 *      userB
 *      Spring1 BOOK
 *      Spring2 BOOK
 *
 * */

// spring component scan 대상
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    // 스프링 빈이 모두 엮이고 난 다음
    // PostConstruct가 실행행
    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void dbInit1(){
            Member member = new Member();
            member.setUsername("userA");
            member.setAddress(new Address("서울", "1", "1111"));
            em.persist(member);

            Book book = new Book();
            book.setName("JPA1 BOOK");
            book.setPrice(10000);
            book.setStockQuantity(100);
            em.persist(book);

            Book book2 = new Book();
            book2.setName("JPA2 BOOK");
            book2.setPrice(20000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 =  OrderItem.createOrderItem(book, 10000,1);
            OrderItem orderItem2 =  OrderItem.createOrderItem(book2, 20000,2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
            System.out.println("Init1" + this.getClass()); // 원본 인스턴스
        }

        public void dbInit2(){
            Member member = new Member();
            member.setUsername("userB");
            member.setAddress(new Address("경기", "2", "1111"));
            em.persist(member);

            Book book = new Book();
            book.setName("JPA1 BOOK");
            book.setPrice(10000);
            book.setStockQuantity(100);
            em.persist(book);

            Book book2 = new Book();
            book2.setName("JPA2 BOOK");
            book2.setPrice(20000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 =  OrderItem.createOrderItem(book, 10000,1);
            OrderItem orderItem2 =  OrderItem.createOrderItem(book2, 20000,2);



            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
            System.out.println("Init2" + this.getClass());
        }

    }

}

