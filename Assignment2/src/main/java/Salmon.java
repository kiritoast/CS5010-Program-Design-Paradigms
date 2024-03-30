/**
 * class salmon that extends grocery
 */
public class Salmon extends Grocery {

  /**
   *
   * @param manufacturer manufacturer
   * @param productName name of product
   * @param price price of product
   * @param minimumAge minimum age to buy
   * @param weight weight of product
   */
  public Salmon(String manufacturer, String productName, double price, int minimumAge,
      double weight) {
    super(manufacturer, productName, price, minimumAge, weight);
  }

}
