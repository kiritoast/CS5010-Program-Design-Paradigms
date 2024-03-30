import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * inventory class that store both grocery stock and household stock
 */
public class Inventory {
  private ArrayList<StockItem> groceryStock;
  private ArrayList<StockItem> householdStock;

  /**
   *
   * @param groceryStock array list that store grocery
   * @param householdStock array list that store household
   */
  public Inventory(ArrayList<StockItem> groceryStock, ArrayList<StockItem> householdStock) {
    this.groceryStock = groceryStock;
    this.householdStock = householdStock;
  }

  /**
   * substitue item if one item is not enough
   * @param product product that need to be substitutes
   * @return the product that can be substitutes
   */
  public Product substitute(Product product){
    if(product instanceof Grocery){
      for(StockItem stockItem: groceryStock){
        Product substituteProduct = stockItem.getProduct();
        if(product.getClass().equals(substituteProduct.getClass())){
          if(stockItem.getQuantity() > 0 && substituteProduct.getPrice() <= product.getPrice()
              && ((Grocery) substituteProduct).getWeight() >= ((Grocery) product).getWeight() ){
            return substituteProduct;
          }
        }
      }
    }
    if(product instanceof Household){
      for(StockItem stockItem: householdStock){
        Product substituteProduct = stockItem.getProduct();
        if(product.getClass().equals(substituteProduct.getClass())){
          if(substituteProduct.getPrice() <= product.getPrice() &&
              ((Household) product).getUnits() < ((Household) substituteProduct).getUnits()){
            return substituteProduct;
          }
        }
      }
    }
    return null;
  }


  /**
   * adding stockitem to the appropriate list
   * @param stockItem item that need to be added
   * @return if the stock has been added to inventory
   */
  public boolean addStock(StockItem stockItem){
    if(stockItem.getProduct() instanceof Grocery){
      groceryStock.add(stockItem);
      return true;
    }else if(stockItem.getProduct() instanceof Household){
      householdStock.add(stockItem);
      return true;
    }else{
      return false;
    }
  }

  /**
   * return the total price of inventory
   * @return total price
   */
  public double getTotalPrice(){
    double totalPrice = 0.0;
    for(StockItem stockItem: groceryStock){
      totalPrice += stockItem.getProduct().getPrice();
    }
    for(StockItem stockItem: householdStock){
      totalPrice += stockItem.getProduct().getPrice();
    }
    return totalPrice;
  }

  /**
   *
   * @return the grocery stock
   */
  public ArrayList<StockItem> getGroceryStock() {
    return groceryStock;
  }

  /**
   *
   * @return the household stock
   */
  public ArrayList<StockItem> getHouseholdStock() {
    return householdStock;
  }
  /**
   * {@inheritDoc}
   */

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Inventory inventory = (Inventory) o;
    return Objects.equals(groceryStock, inventory.groceryStock) && Objects.equals(
        householdStock, inventory.householdStock);
  }
  /**
   * {@inheritDoc}
   */

  @Override
  public int hashCode() {
    return Objects.hash(groceryStock, householdStock);
  }
}
