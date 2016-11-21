#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.samples.crud;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import ${package}.samples.backend.DataService;
import ${package}.samples.backend.data.Product;

import com.vaadin.server.data.AbstractDataProvider;
import com.vaadin.server.data.Query;

public class ProductDataProvider extends AbstractDataProvider<Product> {

    private String filterText;

    /**
     * Sets the filtering text for this DataProvider.
     * 
     * @param filterText
     *            the filtering text
     */
    public void setFilterText(String filterText) {
        if (Objects.equals(this.filterText, filterText)) {
            return;
        }
        if (filterText != null) {
            this.filterText = filterText.toLowerCase(Locale.ENGLISH);
        } else {
            this.filterText = null;
        }
        refreshAll();
    }

    /**
     * Store given product to the backing data service.
     * 
     * @param product
     *            the updated or new product
     */
    public void save(Product product) {
        DataService.get().updateProduct(product);
        refreshAll();
    }

    /**
     * Delete given product from the backing data service.
     * 
     * @param product
     *            the product to be deleted
     */
    public void delete(Product product) {
        DataService.get().deleteProduct(product.getId());
        refreshAll();
    }

    @Override
    public boolean isInMemory() {
        return true;
    }

    @Override
    public int size(Query t) {
        return (int) fetch(t).count();
    }

    @Override
    public Stream<Product> fetch(Query query) {
        if (filterText == null || filterText.isEmpty()) {
            return DataService.get().getAllProducts().stream();
        }
        return DataService.get().getAllProducts().stream()
                .filter(product -> passesFilter(product.getProductName())
                        || passesFilter(product.getAvailability())
                        || passesFilter(product.getCategory()));
    }

    private boolean passesFilter(Object object) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
