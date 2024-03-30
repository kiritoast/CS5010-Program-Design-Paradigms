import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest {
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

  String name;
  int age;
  Customer customer1;
  Customer customer2;
  Inventory inventory;
  ArrayList<StockItem> groceryStock;
  ArrayList<StockItem> householdStock;

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
    customer1 = new Customer("Jinming", 20, shoppingCart);
    customer2 = new Customer("Lee", 1, shoppingCart);


    groceryStock = new ArrayList<StockItem>();
    householdStock = new ArrayList<StockItem>();

    groceryStock.add(new StockItem(beer1, 1));
    groceryStock.add(new StockItem(cheese1, 10));
    groceryStock.add(new StockItem(salmon1, 0));
    householdStock.add(new StockItem(paperTowel1, 10));
    householdStock.add(new StockItem(shampoo, 0));

    inventory = new Inventory(groceryStock, householdStock);

  }

  @Test
  void getName() {
    Assertions.assertEquals("Jinming",customer1.getName());
  }

  @Test
  void getAge() {
    Assertions.assertEquals(20,customer1.getAge());

  }

  @Test
  void getShoppingCart() {
    Assertions.assertEquals(shoppingCart,customer1.getShoppingCart());

  }

  @Test
  void checkGroceryQuantity() {
    try {
      customer1.checkGroceryQuantity(beer1,1,inventory);
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
    try {
      customer1.checkGroceryQuantity(salmon1,2,inventory);
    } catch (Exception e){
      System.out.println(e.getMessage());
    }

  }

  @Test
  void checkHouseholdQuantity() {
    try {
      customer1.checkHouseholdQuantity(paperTowel1,5,inventory);
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
    try {
      customer1.checkHouseholdQuantity(shampoo,0,inventory);
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  @Test
  void addItemToCart() {
    customer1.addItemToCart(beer2,5, inventory);
    customer1.addItemToCart(cheese1,20, inventory);

  }

  @Test
  void testAddItemToCart() {
    customer1.addItemToCart(beer2, inventory);
    customer1.addItemToCart(cheese1, inventory);
  }

  @Test
  void checkOut() {
    customer1.checkOut(inventory);
  }

  @Test
  void testEquals() {
    Customer customerTrue = new Customer("Jinming", 20, shoppingCart);
    Customer customerFalse = new Customer("hi", 5, shoppingCart);

    Assertions.assertTrue(customer1.equals(customer1));
    Assertions.assertTrue(customer1.equals(customerTrue));

    Assertions.assertFalse(customer1.equals(customerFalse));
    Assertions.assertFalse(customer1.equals(null));
    Assertions.assertFalse(customer1.equals(new Object()));

  }

  @Test
  void testHashCode() {
    Customer customerTrue = new Customer("Jinming", 20, shoppingCart);
    Assertions.assertEquals(customerTrue.hashCode(), customer1.hashCode());

  }
}