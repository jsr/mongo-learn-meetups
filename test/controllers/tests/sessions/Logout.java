package controllers.tests.sessions;

import org.junit.*;
import play.mvc.Http.Response;
import fixtures.ControllerFixture;

public class Logout extends ControllerFixture {
      
  @Test
  public void signsTheUserOut() {
    Response response = LoggedInGET("/sessions/logout");
    assertRedirectsTo("/", response);
    assertNull(getSession(response, "uid"));
  }

}