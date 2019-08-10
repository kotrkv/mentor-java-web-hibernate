package DAO;

import model.DailyReport;
import model.SaleCar;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public void create() {
        Transaction transaction = session.beginTransaction();
        List<SaleCar> saleCars = session.createQuery("FROM SaleCar").list();

        long countSaleCars = saleCars.stream().count();
        long sumSaleCars = saleCars.stream().mapToLong(SaleCar::getPrice).sum();

        DailyReport dailyReport = new DailyReport(sumSaleCars, countSaleCars);
        session.saveOrUpdate(dailyReport);
        session.createSQLQuery("DELETE FROM sale_cars").executeUpdate();
        transaction.commit();
        session.close();
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastDailyReport() {
        Transaction transaction = session.beginTransaction();
        //DailyReport dailyReports = session.createQuery("FROM DailyReport ORDER BY id DESC");
        Query query = session.createQuery("SELECT d FROM DailyReport d ORDER BY d.id DESC");
        query.setMaxResults(1);
        final DailyReport dailyReport = (DailyReport) query.uniqueResult();
        transaction.commit();
        session.close();
        return dailyReport;
    }

    public void deleteAll() {
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        session.createQuery("DELETE DailyReport").executeUpdate();
        session.close();
    }
}
