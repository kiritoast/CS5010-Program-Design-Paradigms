import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * class shopping cart that store info about items
 */
public class ShoppingCart {

  /**
   * hashmap cart map that works as the shopping cart
   */
  public HashMap<Product, Integer> cartMap;

  /**
   *
   * @param cartMap hashmap car that store items and quantity
   */
  public ShoppingCart(HashMap<Product, Integer> cartMap) {
    this.cartMap = cartMap;
  }

  /**
   *
   * @return the total price of the shopping cart
   */
  public double calculatePrice(){
    double totalPrice = 0.00;
    for(Map.Entry<Product, Integer> pair: this.cartMap.entrySet()){
      totalPrice += pair.getKey().getPrice() * pair.getValue();
    }
    return totalPrice;
  }

  /**
   * add item to the cart
   * @param product item that add to cart
   * @param amount amount that need to be added
   */
  public void addItem(Product product, int amount){
    this.cartMap.put(product, amount);
  }

  /**
   *
   * @return the shopping cart
   */
  public HashMap<Product, Integer> getCartMap() {
    return cartMap;
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
    ShoppingCart that = (ShoppingCart) o;
    return Objects.equals(cartMap, that.cartMap);
  }
  /**
   * {@inheritDoc}
   */

  @Override
  public int hashCode() {
    return Objects.hash(cartMap);
  }
}
