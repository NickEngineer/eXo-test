package ua.nick.exoplatform.testtask.filehandlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nick.exoplatform.testtask.model.Product;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ResourceFile {

    private static final Logger LOGGER = LogManager.getLogger(ResourceFile.class.getName()); // logger log4j2

    public static Map<String, Product> loadCatalog(String filePath, ServletContext servletContext) {
        Map<String, Product> productCatalog = new ConcurrentSkipListMap();

        File csvFile = new File(filePath);

        // copying the data pattern if there is no a csvFile
        if (!csvFile.exists()) {
            StringBuilder dataFileTemplatePathBuilder = new StringBuilder(100);
            dataFileTemplatePathBuilder.append(System.getProperty("catalina.base"));
            dataFileTemplatePathBuilder.append(File.separatorChar);
            dataFileTemplatePathBuilder.append("webapps");
            dataFileTemplatePathBuilder.append(File.separatorChar);
            dataFileTemplatePathBuilder.append(servletContext.getContextPath().substring(1)); // buildshop
            dataFileTemplatePathBuilder.append(File.separatorChar);
            dataFileTemplatePathBuilder.append("WEB-INF");
            dataFileTemplatePathBuilder.append(File.separatorChar);
            dataFileTemplatePathBuilder.append("static");
            dataFileTemplatePathBuilder.append(File.separatorChar);
            dataFileTemplatePathBuilder.append("datapattern");
            dataFileTemplatePathBuilder.append(File.separatorChar);
            dataFileTemplatePathBuilder.append("data.csv");


            String dataTemplatePath = dataFileTemplatePathBuilder.toString();

            Path source = Paths.get(dataTemplatePath);
            Path destination = Paths.get(filePath);

            try {
                Files.createDirectories(destination);
            } catch (IOException ex) {
                LOGGER.error("Load data directories creation error", ex);
            }

            try {
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                LOGGER.error("Copying data catalog directory error", ex);
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
                    LOGGER.error("Addition products into collection error", ex);
                }
            }
        } catch (FileNotFoundException ex) {
            LOGGER.error("Data catalog not found error", ex);
        } catch (IOException ex) {
            LOGGER.error("Data catalog IOException", ex);
        }

        return productCatalog;
    }
}
