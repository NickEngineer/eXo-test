package ua.nick.eXoPlatform.testTask.Servlets.BuyServlets;

import ua.nick.eXoPlatform.testTask.FileHandlers.OrderBuilder;
import ua.nick.eXoPlatform.testTask.Model.Product;
import ua.nick.eXoPlatform.testTask.Model.ProductCatalog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "BuyServiceServlet", urlPatterns = "/buyservice")
public class BuyServiceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String itemsString = request.getParameter("selectedGoods");

            if (itemsString.length() > 0) {
                Map<String, Product> productCatalog = ProductCatalog.getCatalog(this.getServletContext());
                String[] items = itemsString.split(",");

                StringBuilder orderBuilder = new StringBuilder(itemsString.length() * 7);
                Product product;

                for (String item : items) {
                    product = productCatalog.get(item);

                    if (product != null) {
                        orderBuilder.append(product.getName());
                        orderBuilder.append(",");
                        orderBuilder.append(product.getVendorCode());
                        orderBuilder.append(",");
                        orderBuilder.append(product.getPrice());
                        orderBuilder.append("\n");
                    } else {
                        response.setStatus(400);
                        return;
                    }
                }

                boolean addOrderStatus = false;

                OrderBuilder order = new OrderBuilder(orderBuilder.toString());

                addOrderStatus = order.saveOrder(this.getServletContext());

                if (addOrderStatus) {
                    response.setStatus(201);
                }
            } else {
                response.setStatus(400);
            }
        } catch (Exception ex) {
            response.setStatus(500);
        }
    }
}
