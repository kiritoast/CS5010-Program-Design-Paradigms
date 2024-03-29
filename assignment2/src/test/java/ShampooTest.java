import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class ShampooTest {
  private Shampoo shampoo;
  @BeforeEach
  void setUp() {
    shampoo = new Shampoo("best shamp", "shampoo first", 8.99, 7,6);

  }
}