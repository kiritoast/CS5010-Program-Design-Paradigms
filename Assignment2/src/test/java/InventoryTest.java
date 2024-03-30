import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryTest {
  Beer beer1;
  Beer beer2;
  Cheese cheese1;
  Cheese cheese2;
  Salmon salmon1;
  Salmon salmon2;
  PaperTowels paperTowel1;
  PaperTowels paperTowel2;
  Shampoo shampoo;
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
    groceryStock = new ArrayList<StockItem>();
    householdStock = new ArrayList<StockItem>();

    groceryStock.add(new StockItem(beer1, 10));
    groceryStock.add(new StockItem(cheese1, 10));
    groceryStock.add(new StockItem(salmon1, 0));
    householdStock.add(new StockItem(paperTowel1, 10));
    householdStock.add(new StockItem(shampoo, 0));

    inventory = new Inventory(groceryStock, householdStock);
  }

  @Test
  void substitute() {
    Assertions.assertEquals(beer1, inventory.substitute(beer1));
    Assertions.assertEquals(null, inventory.substitute(salmon1));
    Assertions.assertEquals(null, inventory.substitute(paperTowel1));
    Assertions.assertEquals(null, inventory.substitute(shampoo));


  }

  @Test
  void addStock() {
    Assertions.assertTrue(inventory.addStock(new StockItem(beer2, 20)));
    Assertions.assertTrue(inventory.addStock(new StockItem(cheese2, 20)));
    Assertions.assertFalse(inventory.addStock(new StockItem(null, 20)));
  }

  @Test
  void getTotalPrice() {
    Assertions.assertEquals(52.5,inventory.getTotalPrice());

  }

  @Test
  void getGroceryStock() {
    Assertions.assertEquals(groceryStock,inventory.getGroceryStock());
  }

  @Test
  void getHouseholdStock() {
    Assertions.assertEquals(householdStock,inventory.getHouseholdStock());

  }

  @Test
  void testEquals() {
    Inventory inventoryTrue = this.inventory;
    Inventory inventoryFalse = new Inventory(groceryStock, groceryStock);
    Assertions.assertTrue(inventory.equals(inventory));
    Assertions.assertTrue(inventory.equals(inventoryTrue));

    Assertions.assertFalse(inventory.equals(inventoryFalse));
    Assertions.assertFalse(inventory.equals(null));
    Assertions.assertFalse(inventory.equals(new Object()));

  }

  @Test
  void testHashCode() {
    Inventory inventoryTrue = this.inventory;
    Assertions.assertEquals(inventoryTrue.hashCode(), inventory.hashCode());

  }
}