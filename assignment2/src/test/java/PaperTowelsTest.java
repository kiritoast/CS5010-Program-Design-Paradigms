import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class PaperTowelsTest {
  PaperTowels paperTowels;
  @BeforeEach
  void setUp() {
    this.paperTowels = new PaperTowels("ppt", "good ppt", 4.99, 3, 10);
  }
}