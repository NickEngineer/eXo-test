package ua.nick.exoplatform.test_task.servlets.shop_servlets;

import ua.nick.exoplatform.test_task.model.Product;
import ua.nick.exoplatform.test_task.model.ProductCatalog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ItemsServlet", urlPatterns = "/shop/items")
public class ItemsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Product> productCatalog = ProductCatalog.getCatalog(this.getServletContext());

        request.setAttribute("products", productCatalog.values().toArray());

        request.getRequestDispatcher("/jspPages/shopItems.jsp").include(request, response);
    }
}
