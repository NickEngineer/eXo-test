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

@WebServlet(name = "BasketServlet", urlPatterns = "/shop/basket")
public class BasketServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Product> productCatalog = ProductCatalog.getCatalog(this.getServletContext());


        String items = (String) request.getParameter("items");
        String[] codes = items.split(",");

        Product product;

        String[][] goodsItemsRepresentation = new String[codes.length][3];

        for (int i = 0; i < codes.length; ++i) {
            if (productCatalog.containsKey(codes[i])) {
                product = productCatalog.get(codes[i]);

                goodsItemsRepresentation[i][0] = product.getName();
                goodsItemsRepresentation[i][1] = codes[i];
                goodsItemsRepresentation[i][2] = Integer.toString(product.getPrice());
            } else {
                goodsItemsRepresentation[i][0] = "";
                goodsItemsRepresentation[i][1] = codes[i];
                goodsItemsRepresentation[i][2] = "";
            }
        }

        request.setAttribute("products", goodsItemsRepresentation);

        request.getRequestDispatcher("/jspPages/basket.jsp").include(request, response);
    }
}
