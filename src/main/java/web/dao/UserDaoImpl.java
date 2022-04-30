package web.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class UserDaoImpl implements UserDao {


    @PersistenceContext
    private EntityManager entityManager;

    //select * fom user
    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    //create- insert table
    @Override
    public void addUser(User user) {
        entityManager.persist(user);

    }

    //updade user
    @Override
    public void updateUser(User user) {
        entityManager.merge(user);

    }

    //delete user
    @Override
    public User removeUser(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
                return user;
    }

    @Override
    public User getUserId(long id) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.id =:user_id", User.class);
        query.setParameter("user_id", id);
        return query.getResultList().stream().findAny().orElse(null);

    }
}
