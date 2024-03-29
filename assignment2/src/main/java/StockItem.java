import java.util.Objects;

/**
 * class that store the in stock info about one product
 */
public class StockItem {
  private Product product;
  private int quantity;

  /**
   *
   * @param product product
   * @param quantity quantity of product
   */
  public StockItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  /**
   * check if there is enough stock
   * @param amount amount need to be checked
   * @return true if there is enough, false if not
   */
  public boolean checkEnoughStock(int amount){
    if(this.quantity < amount){
      System.out.println("Not enough stock");
      return false;
    }else{
      return true;
    }
  }

  /**
   * decrease the amount of stock
   * @param amount amount need to be decrease
   * @return true if success, false if not
   */
  public boolean decreaseQuantity(int amount){
    if(checkEnoughStock(amount)){
      this.quantity = this.quantity - amount;
    }
    return checkEnoughStock(amount);
  }

  /**
   * increase a certain amount
   * @param amount that need to be increased
   */
  public void increaseQuantity(int amount){
    this.quantity = this.quantity + amount;
  }

  /**
   * get the product
   * @return the product
   */
  public Product getProduct() {
    return product;
  }

  /**
   * get the quantity
   * @return the number of quantity
   */
  public int getQuantity() {
    return quantity;
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
    StockItem stockItem = (StockItem) o;
    return quantity == stockItem.quantity && Objects.equals(product, stockItem.product);
  }
  /**
   * {@inheritDoc}
   */

  @Override
  public int hashCode() {
    return Objects.hash(product, quantity);
  }
}
