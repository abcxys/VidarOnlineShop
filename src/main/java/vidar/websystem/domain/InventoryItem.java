package vidar.websystem.domain;

/**
 * @author yishi.xing
 * Create datetime Apr 5th, 2024 - 16:44 PM
 */
public interface InventoryItem {
    // get the inventory_id of the item
    Long getId();
    Long getProductId();
    String getLocation();
    Integer getQuantity();
}