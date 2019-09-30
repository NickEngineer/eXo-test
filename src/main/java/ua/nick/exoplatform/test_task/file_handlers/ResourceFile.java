package ua.nick.exoplatform.test_task.file_handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nick.exoplatform.test_task.model.Product;
import ua.nick.exoplatform.test_task.model.ProductCatalog;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ResourceFile {

    private static final Logger LOGGER = LogManager.getLogger(ResourceFile.class.getName()); // logger log4j2

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

    public static Map<String, Product> loadCatalog(String filePath, ServletContext servletContext) {
        Map<String, Product> productCatalog = new ConcurrentSkipListMap();

        // create the file path if it doesn't exist (folder or data.csv)
        if (filePath == null) {
            StringBuilder filePathBuilder = new StringBuilder(100);
            filePathBuilder.append(servletContext.getRealPath("/"));
            filePathBuilder.append("..");
            filePathBuilder.append(File.separatorChar);
            filePathBuilder.append("..");
            filePathBuilder.append(File.separatorChar);
            filePathBuilder.append("data");
            filePathBuilder.append(File.separatorChar);
            filePathBuilder.append("data.csv");

            filePath = filePathBuilder.toString();
        }

        File csvFile = new File(filePath);

        // copying the data pattern if there is no a csvFile
        if (!csvFile.exists()) {

            String dataTemplatePath = servletContext.getRealPath("/WEB-INF/classes/data_pattern/data.csv");

            Path source = Paths.get(dataTemplatePath);
            Path destination = Paths.get(filePath);

            try {
                Files.createDirectories(destination);
            } catch (IOException ex) {
                LOGGER.error("Load data directories creation error",ex);
            }

            try {
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                LOGGER.error("Copying data catalog directory error",ex);
            }

            csvFile = new File(filePath);
        }


        //read the data file
        try (FileReader reader = new FileReader(csvFile);
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            String[] lineComponents;

            // fill the product collection from the data.csv
            while ((line = br.readLine()) != null) {
                lineComponents = line.split(",");

                try {
                    productCatalog.put(lineComponents[1]
                            , new Product(lineComponents[0]
                                    , lineComponents[1]
                                    , Integer.parseInt(lineComponents[2])
                            )
                    );
                } catch (Exception ex) {
                    LOGGER.error("Addition products into collection error",ex);
                }
            }
        } catch (FileNotFoundException ex) {
            LOGGER.error("Data catalog not found error",ex);
        } catch (IOException ex) {
            LOGGER.error("Data catalog IOException",ex);
        }

        return productCatalog;
    }
}
