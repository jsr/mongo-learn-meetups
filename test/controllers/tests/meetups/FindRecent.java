package controllers.tests.meetups;

import models.*;
import fixtures.*;
import org.junit.*;
import com.google.gson.*;
import play.mvc.Http.Response;

public class FindRecent extends ControllerFixture {
  
  @Test
  public void returnsTheFoundMeetups() { 
    Helper.load("meetup-set1");
    Response response = GET("/meetups/findrecent?page=2");
    assertIsOk(response);
    JsonObject root = deserializeResponse(response);
    
    assertEquals(2, root.get("page").getAsInt());
    assertEquals(2, root.get("pages").getAsInt());
    assertEquals(8, root.get("total").getAsInt());
  }
}