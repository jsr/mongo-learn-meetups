package controllers.tests.home;

import models.*;
import fixtures.*;
import java.util.*;
import org.junit.*;
import play.mvc.Http.Response;

public class Index extends ControllerFixture {
  
  @Test
  public void displaysTheHomePageWithAllTheTagsSortedByName() { 
    Helper.load("tags");
    assertIsOk(GET("/"));
    List<Tag> tags = (List)renderArgs("tags");
    assertEquals(3, tags.size());
    assertEquals("MongoDB", tags.get(0).getId());
    assertEquals("NoSQL", tags.get(1).getId());
    assertEquals("Pie", tags.get(2).getId());
  }
}