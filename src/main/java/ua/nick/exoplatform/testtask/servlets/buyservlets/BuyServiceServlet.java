package ua.nick.exoplatform.testtask.servlets.buyservlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nick.exoplatform.testtask.filehandlers.OrderBuilder;
import ua.nick.exoplatform.testtask.model.Product;
import ua.nick.exoplatform.testtask.model.ProductCatalog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "BuyServiceServlet", urlPatterns = "/buyservice")
public class BuyServiceServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(BuyServiceServlet.class.getName()); // logger log4j2

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
                        LOGGER.warn("There is no a product in the product collection");
                        response.setStatus(400);
                        return;
                    }
                }

                boolean addOrderStatus = false;

                OrderBuilder order = new OrderBuilder(orderBuilder.toString());

                addOrderStatus = order.saveOrder(this.getServletContext());

                if (addOrderStatus) {
                    LOGGER.info("Success order");
                    response.setStatus(201);
                }
            } else {
                LOGGER.warn("Items to order are empty");
                response.setStatus(400);
            }
        } catch (Exception ex) {
            LOGGER.error("Order error", ex);
            response.setStatus(500);
        }
    }
}
