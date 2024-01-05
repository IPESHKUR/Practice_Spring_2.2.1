package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserCar(String model, int series) {
        TypedQuery<Car> getCarsFromUser = sessionFactory.getCurrentSession()
                .createQuery("from Car s where s.model = :model and s.series = :series");
        getCarsFromUser.setParameter("model", model);
        getCarsFromUser.setParameter("series", series);
        List<Car> allCars = getCarsFromUser.getResultList();
        if (!allCars.isEmpty()) {
            Car getCar = allCars.get(0);
            List<User> users = listUsers();
            User carUser = users.stream()
                    .filter(user -> user.getUserCar().equals(getCar))
                    .findAny()
                    .orElse(null);
            return carUser;
        }

        return null;
    }
}
