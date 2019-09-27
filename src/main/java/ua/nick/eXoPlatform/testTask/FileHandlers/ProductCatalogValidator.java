package ua.nick.eXoPlatform.testTask.FileHandlers;

import ua.nick.eXoPlatform.testTask.Model.Product;
import ua.nick.eXoPlatform.testTask.Model.ProductCatalog;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimerTask;


public class ProductCatalogValidator extends TimerTask {

    private String dataPath = null;
    private Class syncronizer = null;
    private Map<String, Product> oldProductCatalog = null;

    @Override
    public void run() {
        Map<String, Product> loadedProductCatalog
                = ResourceFile.loadCatalog(dataPath, syncronizer);

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

    public ProductCatalogValidator(String dataPath, Class syncronizer, Map<String, Product> oldProductCatalog) {
        this.dataPath = dataPath;
        this.syncronizer = syncronizer;
        this.oldProductCatalog = oldProductCatalog;
    }
}
