package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public void create(Car car) {
        Transaction transaction = session.getTransaction();
        session.saveOrUpdate(car);
        transaction.commit();
    }

    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return cars;
    }

    public Long getCountOfCars(String brand) {
        return 0L;
    }
}
