import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockItemTest {
  Cheese cheese1;
  Cheese cheese2;
  StockItem stockItem;


  @BeforeEach
  void setUp() {
    cheese1 = new Cheese("factory", "good cheese",10.5, 4, 37.9);
    cheese2 = new Cheese("factory-b", "bad cheese",11.3, 5, 22.4);
    stockItem = new StockItem(cheese1, 20);
  }

  @Test
  void checkEnoughStock() {
    Assertions.assertTrue(stockItem.checkEnoughStock(3));
    Assertions.assertFalse(stockItem.checkEnoughStock(22));

  }

  @Test
  void decreaseQuantity() {
    Assertions.assertTrue(stockItem.decreaseQuantity(3));
    Assertions.assertFalse(stockItem.decreaseQuantity(22));

  }

  @Test
  void increaseQuantity() {
    stockItem.increaseQuantity(27);
    Assertions.assertEquals(47,stockItem.getQuantity());

  }

  @Test
  void getProduct() {
    Assertions.assertEquals(cheese1, stockItem.getProduct());
  }

  @Test
  void getQuantity() {
    Assertions.assertEquals(20, stockItem.getQuantity());
  }

  @Test
  void testEquals() {
    StockItem stockItem1 = new StockItem(cheese1, 20);
    StockItem stockItem2 = new StockItem(cheese2, 40);
    StockItem stockItem3 = new StockItem(cheese1, 30);

    Assertions.assertFalse(stockItem.equals(null));
    Assertions.assertFalse(stockItem.equals(new Object()));
    Assertions.assertFalse(stockItem.equals(stockItem2));
    Assertions.assertFalse(stockItem.equals(stockItem3));

    Assertions.assertTrue(stockItem1.equals(stockItem1));
    Assertions.assertTrue(stockItem.equals(stockItem1));

  }

  @Test
  void testHashCode() {
    StockItem stockItem1 = new StockItem(cheese1, 20);
    Assertions.assertEquals(stockItem1.hashCode(), stockItem.hashCode());

  }
}