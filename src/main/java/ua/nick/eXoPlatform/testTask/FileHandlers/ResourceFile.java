package ua.nick.eXoPlatform.testTask.FileHandlers;

import ua.nick.eXoPlatform.testTask.Model.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ResourceFile {

    public static void printFileIntoResponse(File file, HttpServletResponse response) throws IOException {

        if (file != null) {
            try (FileReader reader = new FileReader(file);
                 BufferedReader br = new BufferedReader(reader)) {

                String line;
                PrintWriter writer = response.getWriter();

                while ((line = br.readLine()) != null) {
                    writer.println(line);
                }
            }
        }
    }

    public static Map<String, Product> loadCatalog(String filePath, Class context) {
        Map<String, Product> productCatalog = new ConcurrentSkipListMap();

        File csvFile = new File(
                filePath
        );

        if (csvFile != null) {
            try (FileReader reader = new FileReader(csvFile);
                 BufferedReader br = new BufferedReader(reader)) {

                String line;
                String[] lineComponents;

                while ((line = br.readLine()) != null) {
                    lineComponents = line.split(",");

                    try {
                        productCatalog.put(lineComponents[1]
                                , new Product(lineComponents[0]
                                        , lineComponents[1]
                                        , Integer.parseInt(lineComponents[2])
                                )
                        );
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return productCatalog;
    }
}
