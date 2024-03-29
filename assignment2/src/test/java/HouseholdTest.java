import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HouseholdTest {
  private Household household;
  @BeforeEach
  void setUp() {
    household = new Household("ppt", "good ppt", 4.99, 3, 10);

  }

  @Test
  void getUnits() {
    Assertions.assertEquals(10, household.getUnits());
  }

  @Test
  void testEquals() {
    Household householdTrue = this.household;

    Assertions.assertTrue(household.equals(household));
    Assertions.assertTrue(householdTrue.equals(household));

    Household householdFalse = new Household("ppt work", "bad ppt",3.3, 5, 4);

    Assertions.assertFalse(household.equals(null));
    Assertions.assertFalse(household.equals(new Object()));
    Assertions.assertFalse(household.equals(householdFalse));

  }

  @Test
  void testHashCode() {
    Household householdTrue = new Household("ppt", "good ppt", 4.99, 3, 10);
    Assertions.assertEquals(householdTrue.hashCode(), household.hashCode());

  }
}