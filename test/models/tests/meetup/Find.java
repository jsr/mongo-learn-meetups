package models.tests.meetup;

import utils.*;
import models.*;
import fixtures.*;
import org.junit.*;
import java.util.*;

public class Find extends ModelFixture {  
  
  @Test
  public void returns250MeetupsIfDoingAGeoSearch() {
    insertGeoMeetups();
    List<Meetup> meetups = Meetup.find(5f, 5f, null);
    assertEquals(200, meetups.size());
  }
  
  @Test
  public void returnsMeetupsNearTheSpecificCoordinates() {
    insertGeoMeetups();
    List<Meetup> meetups = Meetup.find(5f, 5f, null);
    assertEquals(100f, (float)meetups.get(199).coordinates.get(0), 0);
    assertEquals(100f, (float)meetups.get(199).coordinates.get(1), 0);
  }
  
  @Test
  public void returnsMeetupsContainingASpecifiedTag() {
    Helper.load("meetup-set1");
    List<Meetup> meetups = Meetup.find(0, 0, createTags("spice"));
    assertEquals(2, meetups.size());
    for(Meetup meetup : meetups) {
      assertTrue(meetup.tags.contains("spice"));
    }
  }
   
  @Test
  public void returnsMeetupsContainingAnySpecifiedTags() {
    Helper.load("meetup-set1");
    List<Meetup> meetups = Meetup.find(0,0, createTags("spice", "dance"));
    assertEquals(3, meetups.size());
    for(Meetup meetup : meetups) {
      assertTrue(meetup.tags.contains("spice") || meetup.tags.contains("dance"));
    }
  }
   
  @Test
  public void returnsNothingIfNoTagsMatch() {
    Helper.load("meetup-set1");
    List<Meetup> meetups = Meetup.find(0, 0, createTags("harkonnen"));
    assertEquals(0, meetups.size());
  }
   
  private Set<String> createTags(String... tags) {
    Set<String> set = new HashSet<String>();
    for(String tag : tags) {
      set.add(tag);
    }
    return set;
  }
   
  private void insertGeoMeetups() {
    for(int i = 0; i < 300; ++i) {
      Helper.loadMeetup(new Float[]{(1f+i)/2, (1f+i)/2});
    }
  }
}
