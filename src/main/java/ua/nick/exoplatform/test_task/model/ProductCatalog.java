package ua.nick.exoplatform.test_task.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nick.exoplatform.test_task.file_handlers.ProductCatalogValidator;
import ua.nick.exoplatform.test_task.file_handlers.ResourceFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Timer;

public class ProductCatalog {

    private static final Logger LOGGER = LogManager.getLogger(ProductCatalog.class.getName()); // logger log4j2

    private static final ProductCatalog INSTANCE = new ProductCatalog();

    private static final String CSV_FILE_PATH = "/../../data/data.csv"; // data file relative path
    private static final int CATALOG_UPDATE_TIME = 300000; // 5 minutes

    private static Map<String, Product> productCatalog = null;

    public static Map<String, Product> getCatalog(ServletContext servletContext) {


        synchronized (INSTANCE) {
            if (productCatalog == null) {
                String csvDataPath = null;
                try {
                    csvDataPath = servletContext.getRealPath(CSV_FILE_PATH);
                    throw new RuntimeException("test");
                } catch (Exception ex) {
                    LOGGER.error("Csv data path creation error", ex);
                }

                // Load productCatalog from data.csv
                try {
                    productCatalog = ResourceFile.loadCatalog(csvDataPath,
                            servletContext);
                } catch (Exception ex) {
                    LOGGER.error("Load productCatalog from data.csv error",ex);
                }

                Timer timer = new Timer();

                // Start catalog update functionality
                timer.schedule(new ProductCatalogValidator(csvDataPath,
                        servletContext, productCatalog), 0, CATALOG_UPDATE_TIME);
            }
        }

        return productCatalog;
    }
}