package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.ItemDBStore;

import java.util.Collection;
import java.util.List;

@Service
public class ItemService {

    private final ItemDBStore store;

    public ItemService(ItemDBStore store) {
        this.store = store;
    }

    public Item add(Item item, List<String> idsCat) {
        return store.add(item, idsCat);
    }

    public void update(Item item) {
        store.update(item);
    }

    public Collection<Item> findAll() {
        return store.findAll();
    }

    public Item findById(int id) {
        return store.findById(id);
    }

    public Collection<Item> findNew() {
        return store.findByConditionDone(false);
    }

    public Collection<Item> findDone() {
        return store.findByConditionDone(true);
    }

    public void deleteById(int id) {
        store.deleteById(id);
    }

    public void doneById(int id) {
        store.doneById(id);
    }
}
