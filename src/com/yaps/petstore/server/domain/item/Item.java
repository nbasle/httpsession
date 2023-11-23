package com.yaps.petstore.server.domain.item;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.FinderException;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.product.Product;

import java.util.Collection;

/**
 * This class represents an Item in the catalog of the YAPS company.
 * The catalog is divided into catagories. Each one divided into products
 * and each product in items.
 */
public final class Item extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _name;
    private double _unitCost;
    private String _imagePath;
    private Product _product;

    // ======================================
    // =            Constructors            =
    // ======================================
    {
        _dao = new ItemDAO();
    }

    public Item() {
    }

    public Item(final String id) {
        setId(id);
    }

    public Item(final String id, final String name, final double unitCost, final Product product) {
        setId(id);
        setName(name);
        setUnitCost(unitCost);
        setProduct(product);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This methods returns all the items for a given product.
     *
     * @param productId product id from which we want all the items
     * @return a collection of Item
     * @throws com.yaps.petstore.common.exception.ObjectNotFoundException
     *                         thrown if the id is not found
     * @throws FinderException thrown if a system failure occurs
     */
    public Collection findAll(final String productId) throws FinderException {
        final String mname = "findAll";
        Trace.entering(getCname(), mname, productId);

        // Uses the DAO to access the persistent layer
        final Collection items = getDAO().selectAll(productId);

        Trace.exiting(getCname(), mname, new Integer(items.size()));
        return items;
    }

    /**
     * This method returns all the items that match a keyword in their id or name
     *
     * @param keyword
     * @return a collection of Item
     * @throws com.yaps.petstore.common.exception.ObjectNotFoundException
     *                         thrown if the id is not found
     * @throws FinderException thrown if a system failure occurs
     */
    public Collection search(final String keyword) throws FinderException {
        final String mname = "search";
        Trace.entering(getCname(), mname, keyword);

        // Uses the DAO to access the persistent layer
        final Collection items = getDAO().search(keyword);

        Trace.exiting(getCname(), mname, new Integer(items.size()));
        return items;
    }

    // ======================================
    // =          Protected methods         =
    // ======================================
    protected void loadObject(final Object object) {
        final Item temp = (Item) object;

        // Sets data to current object
        setId(temp.getId());
        setName(temp.getName());
        setUnitCost(temp.getUnitCost());
        setImagePath(temp.getImagePath());
        setProduct(temp.getProduct());
    }

    protected void checkData() throws CheckException {
        checkId(getId());
        if (getName() == null || "".equals(getName()))
            throw new CheckException("Invalid name");
        if (getUnitCost() <= 0)
            throw new CheckException("Invalid unit cost");
        if (getProduct() == null || getProduct().getId() == null || "".equals(getProduct().getId()))
            throw new CheckException("Invalid product");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    private ItemDAO getDAO() {
        return (ItemDAO) _dao;
    }

    public String getName() {
        return _name;
    }

    public void setName(final String name) {
        _name = name;
    }

    public double getUnitCost() {
        return _unitCost;
    }

    public void setUnitCost(final double unitCost) {
        _unitCost = unitCost;
    }

    public String getImagePath() {
        return _imagePath;
    }

    public void setImagePath(final String imagePath) {
        _imagePath = imagePath;
    }

    public Product getProduct() {
        return _product;
    }

    public void setProduct(final Product product) {
        _product = product;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Item{");
        buf.append("id=").append(getId());
        buf.append(",name=").append(getName());
        buf.append(",unitCost=").append(getUnitCost());
        buf.append(",imagePath=").append(getImagePath());
        buf.append('}');
        return buf.toString();
    }
}
