package jpa.web.shop.repository;

import jpa.web.shop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            // 완전 새로 생성한 객체
            em.persist(item);
        }else{
            // 준영속 상태의 엔티티를  영속 상태로 변경할때 사용하는 기능

            // 1.  1차 캐시에서 엔티티 조회
            // 2.  1.에서 없다면 DB에서 조회해 1차 캐시에 저장한다.
            // 3.  조회한 영속 엔티티에 member 엔티티의 값을 채워넣는다.
            // 4.  영속상태
            // update
            Item merged = em.merge(item);
            // merged   : 영속성 컨텍스트
            // item     : 그냥 item 객체
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
