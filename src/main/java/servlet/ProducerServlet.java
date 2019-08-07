package servlet;

import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String brand = req.getParameter("brand");
            String model = req.getParameter("model");
            String licensePlate = req.getParameter("licensePlate");
            Long price = Long.parseLong(req.getParameter("price"));
            CarService.getInstance().create(new Car(brand, model, licensePlate, price));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
