package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Query;
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
        transaction.begin();
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

    public Integer getCountOfCars(String brand) {
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Query query = session.createQuery("FROM Car WHERE brand = :brand");
        query.setParameter("brand", brand);
        Integer count = query.list().size();
        return count;
    }
}
