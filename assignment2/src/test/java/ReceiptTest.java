import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReceiptTest {
  Receipt receipt;
  private ArrayList<Product> productReceived;
  private ArrayList<Product> productOutOfStock;
  private ArrayList<Product> productRemoved;
  private ArrayList<Product> productRemovedFalse;
  private ArrayList<Product> productReceivedFalse;

  Beer beer;

  @BeforeEach
  void setUp() {
    productReceived = new ArrayList<>();
    productOutOfStock = new ArrayList<>();
    productRemoved = new ArrayList<>();
    productRemovedFalse = new ArrayList<>();
    productReceivedFalse = new ArrayList<>();
    productReceivedFalse.add(beer);
    productRemovedFalse.add(beer);
    receipt = new Receipt(23.00, productReceived, productOutOfStock, productRemoved);
  }

  @Test
  void getTotalPrice() {
    Assertions.assertEquals(23.00, receipt.getTotalPrice());

  }

  @Test
  void getProductReceived() {
    Assertions.assertEquals(productReceived, receipt.getProductReceived());

  }

  @Test
  void getProductOutOfStock() {
    Assertions.assertEquals(productOutOfStock, receipt.getProductOutOfStock());

  }

  @Test
  void getProductRemoved() {
    Assertions.assertEquals(productRemoved, receipt.getProductRemoved());

  }

  @Test
  void testEquals() {
    Receipt receiptTrue = this.receipt;
    Receipt receiptFalse = new Receipt(50.15, productReceived, productOutOfStock, productRemoved);
    Receipt receiptFalse2 = new Receipt(23.00, productReceived, productRemovedFalse, productRemoved);
    Receipt receiptFalse3 = new Receipt(23.00, productRemovedFalse, productRemovedFalse, productRemovedFalse);


    Assertions.assertTrue(receipt.equals(receipt));
    Assertions.assertTrue(receipt.equals(receiptTrue));

    Assertions.assertFalse(receipt.equals(receiptFalse));
    Assertions.assertFalse(receipt.equals(receiptFalse2));
    Assertions.assertFalse(receipt.equals(null));
    Assertions.assertFalse(receipt.equals(new Object()));

  }

  @Test
  void testHashCode() {
    Receipt receiptTrue = this.receipt;
    Assertions.assertEquals(receipt.hashCode(), receiptTrue.hashCode());

  }
}