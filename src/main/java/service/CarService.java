package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private CarDao carDao;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
//        this.carDao = new CarDao();
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public void create(Car car) {
        if (new CarDao(sessionFactory.openSession()).getCountOfCars(car.getBrand()) >= 10) {
               throw new RuntimeException("Машин много");
        } else {
            new CarDao(sessionFactory.openSession()).create(car);
        }
    }

    public void sale(String brand, String model, String licensePlate) {
        new CarDao(sessionFactory.openSession()).sale(brand, model, licensePlate);
    }
}
