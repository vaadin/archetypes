#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.samples.crud;

import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Stream;

import ${package}.samples.backend.DataService;
import ${package}.samples.backend.data.Product;

import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;

public class ProductDataProvider
        extends AbstractDataProvider<Product, Supplier<String>> {

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
    public int size(Query<Product, Supplier<String>> t) {
        return (int) fetch(t).count();
    }

    @Override
    public Stream<Product> fetch(Query<Product, Supplier<String>> query) {
        String filterText = query.getFilter().map(Supplier::get).orElse(null);
        if (filterText == null || filterText.isEmpty()) {
            return DataService.get().getAllProducts().stream();
        }
        return DataService.get().getAllProducts().stream().filter(
                product -> passesFilter(product.getProductName(), filterText)
                        || passesFilter(product.getAvailability(), filterText)
                        || passesFilter(product.getCategory(), filterText));
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
