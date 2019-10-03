package ua.nick.exoplatform.testtask.filehandlers;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderBuilder {

    private String orderCSVBody;

    public OrderBuilder(String orderCSVBody) {
        this.orderCSVBody = orderCSVBody;
    }

    public boolean saveOrder(ServletContext servletContext) throws IOException {
        boolean result = false;

        StringBuilder orderPathBuilder = new StringBuilder();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");

        String dateString = format.format(new Date());

        //Timestamp ts = new Timestamp(date.getTime());

        orderPathBuilder.append(System.getProperty("catalina.base"));
        orderPathBuilder.append(File.separatorChar);
        orderPathBuilder.append("data");
        orderPathBuilder.append(File.separatorChar);
        orderPathBuilder.append("order-");
        orderPathBuilder.append(dateString);
        orderPathBuilder.append(".csv");

        //save order
        Path file = Paths.get(orderPathBuilder.toString());
        Files.write(file, orderCSVBody.getBytes());


        //check correct saving of the order
        StringBuilder orderValidation = new StringBuilder();

        for (String line : Files.readAllLines(file)) {
            orderValidation.append(line);
            orderValidation.append("\n");
        }

        if (orderCSVBody.equals(orderValidation.toString())) {
            result = true;
        }

        return result;
    }
}
