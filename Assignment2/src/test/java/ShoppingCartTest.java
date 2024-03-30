import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {
  ShoppingCart shoppingCart;
  Beer beer1;
  Beer beer2;
  Cheese cheese1;
  Cheese cheese2;
  Salmon salmon1;
  Salmon salmon2;
  PaperTowels paperTowel1;
  PaperTowels paperTowel2;
  Shampoo shampoo;
  HashMap<Product, Integer> cartMap;
  HashMap<Product, Integer> cartMapFalse;

  @BeforeEach
  void setUp() {
    beer1 = new Beer("factory", "good cheese",10.5, 4, 37.9);
    beer2 = new Beer("factory", "good cheese",10.5, 4, 37.9);
    cheese1 = new Cheese("factory", "good cheese",10.5, 4, 37.9);
    cheese2 = new Cheese("factory", "good cheese",10.5, 4, 37.9);
    salmon1 = new Salmon("factory", "good cheese",10.5, 4, 37.9);
    salmon2 = new Salmon("factory", "good cheese",10.5, 4, 37.9);
    paperTowel1 = new PaperTowels("factory", "good cheese",10.5, 4, 1);
    paperTowel2 = new PaperTowels("factory", "good cheese",10.5, 4, 1);
    shampoo = new Shampoo("factory", "good cheese",10.5, 4, 1);
    cartMap = new HashMap<Product, Integer>();
    cartMap.put(beer1,10);
    cartMap.put(cheese1,20);
    cartMap.put(paperTowel1,30);

    shoppingCart = new ShoppingCart(cartMap);
  }

  @Test
  void calculatePrice() {
    Assertions.assertEquals(630.0,shoppingCart.calculatePrice());


  }

  @Test
  void addItem() {
    shoppingCart.addItem(beer2, 10);
    shoppingCart.addItem(paperTowel2,20);
  }

  @Test
  void getCartMap() {
    Assertions.assertEquals(cartMap,shoppingCart.getCartMap());
  }

  @Test
  void testEquals() {
    ShoppingCart shoppingCartTrue = this.shoppingCart;
    cartMapFalse = new HashMap<Product, Integer>();
    cartMapFalse.put(shampoo,20);
    ShoppingCart shoppingCartFalse = new ShoppingCart(cartMapFalse);
    Assertions.assertTrue(shoppingCart.equals(shoppingCart));
    Assertions.assertTrue(shoppingCart.equals(shoppingCartTrue));

    Assertions.assertFalse(shoppingCart.equals(shoppingCartFalse));
    Assertions.assertFalse(shoppingCart.equals(null));
    Assertions.assertFalse(shoppingCart.equals(new Object()));

  }

  @Test
  void testHashCode() {
    ShoppingCart shoppingCartTrue = this.shoppingCart;
    Assertions.assertEquals(shoppingCartTrue.hashCode(), shoppingCart.hashCode());

  }
}