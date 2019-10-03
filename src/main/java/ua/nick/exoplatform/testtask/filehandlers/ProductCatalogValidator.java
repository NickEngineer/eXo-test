package ua.nick.exoplatform.testtask.filehandlers;

import ua.nick.exoplatform.testtask.model.Product;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;


public class ProductCatalogValidator extends TimerTask {

    private String dataPath = null;
    private ServletContext servletContext = null;
    private Map<String, Product> oldProductCatalog = null;

    @Override
    public void run() {

        Map<String, Product> loadedProductCatalog
                = ResourceFile.loadCatalog(dataPath, servletContext);


        Set<String> oldKeys = oldProductCatalog.keySet();


        for (String oldKey : oldKeys) {
            if (!loadedProductCatalog.containsKey(oldKey)) {
                oldProductCatalog.remove(oldKey);
            }
        }

        Set<String> newKeys = loadedProductCatalog.keySet();

        for (String newKey : newKeys) {
            if (!oldProductCatalog.containsKey(newKey)) {
                oldProductCatalog.put(newKey, loadedProductCatalog.get(newKey));
            }
        }
    }

    public ProductCatalogValidator(String dataPath, ServletContext servletContext, Map<String, Product> oldProductCatalog) {
        this.dataPath = dataPath;
        this.servletContext = servletContext;
        this.oldProductCatalog = oldProductCatalog;
    }
}
