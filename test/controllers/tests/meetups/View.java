package controllers.tests.meetups;

import models.*;
import fixtures.*;
import org.junit.*;
import play.mvc.Http.Response;

public class View extends ControllerFixture {
  
  @Test
  public void redirectsToTheHomePageIfTheMeetupDoesNotExist() { 
    assertRedirectsTo("/", GET("/meetups/view/123"));
  }
  
  @Test
  public void displaysTheMeetup() { 
    Meetup meetup = Helper.loadMeetupOne();
    assertIsOk(GET("/meetups/view/" + meetup.getId()));
    assertEquals(meetup, renderArgs("meetup"));
  }
}