package models.tests.user;

import models.*;
import org.junit.*;
import java.util.*;
import fixtures.ValidationFixture;

public class Validation extends ValidationFixture {
         
  @Test
  public void isInvalidWhenTheNameIsMissing() {
    User user = new User();
    validation.valid(user);
    assertError("name", "Required");
  }
  
  @Test
  public void isInvalidWhenTheNameIsTooLong() {
    User user = new User();
    user.name = createString(21); 
    validation.valid(user);
    assertError("name", "Maximum size is 20");
  }
  
  @Test
  public void isInvalidWhenThePasswordIsMissing() {
    User user = new User();
    validation.valid(user);
    assertError("password", "Required");
  }
  
  @Test
  public void isInvalidWhenThePasswordIsTooshort() {
    User user = new User();
    user.password = "1234567";
    validation.valid(user);
    assertError("password", "Minimum size is 8");
  }
  
  @Test
  public void hasNoErrorsWhenValid() {
    User user = new User();
    user.name = "Leto";
    user.password = "Ghanima1";
    assertNoErrors();
  }
}
