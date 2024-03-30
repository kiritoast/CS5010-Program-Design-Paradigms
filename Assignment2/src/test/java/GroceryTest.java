import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroceryTest {
  private Grocery grocery;

  @BeforeEach
  void setUp() {
    grocery = new Grocery("factory", "good cheese",10.5, 4, 37.9);

  }

  @Test
  void getWeight() {
    Assertions.assertEquals(37.9, grocery.getWeight());
  }

  @Test
  void testEquals() {
    Grocery groceryTrue = new Grocery("factory", "good cheese",10.5, 4, 37.9);
    Grocery groceryFalse = new Grocery("factory", "good cheese",3.3, 5, 55.1);

    Assertions.assertTrue(grocery.equals(grocery));
    Assertions.assertFalse(grocery.equals(null));
    Assertions.assertFalse(grocery.equals(new Object()));
    Assertions.assertTrue(grocery.equals(groceryTrue));
    Assertions.assertFalse(grocery.equals(groceryFalse));

  }

  @Test
  void testHashCode() {
    Grocery groceryTrue = new Grocery("factory", "good cheese",10.5, 4, 37.9);
    Assertions.assertEquals(groceryTrue.hashCode(), grocery.hashCode());

  }
}