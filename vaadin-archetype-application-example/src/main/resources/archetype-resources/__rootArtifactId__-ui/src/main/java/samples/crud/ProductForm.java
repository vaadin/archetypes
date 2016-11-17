#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.samples.crud;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.beanutils.BeanUtils;
import ${package}.samples.backend.data.Availability;
import ${package}.samples.backend.data.Category;
import ${package}.samples.backend.data.Product;

import com.vaadin.data.BeanBinder;
import com.vaadin.data.Result;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.util.converter.ValueContext;
import com.vaadin.server.Page;

/**
 * A form for editing a single product.
 *
 * Using responsive layouts, the form can be displayed either sliding out on the
 * side of the view or filling the whole screen - see the theme for the related
 * CSS rules.
 */
public class ProductForm extends ProductFormDesign {

    private SampleCrudLogic viewLogic;
    private BeanBinder<Product> beanBinder;

    private static class StockPriceConverter extends StringToIntegerConverter {

        public StockPriceConverter() {
            super("Could not convert value to " + Integer.class.getName());
        }

        @Override
        protected NumberFormat getFormat(Locale locale) {
            // do not use a thousands separator, as HTML5 input type
            // number expects a fixed wire/DOM number format regardless
            // of how the browser presents it to the user (which could
            // depend on the browser locale)
            DecimalFormat format = new DecimalFormat();
            format.setMaximumFractionDigits(0);
            format.setDecimalSeparatorAlwaysShown(false);
            format.setParseIntegerOnly(true);
            format.setGroupingUsed(false);
            return format;
        }

        @Override
        public Result<Integer> convertToModel(String value,
                ValueContext context) {
            Result<Integer> result = super.convertToModel(value, context);
            return result.map(stock -> stock == null ? 0 : stock);
        }

    }

    public ProductForm(SampleCrudLogic sampleCrudLogic) {
        super();
        addStyleName("product-form");
        viewLogic = sampleCrudLogic;

        availability.setItems(Availability.values());
        availability.setEmptySelectionAllowed(false);

        beanBinder = new BeanBinder<>(Product.class);
        beanBinder.forField(price).withConverter(new EuroConverter())
                .bind("price");
        beanBinder.forField(stockCount).withConverter(new StockPriceConverter())
                .bind("stockCount");

        category.setItemCaptionGenerator(Category::getName);
        beanBinder.forField(category).bind("category");
        beanBinder.bindInstanceFields(this);

        // enable/disable save button while editing
        beanBinder.addStatusChangeListener(event -> {
            boolean isValid = !event.hasValidationErrors();
            boolean hasChanges = beanBinder.hasChanges();
            save.setEnabled(hasChanges && isValid);
        });

        save.addClickListener(click -> beanBinder.getBean()
                .ifPresent(product -> viewLogic.saveProduct(product)));

        cancel.addClickListener(click -> viewLogic.cancelProduct());

        delete.addClickListener(click -> beanBinder.getBean()
                .ifPresent(viewLogic::deleteProduct));
    }

    public void setCategories(Collection<Category> categories) {
        category.setItems(categories);
    }

    public void editProduct(Product product) {
        if (product == null) {
            product = new Product();
        }
        beanBinder.setBean(cloneProduct(product));

        // Scroll to the top
        // As this is not a Panel, using JavaScript
        String scrollScript = "window.document.getElementById('" + getId()
                + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);
    }

    private Product cloneProduct(Product product) {
        Product clone;
        try {
            clone = (Product) BeanUtils.cloneBean(product);
        } catch (IllegalAccessException | InstantiationException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Failed to clone a product bean");
        }
        return clone;
    }
}
