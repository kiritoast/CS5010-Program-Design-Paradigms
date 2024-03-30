import java.util.Objects;

/**
 * abstract class grocery that extends product
 */
public class Grocery extends Product{

  /**
   * weight of the product
   */
  protected double weight;

  /**
   *
   * @param manufacturer manufacturer
   * @param productName name of product
   * @param price price of product
   * @param minimumAge minimum age to buy
   * @param weight weight of product
   */
  public Grocery(String manufacturer, String productName, double price, int minimumAge,
      double weight) {
    super(manufacturer, productName, price, minimumAge);
    this.weight = weight;
  }

  /**
   *
   * @return the weight
   */
  public double getWeight() {
    return weight;
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
    if (!super.equals(o)) {
      return false;
    }
    Grocery grocery = (Grocery) o;
    return Double.compare(grocery.weight, weight) == 0;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), weight);
  }
}
