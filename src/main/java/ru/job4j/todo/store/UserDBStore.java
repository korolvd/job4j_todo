package ru.job4j.todo.store;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@ThreadSafe
public class UserDBStore implements DBStore {

    private final SessionFactory sf;

    public UserDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<User> add(User user) {
        try {

            transaction(session -> session.save(user), sf);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmailAndPwd(String email, String password) {
        return transaction(session -> session.createQuery(
                                "from ru.job4j.todo.model.User "
                                        + " where email = :email and password = :password")
                        .setParameter("email", email)
                        .setParameter("password", password).uniqueResultOptional(), sf);
    }
}
