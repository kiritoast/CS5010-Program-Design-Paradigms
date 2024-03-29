import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class BeerTest {
  Beer beer;
  Beer beer2;

  @BeforeEach
  void setUp() {
    beer = new Beer("bear fac","good beer",23.43,21,30.33);
  }
}