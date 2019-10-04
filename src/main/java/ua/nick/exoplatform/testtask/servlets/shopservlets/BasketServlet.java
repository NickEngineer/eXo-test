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
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

@WebServlet(name = "BasketServlet", urlPatterns = "/shop/basket")
public class BasketServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(BasketServlet.class.getName()); // logger log4j2

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Product> productCatalog = ProductCatalog.getCatalog(this.getServletContext());

        try {
            String items = (String) request.getParameter("items");
            String[] codes = items.split(",");

            Product product;

            Map<String, Product> productsSelected = new ConcurrentSkipListMap();

            for (int i = 0; i < codes.length; ++i) {
                product = productCatalog.get(codes[i]);

                if (product != null) {
                    productsSelected.put(codes[i], new Product(product));
                } else {
                    productsSelected.put(codes[i], new Product("", codes[i], new BigDecimal("0")));
                }
            }

            request.setAttribute("productsSelected", productsSelected.values().toArray());

            request.getRequestDispatcher("/jsp-pages/basket.jsp").include(request, response);
        } catch (Exception ex) {
            LOGGER.error("Basket Servlet error", ex);
            request.getRequestDispatcher("/WEB-INF/static/pages/failure.html").include(request, response);
        }
    }
}
