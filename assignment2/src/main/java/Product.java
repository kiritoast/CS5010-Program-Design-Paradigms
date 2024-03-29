import java.util.Objects;

/**
 * abstract clas product that save info about all product
 */
public class Product {

  /**
   * manufacturer of the product
   */
  protected String manufacturer;
  /**
   * name of the product
   */
  protected String productName;
  /**
   * price of the product as int
   */
  protected double price;
  /**
   * minimum age to buy the product
   */
  protected int minimumAge;

  /**
   *
   * @param manufacturer manufacturer
   * @param productName name of product
   * @param price price of product
   * @param minimumAge minimum age to buy
   */
  public Product(String manufacturer, String productName, double price, int minimumAge) {
    this.manufacturer = manufacturer;
    this.productName = productName;
    this.price = price;
    this.minimumAge = minimumAge;
  }

  /**
   *
   * @return the manufacturer
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   *
   * @return the name of product
   */
  public String getProductName() {
    return productName;
  }

  /**
   *
   * @return the price of the product
   */
  public double getPrice() {
    return price;
  }

  /**
   *
   * @return the minimum age
   */
  public int getMinimumAge() {
    return minimumAge;
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
    Product product = (Product) o;
    return Double.compare(product.price, price) == 0 && minimumAge == product.minimumAge
        && Objects.equals(manufacturer, product.manufacturer) && Objects.equals(
        productName, product.productName);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(manufacturer, productName, price, minimumAge);
  }
}
