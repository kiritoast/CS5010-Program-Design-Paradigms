import java.util.ArrayList;
import java.util.Objects;

/**
 * receipt that generate at the end of purchase
 */
public class Receipt {
  private double totalPrice;
  private ArrayList<Product> productReceived;
  private ArrayList<Product> productOutOfStock;
  private ArrayList<Product> productRemoved;

  /**
   *
   * @param totalPrice total price that customer spent
   * @param productReceived total product that customer received
   * @param productOutOfStock all products that were out of stock
   * @param productRemoved all products that removed
   */
  public Receipt(double totalPrice, ArrayList<Product> productReceived,
      ArrayList<Product> productOutOfStock, ArrayList<Product> productRemoved) {
    this.totalPrice = totalPrice;
    this.productReceived = productReceived;
    this.productOutOfStock = productOutOfStock;
    this.productRemoved = productRemoved;
  }

  /**
   *
   * @return total priced customer spend
   */
  public double getTotalPrice() {
    return totalPrice;
  }

  /**
   *
   * @return total product that customer received
   */
  public ArrayList<Product> getProductReceived() {
    return productReceived;
  }

  /**
   *
   * @return all products that were out of stock
   */
  public ArrayList<Product> getProductOutOfStock() {
    return productOutOfStock;
  }

  /**
   *
   * @return all products that removed
   */
  public ArrayList<Product> getProductRemoved() {
    return productRemoved;
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
    Receipt receipt = (Receipt) o;
    return Double.compare(receipt.totalPrice, totalPrice) == 0 && Objects.equals(
        productReceived, receipt.productReceived) && Objects.equals(productOutOfStock,
        receipt.productOutOfStock) && Objects.equals(productRemoved,
        receipt.productRemoved);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(totalPrice, productReceived, productOutOfStock, productRemoved);
  }
}
