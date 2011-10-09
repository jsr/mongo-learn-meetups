package models.tests.recent;

import utils.*;
import models.*;
import fixtures.*;
import org.junit.*;
import java.util.*;

public class Find extends ModelFixture {

  @Test
  public void returnsNothingIfThereAreNoRecentMeetups() {
    PagedResult<Meetup> result = Recent.find(1);
    assertEquals(0, result.total);
    assertEquals(0, result.models.size());
  }
  
  @Test
  public void returnsTheMostRecentMeetups() {
    Helper.load("meetup-set1");
    PagedResult<Meetup> result = Recent.find(1);
    assertEquals(8, result.total);
    assertEquals(5, result.models.size());
    assertEquals("Annual Bullfight", result.models.get(0).name);
    assertEquals("AI Synopsis", result.models.get(4 ).name);
  }
  
  @Test
  public void returnsPagedResults() {
    Helper.load("meetup-set1");
    PagedResult<Meetup> result = Recent.find(2);
    assertEquals(8, result.total);
    assertEquals(3, result.models.size());
  }
}
