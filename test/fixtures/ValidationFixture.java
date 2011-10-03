package fixtures;

import play.data.validation.Validation;
import play.data.validation.Error;
import org.junit.*;
import play.test.*;

public abstract class ValidationFixture extends UnitTest {
  
  protected Validation validation;
  
  @Before
  public void before() {
    validation = Validation.current();
    validation.clear();
  }
  
  public String createString(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; ++i) {
      sb.append('a');
    }
    return sb.toString();
  }
  
  public void assertError(String name, String message) {
    String fixedName = "." + name;
    for(Error error : validation.errors()) {
      if (fixedName.equals(error.getKey())) {
        assertEquals(message, error.message());
        return;
      }
    }
    fail("expected error was not found");
  }
  
  public void assertNoErrors() {
    assertEquals(false, validation.hasErrors());
  }
}