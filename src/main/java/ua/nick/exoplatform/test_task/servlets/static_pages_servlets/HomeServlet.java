package ua.nick.exoplatform.test_task.servlets.static_pages_servlets;

import ua.nick.exoplatform.test_task.file_handlers.ResourceFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "HomeServlet", urlPatterns = "")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /*
       // possible resource louder
        File file = new File(
                getClass().getClassLoader().getResource("pages/home.html").getFile()
        );

        ResourceFile.printFileIntoResponse(file, response);*/

        String path = "/WEB-INF/classes/pages/home.html";

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        //requestDispatcher.forward(request, response);
        requestDispatcher.include(request,response);
    }
}
