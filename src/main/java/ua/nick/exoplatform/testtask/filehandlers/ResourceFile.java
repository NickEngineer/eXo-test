package ua.nick.exoplatform.testtask.filehandlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nick.exoplatform.testtask.model.Product;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
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
            Path destination = Paths.get(filePath);
            Path folder = Paths.get(filePath.substring(0, filePath.length() - 8));

            try {
                Files.createDirectories(folder);
            } catch (IOException ex) {
                LOGGER.error("Load data directories creation error", ex);
            }

            try {
                Files.createFile(destination);
            } catch (Exception ex) {
                LOGGER.error("data.csv file creation error", ex);
            }

            try {
                ClassLoader classLoader = servletContext.getClassLoader();

                byte[] targetDataPatternBuffer;

                //read data from datapattern into targetDataPatternBuffer
                try (InputStream dataPatternInputStream
                             = classLoader.getResourceAsStream("/datapattern/data.csv")) {
                    targetDataPatternBuffer = new byte[dataPatternInputStream.available()];
                    dataPatternInputStream.read(targetDataPatternBuffer);
                }

                File targetFile = new File(filePath);

                //write data from the template stream into data.csv
                try (OutputStream dataPatternOutputStream = new FileOutputStream(targetFile)) {
                    dataPatternOutputStream.write(targetDataPatternBuffer);
                }

            } catch (IOException ex) {
                LOGGER.error("Copying data catalog from resources error", ex);
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
                                    , new BigDecimal(lineComponents[2])
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
