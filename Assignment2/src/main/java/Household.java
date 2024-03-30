import java.util.Objects;

/**
 * abstact class that extends product
 */
public class Household extends Product{

  /**
   * number of individual units in a package
   */
  protected int units;

  /**
   *
   * @param manufacturer manufacturer
   * @param productName name of product
   * @param price price of product
   * @param minimumAge minimum age to buy
   * @param units number of individual units in a package
   */
  public Household(String manufacturer, String productName, double price, int minimumAge,
      int units) {
    super(manufacturer, productName, price, minimumAge);
    this.units = units;
  }

  /**
   *
   * @return number of individual units in a package
   */
  public int getUnits() {
    return units;
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
    Household household = (Household) o;
    return units == household.units;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), units);
  }
}
