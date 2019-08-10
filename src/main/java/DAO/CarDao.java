package DAO;

import model.Car;
import model.SaleCar;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public void create(Car car) {
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.saveOrUpdate(car);
        transaction.commit();
    }

    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createCriteria(Car.class).list();
        transaction.commit();
        return cars;
    }

    public Integer getCountOfCars(String brand) {
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Integer count = session.createCriteria(Car.class)
                .add(Restrictions.eq("brand", brand)).list().size();
        transaction.commit();
        return count;
    }

    public void sale(String brand, String model, String licensePlate) {
        Transaction transaction = session.getTransaction();
        transaction.begin();

        Criteria criteria = session.createCriteria(Car.class)
                .add(Restrictions.eq("brand", brand))
                .add(Restrictions.eq("model", model))
                .add(Restrictions.eq("licensePlate", licensePlate));

        final Car car = (Car) criteria.uniqueResult();

        SaleCar saleCar = new SaleCar(car.getBrand()
                , car.getModel()
                , car.getLicensePlate()
                , car.getPrice());
        session.saveOrUpdate(saleCar);
        session.delete(car);
        transaction.commit();
        session.close();
    }

    public void deleteAll() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE Car").executeUpdate();
        transaction.commit();
        session.close();
    }
}
