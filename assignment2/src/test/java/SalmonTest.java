import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class SalmonTest {
  Salmon salmon;
  @BeforeEach
  void setUp() {
    salmon = new Salmon("factory work","best salmon", 12.99,5,8.21);
  }
}