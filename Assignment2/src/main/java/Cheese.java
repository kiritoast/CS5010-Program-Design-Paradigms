/**
 * class that inherent grocery
 */
public class Cheese extends Grocery{

  /**
   *
   * @param manufacturer manufacturer
   * @param productName name of product
   * @param price price of product
   * @param minimumAge minimum age to buy
   * @param weight weight of product
   */
  public Cheese(String manufacturer, String productName, double price, int minimumAge,
      double weight) {
    super(manufacturer, productName, price, minimumAge, weight);
  }

}
