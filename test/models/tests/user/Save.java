package models.tests.user;

import utils.*;
import models.*;
import org.junit.*;
import fixtures.ModelFixture;

public class Save extends ModelFixture {
  @Test
  public void savesTheUserWithAHashedPassword() {
    User user = new User();
    user.name = "my name";
    user.password = "plain text";
    user.save();
    assertEquals(1, User.filter("name = ", user.name).filter("password = ", user.password).count());
  }

  @Test
  public void hashesThePassword() {
    User user = new User();
    user.name = "my name";
    user.password = "plain text";
    user.save();
    assertEquals(true, BCrypt.checkpw("plain text", user.password));
  }

  @Test(expected=DuplicateKeyException.class)
  public void throwsExceptionOnDuplicateName() {
    User user1 = new User();
    user1.name = "leto";
    user1.save();
    
    User user2 = new User();
    user2.name = "leto";
    user2.save();
  }
}