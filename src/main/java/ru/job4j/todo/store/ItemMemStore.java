package ru.job4j.todo.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

@Repository
@ThreadSafe
public class ItemMemStore {

    private final Map<Integer, Item> items = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger(1);

    public Item add(Item item) {
        item.setId(ids.getAndIncrement());
        return items.putIfAbsent(item.getId(), item) == null ? item : null;
    }

    public void update(Item item) {
        items.replace(item.getId(), item);
    }

    public Collection<Item> findAll() {
        return items.values();
    }

    public Item findById(int id) {
        return items.get(id);
    }

    public Collection<Item> findByPredicate(Predicate<Item> predicate) {
        Collection<Item> rsl = new ArrayList<>();
        for (Item item : items.values()) {
            if (predicate.test(item)) {
                rsl.add(item);
            }
        }
        return rsl;
    }

    public void deleteById(int id) {
        items.remove(id);
    }
}
