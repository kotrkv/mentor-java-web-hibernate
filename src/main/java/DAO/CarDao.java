package DAO;

import model.Car;
import model.DailyReport;
import model.SaleCar;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Optional;

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
        session.close();
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

    public void sale(String brand, String model, String licensePlate) {
        Transaction transaction = session.getTransaction();
        transaction.begin();

        Criteria criteria = session.createCriteria(Car.class)
                .add(Restrictions.eq("brand", brand))
                .add(Restrictions.eq("model", model))
                .add(Restrictions.eq("licensePlate", licensePlate));

        List<Car> cars = criteria.list();
        Optional<Car> optionalCar = cars.stream()
                .findAny();

        if (optionalCar.isPresent()) {
            SaleCar saleCar = new SaleCar(optionalCar.get().getBrand()
                                            , optionalCar.get().getModel()
                                            , optionalCar.get().getLicensePlate()
                                            , optionalCar.get().getPrice());
            session.saveOrUpdate(saleCar);
            session.delete(optionalCar.get());
            transaction.commit();
            session.close();
        } else {
            transaction.rollback();
            session.close();
        }
    }
}
