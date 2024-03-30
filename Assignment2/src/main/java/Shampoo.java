/**
 * class that extends household
 */
public class Shampoo extends Household{

  /**
   *
   * @param manufacturer manufacturer
   * @param productName name of product
   * @param price price of product
   * @param minimumAge minimum age to buy
   * @param units number of individual units in a package
   */
  public Shampoo(String manufacturer, String productName, double price, int minimumAge, int units) {
    super(manufacturer, productName, price, minimumAge, units);
  }
}
