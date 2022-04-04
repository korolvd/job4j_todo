package ru.job4j.todo.store;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;

@Repository
@ThreadSafe
public class CategoryDBStore implements DBStore {

    private final SessionFactory sf;

    public CategoryDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Category> findAll() {
        return transaction(session ->
                session.createQuery("from ru.job4j.todo.model.Category").list(), sf);
    }
}
