package models.tests.user;

import utils.*;
import models.*;
import fixtures.*;
import org.junit.*;

public class LoadFromCredentials extends ModelFixture {
  
  @Test
  public void returnsNullIfTheNameIsNotValid() {
    assertEquals(null, User.loadFromCredentials("invalid", "anything"));
  }
  
  @Test
  public void returnsNullIfThePasswordDoesNotMatch() {
    Helper.load("user-jessica");
    assertEquals(null, User.loadFromCredentials("jessica", "wrong"));
  }
  
  @Test
  public void returnsTheUser() {
    Helper.load("user-jessica");
    assertEquals("jessica", User.loadFromCredentials("jessica", "dukeleto").name);
    assertEquals("jessica", User.loadFromCredentials("jESsica", "dukeleto").name);
  }
}
