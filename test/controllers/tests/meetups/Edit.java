package controllers.tests.meetups;

import models.*;
import org.junit.*;
import fixtures.*;
import play.mvc.Http.Response;
import org.bson.types.ObjectId;

public class Edit extends ControllerFixture {
  
  @Test
  public void mustBeSignedIn() {
    assertMustBeLoggedIn(GET("/meetups/edit/id"));
  }

  @Test
  public void mustBeTheOwnerOfTheMeetup() {
    Response response = LoggedInGET("/meetups/edit/" + ObjectId.get());
    assertRedirectsTo("/", response);
  }

  @Test
  public void loadsTheView() {
    Meetup meetup = Helper.loadMeetupOne();
    Response response = LoggedInGET("/meetups/edit/" + meetup.getId());
    assertIsOk(response);
    assertEquals(meetup, renderArgs("meetup"));
  }
}