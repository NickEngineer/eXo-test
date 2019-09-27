package ua.nick.eXoPlatform.testTask.Servlets.ShopServlets;

import ua.nick.eXoPlatform.testTask.Model.Product;
import ua.nick.eXoPlatform.testTask.Model.ProductCatalog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "ItemsServlet", urlPatterns = "/shop/items")
public class ItemsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Product> productCatalog = ProductCatalog.getCatalog(this.getServletContext());

       /* Set<String> catalogKeys = productCatalog.keySet();

        Product product;
        Product[] products = new Product[productCatalog.size()];
        int productNumber = 0;

        for (String catalogKey : catalogKeys) {
            product = productCatalog.get(catalogKey);

            products[productNumber++] = product;
        }*/

        request.setAttribute("products", productCatalog.values().toArray());

        request.getRequestDispatcher("/jspPages/shopItems.jsp").forward(request, response);
    }
}
