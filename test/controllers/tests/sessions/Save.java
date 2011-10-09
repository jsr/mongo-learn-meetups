package controllers.tests.sessions;

import org.junit.*;
import play.mvc.Http.Response;
import fixtures.ControllerFixture;

public class Save extends ControllerFixture {
  
  @Test
  public void returnsErrorIfTheUserIsntFound() {
    Response response = POST("/sessions");
    assertError("invalid name or password, please try again");
    assertContentMatch("Please enter your name and password", response);
  }
    
  @Test
  public void signsTheUserIn() {
    Response response = POST("/sessions?name=jessica&password=dukeleto");
    assertRedirectsTo("/", response);
    assertEquals("jessica", getSession(response, "uid"));
  }

}