package ua.nick.exoplatform.testtask.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nick.exoplatform.testtask.filehandlers.ProductCatalogValidator;
import ua.nick.exoplatform.testtask.filehandlers.ResourceFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Map;
import java.util.Timer;

public class ProductCatalog {

    private static final Logger LOGGER = LogManager.getLogger(ProductCatalog.class.getName()); // logger log4j2

    private static final ProductCatalog INSTANCE = new ProductCatalog();

    private static final String CSV_FILE = "data.csv"; // data file
    private static final int CATALOG_UPDATE_TIME = 300000; // 5 minutes

    private static Map<String, Product> productCatalog = null;

    public static Map<String, Product> getCatalog(ServletContext servletContext) {


        synchronized (INSTANCE) {
            if (productCatalog == null) {
                StringBuilder csvDataPathBuilder = new StringBuilder(100);
                try {
                    csvDataPathBuilder.append(System.getProperty("catalina.base"));
                    csvDataPathBuilder.append(File.separatorChar);
                    csvDataPathBuilder.append("data");
                    csvDataPathBuilder.append(File.separatorChar);
                    csvDataPathBuilder.append("data.csv");
                } catch (Exception ex) {
                    LOGGER.error("Csv data path creation error", ex);
                }

                // Load productCatalog from data.csv
                try {
                    productCatalog = ResourceFile.loadCatalog(csvDataPathBuilder.toString(),
                            servletContext);
                } catch (Exception ex) {
                    LOGGER.error("Load productCatalog from data.csv error", ex);
                }

                Timer timer = new Timer();

                // Start catalog update functionality
                try{
                    timer.schedule(new ProductCatalogValidator(csvDataPathBuilder.toString(),
                            servletContext, productCatalog), 0, CATALOG_UPDATE_TIME);
                } catch (Exception ex){
                    LOGGER.error("Catalog update functionality error", ex);
                }

            }
        }

        return productCatalog;
    }
}