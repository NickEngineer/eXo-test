package ua.nick.eXoPlatform.testTask.Model;

import ua.nick.eXoPlatform.testTask.FileHandlers.ProductCatalogValidator;
import ua.nick.eXoPlatform.testTask.FileHandlers.ResourceFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Map;
import java.util.Timer;

public class ProductCatalog {

    private static final ProductCatalog INSTANCE = new ProductCatalog();

    private static final String CSV_FILE = "data.csv";
    private static final int CATALOG_UPDATE_TIME = 300000; // 5 minutes

    private static Map<String, Product> productCatalog = null;

    public static Map<String, Product> getCatalog(ServletContext servletContext) {


        synchronized (INSTANCE) {
            if (productCatalog == null) {
                StringBuilder csvDataPathBuilder = new StringBuilder();

                csvDataPathBuilder.append(servletContext.getRealPath("/"));
                csvDataPathBuilder.append("..");
                csvDataPathBuilder.append(File.separator);
                csvDataPathBuilder.append("..");
                csvDataPathBuilder.append(File.separator);
                csvDataPathBuilder.append("data");
                csvDataPathBuilder.append(File.separator);
                csvDataPathBuilder.append(CSV_FILE);

                productCatalog = ResourceFile.loadCatalog(csvDataPathBuilder.toString(),
                        INSTANCE.getClass());

                Timer timer = new Timer();

                // Start catalog update functionality
                timer.schedule(new ProductCatalogValidator(csvDataPathBuilder.toString(),
                        INSTANCE.getClass(), productCatalog), 0, CATALOG_UPDATE_TIME);


            }
        }

        return productCatalog;
    }
}