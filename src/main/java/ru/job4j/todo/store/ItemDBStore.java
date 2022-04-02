package ru.job4j.todo.store;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Repository
@ThreadSafe
public class ItemDBStore {

    private final SessionFactory sf;

    public ItemDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item) {
        transaction(session -> session.save(item));
        return item;
    }

    public void update(Item item) {
        transaction(session -> {
            session.update(item);
            return new Object();
        });
    }

    public List<Item> findAll() {
        return transaction(session -> session.createQuery("from ru.job4j.todo.model.Item").list());
    }

    public Item findById(int id) {
        return transaction(session -> session.get(Item.class, id));
    }

    public Collection<Item> findByConditionDone(boolean condition) {
        return transaction(session -> session.createQuery(
                        "from ru.job4j.todo.model.Item where done = :condition")
                .setBoolean("condition", condition).list());
    }

    public void deleteById(int id) {
        Item item = new Item();
        item.setId(id);
        transaction(session -> {
                    session.delete(item);
                    return new Object();
                }
        );
    }

    private <T> T transaction(final Function<Session, T> command) {
        final Session session = sf.openSession();
        session.beginTransaction();
        T rsl = command.apply(session);
        session.getTransaction().commit();
        session.close();
        return rsl;
    }
}
