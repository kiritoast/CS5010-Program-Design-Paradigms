import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class CheeseTest {
  private Cheese cheese;
  @BeforeEach
  void setUp() {
    cheese = new Cheese("factory", "good cheese",10.5, 4, 37.9);
  }
}