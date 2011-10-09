package controllers.tests.meetups;

import models.*;
import fixtures.*;
import org.junit.*;
import play.mvc.Http.Response;

public class Save extends ControllerFixture {
  
  @Test
  public void mustBeSignedIn() {
    assertMustBeLoggedIn(POST("/meetups/save"));
  }
  
  @Test
  public void returnsValidationErrors() {
    Response response = LoggedInPOST("/meetups");
    assertNotNull(renderArgs("validation"));
    assertContentMatch("Create Meetup", response);
  }
  
  @Test
  public void savesAValidMeetup() {        
    Response response = LoggedInPOST("/meetups?meetup.name=a-name&meetup.city=a-city&meetup.address=a-address&meetup.date=2011-09-11T16:00&meetup.duration=1.5&meetup.description=a-description&meetup.link=a-link&meetup.coordinates=1&meetup.coordinates=1");
    Meetup meetup = Meetup.find().first();
    assertEquals(1, Meetup.find().count());
    assertRedirectsTo("/meetups/view/" + meetup.getId(), response);
  }
  
  @Test
   public void editsAValidMeetup() {
     Meetup meetup = Helper.loadMeetupOne();
     Response response = LoggedInPOST("/meetups?meetup.name=a-name&meetup.city=a-city&meetup.address=a-address&meetup.date=2011-09-11T16:00&meetup.duration=1.5&meetup.description=a-description&meetup.link=a-link&meetup.coordinates=1&meetup.coordinates=1&meetup._id=" + meetup.getId());
     assertEquals(1, Meetup.filter("name = ", "a-name").count());
     assertEquals(1, Meetup.find().count());
     assertRedirectsTo("/meetups/view/" + meetup.getId(), response);
   }
}