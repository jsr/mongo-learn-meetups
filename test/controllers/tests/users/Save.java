package controllers.tests.users;

import models.*;
import org.junit.*;
import play.mvc.Http.Response;
import fixtures.ControllerFixture;

public class Save extends ControllerFixture {
  
  @Test
  public void returnsValidationErrors() {
    Response response = POST("/users");
    assertNotNull(renderArgs("validation"));
    assertContentMatch("Create a new account", response);
  }
  
  @Test
  public void returnsValidationErrorsOnDuplicateName() {
    Response response = POST("/users?user.name=jessica&user.password=otherpass");
    assertValidation("user.name", "is already taken");
    assertContentMatch("Create a new account", response);
  }
  
  @Test
  public void registersTheUser() {
    Response response = POST("/users?user.name=lEto&user.password=goldenpath");
    assertEquals(1, User.filter("_id = ", "leto").count());
    assertRedirectsTo("/", response);
    assertEquals("leto", getSession(response, "uid"));
  }
}