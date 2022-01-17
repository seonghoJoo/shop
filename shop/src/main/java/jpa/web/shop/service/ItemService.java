package jpa.web.shop.service;

import jpa.web.shop.domain.item.Item;
import jpa.web.shop.domain.item.Book;
import jpa.web.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional(readOnly = false)
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    @Transactional
    public Item updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
        // transactional에 의해 flush가 일어나고 바뀐애들을 update 쿼리를 날려버린다.
        return findItem;
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}

