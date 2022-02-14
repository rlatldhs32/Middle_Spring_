package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item = new Item("itemA",10000,10);
        //when
        Item saveItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }
    @Test
    void findAll(){
        //given
        Item item1 = new Item("itemA",10000,10);
        Item item2 = new Item("itemB",2200,20);

        itemRepository.save(item1);
        itemRepository.save(item2);
        //when
        List<Item> all = itemRepository.findAll();

        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(item1,item2);
    }
    @Test
    void updateItem(){
        //given
        Item item1 = new Item("itemA",10000,10);
        Item item2 = new Item("itemB",2200,20);
        Item savedItem = itemRepository.save(item1);
        itemRepository.save(item2);
        Long itemId = savedItem.getId();
        //when

        Item updateItem = new Item("itemNew",200,30);
        itemRepository.update(itemId,updateItem);
        //then
        Item result = itemRepository.findById(itemId);
        assertThat(result.getItemName()).isEqualTo(updateItem.getItemName());
        assertThat(result.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(result.getQuantity()).isEqualTo(updateItem.getQuantity());


    }
}