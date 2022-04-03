package ru.job4j.todo.store;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.function.Function;

@Repository
@ThreadSafe
public class CategoryDBStore {

    private final SessionFactory sf;

    public CategoryDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Category> findAll() {
        return transaction(session ->
                session.createQuery("from ru.job4j.todo.model.Category").list());
    }

    private <T> T transaction(final Function<Session, T> command) {
        final Session session = sf.openSession();
        session.beginTransaction();
        T rsl = command.apply(session);
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public Category getById(int id) {
        return null;
    }
}
