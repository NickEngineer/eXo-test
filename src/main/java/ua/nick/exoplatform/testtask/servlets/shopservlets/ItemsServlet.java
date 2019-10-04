package ua.nick.exoplatform.testtask.servlets.shopservlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nick.exoplatform.testtask.model.Product;
import ua.nick.exoplatform.testtask.model.ProductCatalog;
import ua.nick.exoplatform.testtask.servlets.buyservlets.BuyServiceServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ItemsServlet", urlPatterns = "/shop/items")
public class ItemsServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(BuyServiceServlet.class.getName()); // logger log4j2

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Product> productCatalog = ProductCatalog.getCatalog(this.getServletContext());

        try {
            request.setAttribute("products", productCatalog.values().toArray());

            request.getRequestDispatcher("/jsp-pages/shop-items.jsp").include(request, response);
        } catch (Exception ex) {
            LOGGER.error("Shop Items error (probably productCatalog is null)", ex);
        }

    }
}
