package ua.nick.eXoPlatform.testTask.Servlets.StaticPagesServlets;

import ua.nick.eXoPlatform.testTask.FileHandlers.ResourceFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "ShopServlet", urlPatterns = "/shop")
public class ShopServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file = new File(
                getClass().getClassLoader().getResource("pages/shop.html").getFile()
        );

        ResourceFile.printFileIntoResponse(file, response);
    }
}
