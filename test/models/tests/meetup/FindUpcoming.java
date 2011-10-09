package models.tests.meetup;

import utils.*;
import models.*;
import fixtures.*;
import org.junit.*;
import java.util.*;

//these tests are going to break after 2015 because of the hard-coded dates!
//what testing noobry
public class FindUpcoming extends ModelFixture {  
  
  @Test
  public void returnsMeetupsAfterTheSpecifiedDate() {
    Helper.load("meetup-set1");
    Date now = new Date();
    PagedResult<Meetup> result = Meetup.findUpcoming(1);
    assertEquals(6, result.total);
    for(Meetup meetup : result.models) {
      assertTrue(meetup.date.compareTo(now) > 0);
    }
  }
   
  @Test
  public void loadSpecifiedPage() {
    Helper.load("meetup-set1");
    PagedResult<Meetup> result = Meetup.findUpcoming(2);
    assertEquals(6, result.total);
    assertEquals(1, result.models.size());
  }
   
  @Test
  public void ordersTheMeetupsByDate() {
    Helper.load("meetup-set1");
    Date start = new Date();
    PagedResult<Meetup> result = Meetup.findUpcoming(1);
    for(Meetup meetup : result.models) {
      assertTrue(meetup.date.compareTo(start) > 0);
      start = meetup.date;
    }
  }
   
  @Test
  public void returnsPageInformationIntoTheResult() {
    Helper.load("meetup-set1");
    PagedResult<Meetup> result = Meetup.findUpcoming(2);
    assertEquals(2, result.page);
    assertEquals(2, result.pages);
  }
}
