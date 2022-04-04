package ru.job4j.todo.store;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import java.util.Collection;
import java.util.List;

@Repository
@ThreadSafe
public class ItemDBStore implements DBStore {

    private final SessionFactory sf;

    public ItemDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item, List<String> idsCat) {
        transaction(session -> {
            for (String id : idsCat) {
                Category category = session.find(Category.class, Integer.parseInt(id));
                item.addCategory(category);
            }
            return session.save(item);
        }, sf);
        return item;
    }

    public void update(Item item) {
        transaction(session -> {
            session.update(item);
            return new Object();
        }, sf);
    }

    public List<Item> findAll() {
        return transaction(session -> session.createQuery(
                "from ru.job4j.todo.model.Item").list(), sf);
    }

    public Item findById(int id) {
        return transaction(session -> session.get(Item.class, id), sf);
    }

    public Collection<Item> findByConditionDone(boolean condition) {
        return transaction(session -> session.createQuery(
                        "from ru.job4j.todo.model.Item where done = :condition")
                .setBoolean("condition", condition).list(), sf);
    }

    public void deleteById(int id) {
        Item item = new Item();
        item.setId(id);
        transaction(session -> {
                    session.delete(item);
                    return new Object();
                }, sf);
    }

    public void doneById(int id) {
        transaction(session -> session.createQuery(
                        "update ru.job4j.todo.model.Item i set i.done = :done where i.id = :id")
                .setParameter("done", true)
                .setParameter("id", id)
                .executeUpdate(), sf);
    }
}
