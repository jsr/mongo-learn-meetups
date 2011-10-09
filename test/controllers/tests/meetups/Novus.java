package controllers.tests.meetups;

import models.*;
import org.junit.*;
import play.mvc.Http.Response;
import org.bson.types.ObjectId;
import fixtures.ControllerFixture;

public class Novus extends ControllerFixture {
  
  @Test
  public void mustBeSignedIn() {
    assertMustBeLoggedIn(GET("/meetups/new"));
  }

  @Test
  public void loadsTheView() {
    Response response = LoggedInGET("/meetups/new");
    assertIsOk(response);
    assertTrue(((Meetup)renderArgs("meetup")).isNew());
  }
}