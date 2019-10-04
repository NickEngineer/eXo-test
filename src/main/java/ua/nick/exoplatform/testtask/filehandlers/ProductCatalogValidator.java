package ua.nick.exoplatform.testtask.filehandlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nick.exoplatform.testtask.model.Product;
import ua.nick.exoplatform.testtask.model.ProductCatalog;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;


public class ProductCatalogValidator extends TimerTask {

    private static final Logger LOGGER = LogManager.getLogger(ProductCatalogValidator.class.getName()); // logger log4j2

    private String dataPath = null;
    private ServletContext servletContext = null;
    private Map<String, Product> oldProductCatalog = null;

    @Override
    public void run() {

        Map<String, Product> loadedProductCatalog
                = ResourceFile.loadCatalog(dataPath, servletContext);

        Set<String> oldKeys = oldProductCatalog.keySet();
        String[] oldKeysArray = oldKeys.toArray(new String[0]);

        // delete old products (deleted from data.csv)
        for (int i = 0; i < oldKeysArray.length; ++i) {
            if (!loadedProductCatalog.containsKey(oldKeysArray[i])) {
                oldProductCatalog.remove(oldKeysArray[i]);
            }
        }

        Set<String> newKeys = loadedProductCatalog.keySet();

        // change existing and add new products
        for (String newKey : newKeys) {
            oldProductCatalog.put(newKey, loadedProductCatalog.get(newKey));
        }

        LOGGER.info("data collection update finished");
    }

    public ProductCatalogValidator(String dataPath, ServletContext servletContext,
                                   Map<String, Product> oldProductCatalog) {
        this.dataPath = dataPath;
        this.servletContext = servletContext;
        this.oldProductCatalog = oldProductCatalog;
    }
}
