import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {
  private Product product;

  @BeforeEach
  void setUp() {
    product = new Product("factory", "good cheese",10.5, 4);
  }

  @Test
  void getManufacturer() {
    Assertions.assertEquals("factory", product.getManufacturer());
  }

  @Test
  void getProductName() {
    Assertions.assertEquals("good cheese", product.getProductName());
  }

  @Test
  void getPrice() {
    Assertions.assertEquals(10.5, product.getPrice());
  }

  @Test
  void getMinimumAge() {
    Assertions.assertEquals(4, product.getMinimumAge());
  }

  @Test
  void testEquals() {
    Product productTrue = new Product("factory", "good cheese",10.5, 4);
    Product productFalse = new Product("tory", "cheese",3.3, 5);

    Assertions.assertTrue(productTrue.equals(productTrue));
    Assertions.assertFalse(product.equals(null));
    Assertions.assertFalse(product.equals(new Object()));
    Assertions.assertTrue(product.equals(product));
    Assertions.assertFalse(product.equals(productFalse));

  }

  @Test
  void testHashCode() {
    Product productTrue = new Product("factory", "good cheese",10.5, 4);
    Assertions.assertEquals(productTrue.hashCode(), product.hashCode());
  }
}